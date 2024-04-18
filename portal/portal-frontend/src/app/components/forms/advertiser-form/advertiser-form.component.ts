import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ServiceType } from '../../../enums/service-type.enum';
import { CommonModule } from '@angular/common';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { AdvertiserService } from '../../../services/advertiser/advertiser.service';
import { IAdvertiser } from '../../../models/advertiser.model';

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

  newAdvertiserFormGroup: FormGroup;
  error: string = '';
  id: number = 0;

  constructor() {
    this.newAdvertiserFormGroup = this._formBuilder.group({
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
    const service = this.newAdvertiserFormGroup.get('serviceType')?.value;

    if (service === '') {
      this.newAdvertiserFormGroup.get('password')?.disable();
      this.newAdvertiserFormGroup.get('authToken')?.disable();
      this.newAdvertiserFormGroup.get('apiUrl')?.disable();
      return;
    }

    if (service === ServiceType.ACCOUNT) {
      this.newAdvertiserFormGroup.get('password')?.enable();
      this.newAdvertiserFormGroup.get('authToken')?.disable();
      this.newAdvertiserFormGroup.get('apiUrl')?.disable();
    } else {
      this.newAdvertiserFormGroup.get('password')?.disable();
      this.newAdvertiserFormGroup.get('authToken')?.enable();
      this.newAdvertiserFormGroup.get('apiUrl')?.enable();
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
      this.newAdvertiserFormGroup.reset();
      this.error = '';
      return;
    }

    this.id = advertiser.id!;

    this.newAdvertiserFormGroup.setValue({
      agentFirstname: advertiser.agentFirstname,
      agentLastname: advertiser.agentLastname,
      email: advertiser.email,
      password: "",
      companyName: advertiser.companyName,
      bussinesName: advertiser.bussinesName,
      phone: advertiser.phone,
      apiUrl: advertiser.apiUrl,
      authToken: "",
      serviceType: advertiser.serviceType,
    });
  }

  submit() {
    const isValid = this.newAdvertiserFormGroup.valid;
    if (isValid) {
      const data: IAdvertiser = this.newAdvertiserFormGroup.value;
      this.id != 0 ? this.updateAdvertiser(data) : this.createAdvertiser(data);
    } else {
      this.error =
        'Por favor, verifique todos los campos del formulario y asegúrese de que estén completos y cumplan con las especificaciones requeridas.';
      this.newAdvertiserFormGroup.markAllAsTouched();
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
        this.newAdvertiserFormGroup.reset();
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
        this.newAdvertiserFormGroup.reset();
        this.error = '';
        this.id = 0;
        this.doRefresh.emit();
      },
    });
  }
}
