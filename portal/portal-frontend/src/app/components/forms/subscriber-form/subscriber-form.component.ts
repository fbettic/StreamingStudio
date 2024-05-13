import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output, inject } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ISubscriber } from '../../../models/subscriber.model';
import { ITargetCategory } from '../../../models/target-category.model';
import { SubscriberService } from '../../../services/subscriber.service';
import { TargetCategoryService } from '../../../services/target-category.service';

@Component({
  selector: 'app-subscriber-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './subscriber-form.component.html',
  styleUrl: './subscriber-form.component.scss',
})
export class SubscriberFormComponent {
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _susbscriberService: SubscriberService = inject(SubscriberService);
  private _targetService: TargetCategoryService = inject(TargetCategoryService);

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  subscriberFormGroup: FormGroup;
  error: string = '';
  marketingPreferences: ITargetCategory[] = [];
  subscriber: ISubscriber | null = null;

  constructor() {
    this._targetService.getAllTargetCategories().subscribe({
      next: (data) => {
        this.marketingPreferences = data;
      },
    });

    this._susbscriberService.getSubscriber().subscribe({
      next: (data) => {
        this.subscriber = data;
      },
    });

    this.subscriberFormGroup = this._formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      firstname: ['', [Validators.required]],
      lastname: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      birth: ['', [Validators.required]],
      marketingPreferences: this._formBuilder.array([]),
    });
  }

  setFormData() {
    console.log(
      '🚀 ~ SubscriberFormComponent ~ setFormData ~ subscriber:',
      this.subscriber
    );

    if (this.subscriber != null) {

      this.subscriberFormGroup.setValue({
        email: this.subscriber.email,
        firstname: this.subscriber.firstname,
        lastname: this.subscriber.lastname,
        phone: this.subscriber.phone,
        birth: this.subscriber.birth,
        marketingPreferences: [],
      });
    }
  }

  onCheckboxChange(event: Event) {
    const target = <HTMLInputElement>event.target;
    const isChecked = target.checked;
    const formArray: FormArray = this.subscriberFormGroup.get(
      'marketingPreferences'
    ) as FormArray;

    if (isChecked) {
      formArray.push(this._formBuilder.control(target.value));
    } else {
      const index = formArray.controls.findIndex(
        (x) => x.value === target.value
      );
      formArray.removeAt(index);
    }
  }

  submit() {
    const isValid = this.subscriberFormGroup.valid;
    if (isValid) {
      const data: ISubscriber = this.subscriberFormGroup.value;
      this.updateAdvertiser(data);
    } else {
      this.error =
        'Por favor, verifique todos los campos del formulario y asegúrese de que estén completos y cumplan con las especificaciones requeridas.';
      this.subscriberFormGroup.markAllAsTouched();
    }
  }

  updateAdvertiser(data: ISubscriber) {
    this._susbscriberService.updateSubscriber(data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ AdvertiserFormComponent ~ this._advertiserService.createAdvertiser ~ error:',
          error
        );

        this.error = error.error;
      },
      complete: () => {
        this.subscriberFormGroup.reset();
        this.error = '';
        this.doRefresh.emit();
      },
    });
  }

  resetForm() {
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
