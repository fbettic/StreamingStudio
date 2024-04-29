import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IFee } from '../../../models/fee.model';
import { FeeService } from '../../../services/fee.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-fee-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './fee-form.component.html',
  styleUrl: './fee-form.component.scss',
})
export class FeeFormComponent {
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _feeService: FeeService = inject(FeeService);

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  feeFormGroup: FormGroup;
  error: string = '';
  id: number = 0;

  constructor() {
    this.feeFormGroup = this._formBuilder.group({
      feeType: ['', Validators.required],
      feeValue: ['', Validators.required],
    });
  }

  @Input()
  set fee(fee: IFee | null) {
    if (!fee) {
      this.id = 0;
      this.resetForm();
      this.error = '';
      return;
    }

    this.id = fee.feeId!;

    this.feeFormGroup.setValue({
      feeType: fee.feeType,
      feeValue: fee.feeValue,
    });
  }

  submit() {
    const isValid = this.feeFormGroup.valid;
    if (isValid) {
      const data: IFee = this.feeFormGroup.value;
      this.id != 0 ? this.updateFee(data) : this.createFee(data);
    } else {
      this.error =
        'Por favor, verifique todos los campos del formulario y asegúrese de que estén completos y cumplan con las especificaciones requeridas.';
      this.feeFormGroup.markAllAsTouched();
    }
  }

  createFee(data: IFee) {
    this._feeService.createFee(data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ FeeFormComponent ~ this._feeService.createFee ~  error.error:',
          error.error
        );
        this.error = error.error;
      },
      complete: () => {
        this.feeFormGroup.reset();
        this.error = '';
        this.doRefresh.emit();
      },
    });
  }

  updateFee(data: IFee) {
    this._feeService.updateFee(this.id, data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ FeeFormComponent ~ updateFee ~ error.error:',
          error.error
        );

        this.error = error.error;
      },
      complete: () => {
        this.feeFormGroup.reset();
        this.error = '';
        this.id = 0;
        this.doRefresh.emit();
      },
    });
  }

  resetForm() {
    this.feeFormGroup.reset({
      feeType: '',
      feeValue: '',
    });
  }
}
