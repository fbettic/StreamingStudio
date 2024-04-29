import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  MinValidator,
} from '@angular/forms';
import { ISizeType } from '../../../models/size-type.model';
import { SizeTypeService } from '../../../services/size-type.service';
import { FeeService } from '../../../services/fee.service';
import { IFee } from '../../../models/fee.model';

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
  sizeFees:IFee[]=[]

  constructor() {
    this._feeService.getAllFees().subscribe({
      next: (res) => {
        this.sizeFees = res.filter((fee) => fee.feeType.includes('SIZE'));
      },
      error: (error) => {
        console.log(
          '🚀 ~ AdvertisingFormComponent ~ this._priorityService.getAllBannerPriorities ~ error:',
          error
        );
      },
    });

    this.sizeTypeFormGroup = this._formBuilder.group({
      sizeType: ['', Validators.required],
      sizeValue: ['', [Validators.required, Validators.min(1), Validators.max(10)]],
      height: ['', [Validators.required,Validators.min(100), Validators.max(2000)]],
      width: ['', [Validators.required,Validators.min(100), Validators.max(2000)]],
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
      this.error =
        'Por favor, verifique todos los campos del formulario y asegúrese de que estén completos y cumplan con las especificaciones requeridas.';
      this.sizeTypeFormGroup.markAllAsTouched();
    }
  }

  createSizeType(data: ISizeType) {
    this._sizeTypeService.createSizeType(data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ SizeTypeFormComponent ~ this._sizeTypeService.createSizeType ~  error.error:',
          error.error
        );
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
        console.log(
          '🚀 ~ SizeTypeFormComponent ~ updateSizeType ~ error.error:',
          error.error
        );

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
