import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import {
  AbstractControlOptions,
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ISubscriber } from '../../../../core/models/subscriber.model';
import { ITargetCategory } from '../../../../core/models/target-category.model';
import { SubscriberService } from '../../../../shared/services/subscriber.service';
import { TargetCategoryService } from '../../../../shared/services/target-category.service';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss',
})
export default class ProfileComponent {
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _susbscriberService: SubscriberService = inject(SubscriberService);
  private _targetService: TargetCategoryService = inject(TargetCategoryService);

  subscriberFormGroup: FormGroup;
  error: string = '';
  targets: ITargetCategory[] = [];
  subscriber: ISubscriber | null = null;
  editMode: boolean = false;

  constructor() {
    this.subscriberFormGroup = this._formBuilder.group(
      {
        email: ['', [Validators.required, Validators.email]],
        firstname: ['', Validators.required],
        lastname: ['', Validators.required],
        phone: ['', Validators.required],
        birth: ['', Validators.required],
        marketingPreferences: this._formBuilder.array([]),
      },
      {
        validator: this.passwordMatchValidator, // Validador personalizado para verificar si las contraseñas coinciden
      } as AbstractControlOptions
    );

    this._targetService.getAllTargetCategories().subscribe({
      next: (data) => {
        this.targets = data;
      },
    });

    this.getSubscriberData();

    this.toggleFormControls();
  }

  getSubscriberData() {
    this._susbscriberService.getSubscriber().subscribe({
      next: (data) => {
        this.subscriber = data;
      },
      complete: () => {
        this.setFormData();
      },
    });
  }

  private addTarget(id: number): void {
    this.marketingPreferences.push(new FormControl(id));
  }

  private removeTarget(id: number): void {
    this.marketingPreferences.removeAt(
      this.marketingPreferences.value.indexOf(id)
    );
  }

  isChecked(id: number): boolean {
    return (this.marketingPreferences.value as number[]).includes(id);
  }

  onCheckboxChange(event: Event): void {
    const target = <HTMLInputElement>event.target;
    const isChecked = target.checked;
    const id = parseInt(target.value);

    if (isChecked) {
      this.addTarget(id);
    } else {
      this.removeTarget(id);
    }
  }

  passwordMatchValidator(formGroup: FormGroup) {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  setFormData() {
    if (this.subscriber) {
      this.resetForm();
      this.subscriberFormGroup.setValue({
        email: this.subscriber.email,
        firstname: this.subscriber.firstname,
        lastname: this.subscriber.lastname,
        phone: this.subscriber.phone,
        birth: this.subscriber.birth,
        marketingPreferences: [],
      });

      this.subscriber.marketingPreferences.map((target) =>
        this.addTarget(target)
      );
    }
  }

  toggleEditMode() {
    this.editMode = !this.editMode;

    if (!this.editMode) {
      this.getSubscriberData();
    }

    this.toggleFormControls();
  }

  private toggleFormControls() {
    if (this.editMode) {
      this.subscriberFormGroup.enable();
    } else {
      this.subscriberFormGroup.disable();
    }
  }

  get marketingPreferences(): FormArray {
    return this.subscriberFormGroup.get('marketingPreferences') as FormArray;
  }

  get confirmPassword() {
    return this.subscriberFormGroup.controls['confirmPassword'];
  }

  get passwordMismatch() {
    return (
      this.subscriberFormGroup.hasError('mismatch') &&
      this.subscriberFormGroup.get('confirmPassword')?.dirty
    );
  }

  get email() {
    return this.subscriberFormGroup.controls['email'];
  }

  get password() {
    return this.subscriberFormGroup.controls['password'];
  }

  get firstname() {
    return this.subscriberFormGroup.controls['firstname'];
  }

  get lastname() {
    return this.subscriberFormGroup.controls['lastname'];
  }

  get phone() {
    return this.subscriberFormGroup.controls['phone'];
  }

  get birth() {
    return this.subscriberFormGroup.controls['birth'];
  }

  submit() {
    const isValid = this.subscriberFormGroup.valid;
    if (isValid) {
      const data: ISubscriber = this.subscriberFormGroup.value;
      this.updateSubscriber(data);
    } else {
      this.error = $localize`Please ensure that all fields are complete and meet the required specifications`;
      this.subscriberFormGroup.markAllAsTouched();
    }
  }

  updateSubscriber(data: ISubscriber) {
    this._susbscriberService.updateSubscriber(data).subscribe({
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.subscriberFormGroup.reset();
        this.error = '';
        this.editMode = false;
        this.getSubscriberData();
      },
    });
  }

  resetForm() {
    this.marketingPreferences.clear();

    this.subscriberFormGroup.reset({
      email: '',
      firstname: '',
      lastname: '',
      phone: '',
      birth: '',
      marketingPreferences: [],
    });
  }
}
