import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ServiceType } from '../../../enums/service-type.enum';
import { IAdvertiser } from '../../../models/advertiser.model';
import { AdvertiserService } from '../../../services/advertiser.service';

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

  advertiserFormGroup: FormGroup;
  error: string = '';
  id: number = 0;

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

  @Input()
  set advertiser(advertiser: IAdvertiser | null) {
    console.log(
      '🚀 ~ AdvertiserFormComponent ~ setsetAdvertiser ~ advertiser:',
      advertiser
    );

    if (!advertiser) {
      this.id = 0;
      this.resetForm()
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
  }

  submit() {
    const isValid = this.advertiserFormGroup.valid;
    if (isValid) {
      const data: IAdvertiser = this.advertiserFormGroup.value;
      this.id != 0 ? this.updateAdvertiser(data) : this.createAdvertiser(data);
    } else {
      this.error =
        'Por favor, verifique todos los campos del formulario y asegúrese de que estén completos y cumplan con las especificaciones requeridas.';
      this.advertiserFormGroup.markAllAsTouched();
    }
  }

  createAdvertiser(data: IAdvertiser) {
    this._advertiserService.createAdvertiser(data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ AdvertiserFormComponent ~ this._advertiserService.createAdvertiser ~ error:',
          error
        );

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
    this._advertiserService.updateAdvertiser(this.id, data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ AdvertiserFormComponent ~ this._advertiserService.createAdvertiser ~ error:',
          error
        );

        this.error = error.error;
      },
      complete: () => {
        this.advertiserFormGroup.reset();
        this.error = '';
        this.id = 0;
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
