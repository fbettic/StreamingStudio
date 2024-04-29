import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ServiceType } from '../../../enums/service-type.enum';
import { IStreamingPlatform } from '../../../models/streaming-platform.model';
import { StreamingPlatformService } from '../../../services/streaming-platform.service';
import { CommonModule } from '@angular/common';
import { FeeService } from '../../../services/fee.service';
import { IFee } from '../../../models/fee.model';

@Component({
  selector: 'app-platform-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './platform-form.component.html',
  styleUrl: './platform-form.component.scss',
})
export class PlatformFormComponent {
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _feeService: FeeService = inject(FeeService);
  private _streamingPlatformService: StreamingPlatformService = inject(
    StreamingPlatformService
  );

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  streamingPlatformFormGroup: FormGroup;
  error: string = '';
  id: number = 0;

  loginFees: IFee[] = [];
  signupFees: IFee[] = [];

  constructor() {
    this._feeService.getAllFees().subscribe({
      next: (res) => {
        this.loginFees = res.filter((fee) => fee.feeType.includes('LOGIN'));
        this.signupFees = res.filter((fee) => fee.feeType.includes('SIGNUP'));
      },
      error: (error) => {
        console.log(
          '🚀 ~ AdvertisingFormComponent ~ this._priorityService.getAllBannerPriorities ~ error:',
          error
        );
      },
    });

    this.streamingPlatformFormGroup = this._formBuilder.group({
      platformName: ['', Validators.required],
      email: ['', Validators.required],
      apiUrl: ['', Validators.required],
      authToken: ['', Validators.required],
      signupFeeId: ['', Validators.required],
      loginFeeId: ['', Validators.required],
      serviceType: ['', Validators.required],
    });
  }

  onServiceTypeChange(): void {
    const service = this.streamingPlatformFormGroup.get('serviceType')?.value;

    if (service === '') {
      this.streamingPlatformFormGroup.get('password')?.disable();
      this.streamingPlatformFormGroup.get('authToken')?.disable();
      this.streamingPlatformFormGroup.get('apiUrl')?.disable();
      return;
    }

    if (service === ServiceType.ACCOUNT) {
      this.streamingPlatformFormGroup.get('password')?.enable();
      this.streamingPlatformFormGroup.get('authToken')?.disable();
      this.streamingPlatformFormGroup.get('apiUrl')?.disable();
    } else {
      this.streamingPlatformFormGroup.get('password')?.disable();
      this.streamingPlatformFormGroup.get('authToken')?.enable();
      this.streamingPlatformFormGroup.get('apiUrl')?.enable();
    }
  }

  @Input()
  set streamingPlatform(streamingPlatform: IStreamingPlatform | null) {
    console.log(
      '🚀 ~ StreamingPlatformFormComponent ~ setsetStreamingPlatform ~ streamingPlatform:',
      streamingPlatform
    );

    if (!streamingPlatform) {
      this.id = 0;
      this.resetForm();
      this.error = '';
      return;
    }

    this.id = streamingPlatform.platformId;

    this.streamingPlatformFormGroup.setValue({
      platformName: streamingPlatform.platformName,
      email: streamingPlatform.email,
      apiUrl: streamingPlatform.apiUrl,
      authToken: streamingPlatform.authToken,
      signupFeeId: streamingPlatform.signupFeeId,
      loginFeeId: streamingPlatform.loginFeeId,
      serviceType: streamingPlatform.serviceType,
    });
  }

  submit() {
    const isValid = this.streamingPlatformFormGroup.valid;
    if (isValid) {
      const data: IStreamingPlatform = this.streamingPlatformFormGroup.value;
      this.id != 0
        ? this.updateStreamingPlatform(data)
        : this.createStreamingPlatform(data);
    } else {
      this.error =
        'Por favor, verifique todos los campos del formulario y asegúrese de que estén completos y cumplan con las especificaciones requeridas.';
      this.streamingPlatformFormGroup.markAllAsTouched();
    }
  }

  createStreamingPlatform(data: IStreamingPlatform) {
    this._streamingPlatformService.createStreamingPlatform(data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ StreamingPlatformFormComponent ~ this._streamingPlatformService.createStreamingPlatform ~ error:',
          error
        );

        this.error = error.error;
      },
      complete: () => {
        this.streamingPlatformFormGroup.reset();
        this.error = '';
        this.doRefresh.emit();
      },
    });
  }

  updateStreamingPlatform(data: IStreamingPlatform) {
    this._streamingPlatformService
      .updateStreamingPlatform(this.id, data)
      .subscribe({
        error: (error) => {
          console.log(
            '🚀 ~ StreamingPlatformFormComponent ~ this._streamingPlatformService.createStreamingPlatform ~ error:',
            error
          );

          this.error = error.error;
        },
        complete: () => {
          this.streamingPlatformFormGroup.reset();
          this.error = '';
          this.id = 0;
          this.doRefresh.emit();
        },
      });
  }

  resetForm() {
    this.streamingPlatformFormGroup.reset({
      platformName: '',
      email: '',
      apiUrl: '',
      authToken: '',
      signupFeeId: '',
      loginFeeId: '',
      serviceType: '',
    });
  }
}
