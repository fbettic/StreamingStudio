import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { IFee } from '../../../../core/models/fee.model';
import { ISizeType } from '../../../../core/models/size-type.model';
import { FeeService } from '../../../../shared/services/fee.service';
import { SizeTypeService } from '../../../../shared/services/size-type.service';

@Component({
  selector: 'app-size-type-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './size-type-form.component.html',
  styleUrl: './size-type-form.component.scss',
})
export class SizeTypeFormComponent {
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _feeService: FeeService = inject(FeeService);
  private _sizeTypeService: SizeTypeService = inject(SizeTypeService);

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  sizeTypeFormGroup: FormGroup;
  error: string = '';
  id: number = 0;
  sizeFees: IFee[] = [];

  constructor() {
    this._feeService.getAllFees().subscribe({
      next: (res) => {
        this.sizeFees = res.filter((fee) => fee.feeType.includes('SIZE'));
      },
      error: (error) => {},
    });

    this.sizeTypeFormGroup = this._formBuilder.group({
      sizeType: ['', Validators.required],
      sizeValue: [
        '',
        [Validators.required, Validators.min(1), Validators.max(10)],
      ],
      height: [
        '',
        [Validators.required, Validators.min(100), Validators.max(2000)],
      ],
      width: [
        '',
        [Validators.required, Validators.min(100), Validators.max(2000)],
      ],
      sizeFeeId: ['', Validators.required],
    });
  }

  @Input()
  set sizeType(sizeType: ISizeType | null) {
    if (!sizeType) {
      this.id = 0;
      this.resetForm();
      this.error = '';
      return;
    }

    this.id = sizeType.sizeId!;

    this.sizeTypeFormGroup.setValue({
      sizeType: sizeType.sizeType,
      sizeValue: sizeType.sizeValue,
      height: sizeType.height,
      width: sizeType.width,
      sizeFeeId: sizeType.sizeFeeId,
    });
  }

  submit() {
    const isValid = this.sizeTypeFormGroup.valid;
    if (isValid) {
      const data: ISizeType = this.sizeTypeFormGroup.value;
      this.id != 0 ? this.updateSizeType(data) : this.createSizeType(data);
    } else {
      this.error = $localize`Please ensure that all fields are complete and meet the required specifications`;
      this.sizeTypeFormGroup.markAllAsTouched();
    }
  }

  createSizeType(data: ISizeType) {
    this._sizeTypeService.createSizeType(data).subscribe({
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.sizeTypeFormGroup.reset();
        this.error = '';
        this.doRefresh.emit();
      },
    });
  }

  updateSizeType(data: ISizeType) {
    this._sizeTypeService.updateSizeType(this.id, data).subscribe({
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.sizeTypeFormGroup.reset();
        this.error = '';
        this.id = 0;
        this.doRefresh.emit();
      },
    });
  }

  resetForm() {
    this.sizeTypeFormGroup.reset({
      sizeType: '',
      sizeValue: '',
      height: '',
      width: '',
      sizeFeeId: '',
    });
  }
}
