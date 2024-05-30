import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ITargetCategory } from '../../../../core/models/target-category.model';
import { TargetCategoryService } from '../../../../shared/services/target-category.service';

@Component({
  selector: 'app-target-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './target-form.component.html',
  styleUrl: './target-form.component.scss',
})
export class TargetFormComponent {
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _targetService: TargetCategoryService = inject(TargetCategoryService);

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  targetFormGroup: FormGroup;
  error: string = '';
  id: number = 0;

  constructor() {
    this.targetFormGroup = this._formBuilder.group({
      targetTitle: ['', Validators.required],
    });
  }

  @Input()
  set target(target: ITargetCategory | null) {
    if (!target) {
      this.id = 0;
      this.resetForm();
      this.error = '';
      return;
    }

    this.id = target.targetId!;

    this.targetFormGroup.setValue({
      targetTitle: target.targetTitle,
    });
  }

  submit() {
    const isValid = this.targetFormGroup.valid;
    if (isValid) {
      const data: ITargetCategory = this.targetFormGroup.value;
      this.id != 0
        ? this.updateTargetCategory(data)
        : this.createTargetCategory(data);
    } else {
      this.error = $localize`Please ensure that all fields are complete and meet the required specifications`;

      this.targetFormGroup.markAllAsTouched();
    }
  }

  createTargetCategory(data: ITargetCategory) {
    this._targetService.createTargetCategory(data).subscribe({
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.targetFormGroup.reset();
        this.error = '';
        this.doRefresh.emit();
      },
    });
  }

  updateTargetCategory(data: ITargetCategory) {
    this._targetService.updateTargetCategory(this.id, data).subscribe({
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.targetFormGroup.reset();
        this.error = '';
        this.id = 0;
        this.doRefresh.emit();
      },
    });
  }

  resetForm() {
    this.targetFormGroup.reset({
      targetTitle: '',
    });
  }
}
