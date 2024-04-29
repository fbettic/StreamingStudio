import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { IBannerPriority } from '../../../models/banner-priority.model';
import { IFee } from '../../../models/fee.model';
import { BannerPriorityService } from '../../../services/banner-priority.service';
import { FeeService } from '../../../services/fee.service';

@Component({
  selector: 'app-banner-priority-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './banner-priority-form.component.html',
  styleUrl: './banner-priority-form.component.scss',
})
export class BannerPriorityFormComponent {
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _feeService: FeeService = inject(FeeService);
  private _bannerPriorityService: BannerPriorityService = inject(
    BannerPriorityService
  );

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  bannerPriorityFormGroup: FormGroup;
  error: string = '';
  id: number = 0;
  priorityFees: IFee[] = [];

  constructor() {
    this._feeService.getAllFees().subscribe({
      next: (res) => {
        this.priorityFees = res.filter((fee) => fee.feeType.includes('PRIORITY'));
      },
      error: (error) => {
        console.log(
          '🚀 ~ AdvertisingFormComponent ~ this._priorityService.getAllBannerPriorities ~ error:',
          error
        );
      },
    });

    this.bannerPriorityFormGroup = this._formBuilder.group({
      priorityType: ['', Validators.required],
      priorityValue: [
        '',
       [ Validators.required,
        Validators.min(1),
        Validators.max(10)]
      ],
      priorityFeeId: ['', Validators.required],
    });
  }

  @Input()
  set bannerPriority(bannerPriority: IBannerPriority | null) {
    if (!bannerPriority) {
      this.id = 0;
      this.resetForm();
      this.error = '';
      return;
    }

    this.id = bannerPriority.priorityId!;

    this.bannerPriorityFormGroup.setValue({
      priorityType: bannerPriority.priorityType,
      priorityValue: bannerPriority.priorityValue,
      priorityFeeId: bannerPriority.priorityFeeId,
    });
  }

  submit() {
    const isValid = this.bannerPriorityFormGroup.valid;
    if (isValid) {
      const data: IBannerPriority = this.bannerPriorityFormGroup.value;
      this.id != 0
        ? this.updateBannerPriority(data)
        : this.createBannerPriority(data);
    } else {
      this.error =
        'Por favor, verifique todos los campos del formulario y asegúrese de que estén completos y cumplan con las especificaciones requeridas.';
      this.bannerPriorityFormGroup.markAllAsTouched();
    }
  }

  createBannerPriority(data: IBannerPriority) {
    this._bannerPriorityService.createBannerPriority(data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ BannerPriorityFormComponent ~ this._bannerPriorityService.createBannerPriority ~  error.error:',
          error.error
        );
        this.error = error.error;
      },
      complete: () => {
        this.bannerPriorityFormGroup.reset();
        this.error = '';
        this.doRefresh.emit();
      },
    });
  }

  updateBannerPriority(data: IBannerPriority) {
    this._bannerPriorityService.updateBannerPriority(this.id, data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ BannerPriorityFormComponent ~ updateBannerPriority ~ error.error:',
          error.error
        );

        this.error = error.error;
      },
      complete: () => {
        this.bannerPriorityFormGroup.reset();
        this.error = '';
        this.id = 0;
        this.doRefresh.emit();
      },
    });
  }

  resetForm() {
    this.bannerPriorityFormGroup.reset({
      priorityType: '',
      priorityValue: '',
      priorityFeeId: '',
    });
  }
}
