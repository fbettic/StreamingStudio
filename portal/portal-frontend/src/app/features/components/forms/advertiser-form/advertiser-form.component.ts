import { CommonModule } from '@angular/common';
import {
  Component,
  EventEmitter,
  Input,
  Output,
  effect,
  inject,
  input,
} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ServiceType } from '../../../../core/enums/service-type.enum';
import { IAdvertiser } from '../../../../core/models/advertiser.model';
import { AdvertiserService } from '../../../../shared/services/advertiser.service';

@Component({
  selector: 'app-advertiser-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './advertiser-form.component.html',
  styleUrl: './advertiser-form.component.scss',
})
export class AdvertiserFormComponent {
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _advertiserService: AdvertiserService = inject(AdvertiserService);

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  userForm = input<boolean>(false);
  advertiser = input<IAdvertiser|null>(null);

  advertiserFormGroup: FormGroup;
  error: string = '';
  id: number = 0;
  editMode: boolean = false;

  constructor() {
    this.advertiserFormGroup = this._formBuilder.group({
      agentFirstname: ['', [Validators.required]],
      agentLastname: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: [{ value: '', disabled: true }, [Validators.required]],
      companyName: ['', [Validators.required]],
      bussinesName: ['', [Validators.required]],
      phone: ['', [Validators.required]],
      apiUrl: [{ value: '', disabled: true }, [Validators.required]],
      authToken: [{ value: '', disabled: true }, [Validators.required]],
      serviceType: ['', [Validators.required]],
    });

    effect(() => {
      if (!this.userForm()) {
        this.editMode = true;
      }
      this.setAdvertiserData(this.advertiser())
    });
  }

  onServiceTypeChange(): void {
    const service = this.advertiserFormGroup.get('serviceType')?.value;

    if (service === '') {
      this.advertiserFormGroup.get('password')?.disable();
      this.advertiserFormGroup.get('authToken')?.disable();
      this.advertiserFormGroup.get('apiUrl')?.disable();
      return;
    }

    if (service === ServiceType.ACCOUNT) {
      this.advertiserFormGroup.get('password')?.enable();
      this.advertiserFormGroup.get('authToken')?.disable();
      this.advertiserFormGroup.get('apiUrl')?.disable();
    } else {
      this.advertiserFormGroup.get('password')?.disable();
      this.advertiserFormGroup.get('authToken')?.enable();
      this.advertiserFormGroup.get('apiUrl')?.enable();
    }
  }

  toggleCredentialsEditing() {
    this.advertiserFormGroup.get('password')?.disable();
    this.advertiserFormGroup.get('authToken')?.disable();
    this.advertiserFormGroup.get('apiUrl')?.disable();
    this.advertiserFormGroup.get('serviceType')?.disable();
  }


  setAdvertiserData(advertiser: IAdvertiser | null) {

    if (!advertiser) {
      this.id = 0;
      this.resetForm();
      this.error = '';
      return;
    }

    this.id = advertiser.advertiserId!;

    this.advertiserFormGroup.setValue({
      agentFirstname: advertiser.agentFirstname,
      agentLastname: advertiser.agentLastname,
      email: advertiser.email,
      password: '',
      companyName: advertiser.companyName,
      bussinesName: advertiser.bussinesName,
      phone: advertiser.phone,
      apiUrl: advertiser.apiUrl,
      authToken: '',
      serviceType: advertiser.serviceType,
    });

    this.toggleFormControls();
    this.onServiceTypeChange();
  }

  submit() {
    const isValid = this.advertiserFormGroup.valid;
    if (isValid) {
      const data: IAdvertiser = this.advertiserFormGroup.value;
      this.id != 0 ? this.updateAdvertiser(data) : this.createAdvertiser(data);
    } else {
      this.error = $localize`Please ensure that all fields are complete and meet the required specifications`;
      this.advertiserFormGroup.markAllAsTouched();
    }
  }

  toggleEditMode() {
    this.editMode = !this.editMode;
    this.toggleFormControls();
  }

  private toggleFormControls() {
    if (this.editMode) {
      this.advertiserFormGroup.enable();
      if (this.userForm()) {
        this.toggleCredentialsEditing();
      }
    } else {
      this.advertiserFormGroup.disable();
    }
  }

  createAdvertiser(data: IAdvertiser) {
    this._advertiserService.createAdvertiser(data).subscribe({
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.advertiserFormGroup.reset();
        this.error = '';
        this.doRefresh.emit();
      },
    });
  }

  updateAdvertiser(data: IAdvertiser) {
    if(!data.serviceType){
      data.serviceType = this.advertiser()?.serviceType!
    }
    this._advertiserService.updateAdvertiser(this.id, data).subscribe({
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.advertiserFormGroup.reset();
        this.error = '';
        this.id = 0;
        this.toggleEditMode()
        this.doRefresh.emit();
      },
    });
  }

  resetForm() {
    this.advertiserFormGroup.reset({
      agentFirstname: '',
      agentLastname: '',
      email: '',
      password: '',
      companyName: '',
      bussinesName: '',
      phone: '',
      apiUrl: '',
      authToken: '',
      serviceType: '',
    });
  }
}
