import {
  Component,
  EventEmitter,
  Input,
  Output,
  effect,
  inject,
  input,
} from '@angular/core';
import { IAdvertising } from '../../../models/advertising.model';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AdvertisingService } from '../../../services/advertising.service';
import { CommonModule, formatDate } from '@angular/common';
import { BannerPriorityService } from '../../../services/banner-priority.service';
import { SizeTypeService } from '../../../services/size-type.service';
import { ISizeType } from '../../../models/size-type.model';
import { IBannerPriority } from '../../../models/banner-priority.model';
import { FeeService } from '../../../services/fee.service';
import { IAdvertisingRequest } from '../../../models/advertising-request.model';
import { IFee } from '../../../models/fee.model';
import { from } from 'rxjs';
import { AdvertiserService } from '../../../services/advertiser.service';

@Component({
  selector: 'app-advertising-form',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './advertising-form.component.html',
  styleUrl: './advertising-form.component.scss',
})
export class AdvertisingFormComponent {
  private _formBuilder: FormBuilder = inject(FormBuilder);
  private _advertisingService: AdvertisingService = inject(AdvertisingService);
  private _advertiserService: AdvertiserService = inject(AdvertiserService);
  private _priorityService: BannerPriorityService = inject(
    BannerPriorityService
  );
  private _sizeService: SizeTypeService = inject(SizeTypeService);
  private _feeService: FeeService = inject(FeeService);
  private _allPagesFee?: IFee;

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  advertiserId = input<number>(0);

  advertisingFormGroup: FormGroup;
  sizes: ISizeType[] = [];
  priorities: IBannerPriority[] = [];
  allPagesCheck: boolean = false;
  allPagesFee: number = 0;
  error: string = '';
  advertisingId: number = 0;
  fromDate: string = '';
  toDate: string = '';
  useBannerId: boolean = true;

  constructor() {

    effect(()=>{
      if(this.advertiserId() !== 0){
        this.getAdvertiserServiceType(this.advertiserId())
      }
    })
    
    
    this._sizeService.getAllSizeTypes().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ AdvertisingFormComponent ~ this._sizeService.getAllSizeTypes ~ res:',
          res
        );
        this.sizes = res;
      },
      error: (error) => {
        console.log(
          '🚀 ~ AdvertisingFormComponent ~ this._sizeService.getAllSizeTypes ~ error:',
          error
        );
      },
    });

    this._priorityService.getAllBannerPriorities().subscribe({
      next: (res) => {
        console.log(
          '🚀 ~ AdvertisingFormComponent ~ this._priorityService.getAllBannerPriorities ~ res:',
          res
        );
        this.priorities = res;
      },
      error: (error) => {
        console.log(
          '🚀 ~ AdvertisingFormComponent ~ this._priorityService.getAllBannerPriorities ~ error:',
          error
        );
      },
    });

    this._feeService.getAllFees().subscribe({
      next: (res) => {
        this._allPagesFee = res.find((fee) => fee.feeType === 'ALLPAGES');
        this.allPagesFee = this._allPagesFee!.feeValue;
      },
      error: (error) => {
        console.log(
          '🚀 ~ AdvertisingFormComponent ~ this._priorityService.getAllBannerPriorities ~ error:',
          error
        );
      },
    });

    const todayDate = new Date();
    this.fromDate = formatDate(todayDate, 'yyyy-MM-dd', 'en');
    this.toDate = formatDate(
      todayDate.setFullYear(todayDate.getFullYear() + 1),
      'yyyy-MM-dd',
      'en'
    );

    this.advertisingFormGroup = this._formBuilder.group({
      advertiserId: [this.advertiserId, [Validators.required]],
      sizeId: ['', [Validators.required]],
      allPagesFeeId: [''],
      priorityId: ['', [Validators.required]],
      redirectUrl: [{ value: '', disabled: true }, [Validators.required]],
      imageUrl: [{ value: '', disabled: true }, [Validators.required]],
      bannerText: [{ value: '', disabled: true }, [Validators.required]],
      bannerId: [{ value: '', disabled: true }, [Validators.required]],
      fromDate: [this.fromDate, [Validators.required]],
      toDate: [this.toDate, [Validators.required]],
    });
  }

  @Input()
  set advertising(advertising: IAdvertising | null) {
    console.log(
      '🚀 ~ AdvertisingFormComponent ~ setsetAdvertising ~ advertising:',
      advertising
    );

    if (!advertising) {
      this.advertisingId = 0;
      this.resetForm();
      this.error = '';
      return;
    }

    this.getAdvertiserServiceType(advertising.advertiserId)

    this.advertisingId = advertising.advertisingId!;
    

    this.advertisingFormGroup.setValue({
      advertiserId: advertising.advertiserId,
      sizeId: advertising.sizeId,
      allPagesFeeId: advertising.allPagesFeeId,
      priorityId: advertising.priorityId,
      redirectUrl: advertising.redirectUrl,
      imageUrl: advertising.imageUrl,
      bannerText: advertising.bannerText,
      bannerId: advertising.bannerId,
      fromDate: this.getDate(advertising.fromDate),
      toDate: this.getDate(advertising.toDate),
    });

    this.allPagesFee = advertising.allPagesFee;
  }

  getAdvertiserServiceType(id: number) {
    this._advertiserService.getAdvertiserById(id).subscribe({
      next: (data) => {
        console.log("🚀 ~ AdvertisingFormComponent ~ this._advertiserService.getAdvertiserById ~ data:", data)
        this.useBannerId = data.serviceType !== 'ACCOUNT';
        this.bannerDataSet()
      },
    });
  }

  bannerDataSet(): void {
    if (this.useBannerId) {
      this.advertisingFormGroup.get('redirectUrl')?.disable();
      this.advertisingFormGroup.get('imageUrl')?.disable();
      this.advertisingFormGroup.get('bannerText')?.disable();
      this.advertisingFormGroup.get('bannerId')?.enable();
      return;
    } else {
      this.advertisingFormGroup.get('redirectUrl')?.enable();
      this.advertisingFormGroup.get('imageUrl')?.enable();
      this.advertisingFormGroup.get('bannerText')?.enable();
      this.advertisingFormGroup.get('bannerId')?.disable();
    }
  }

  onAllPagesCkeck() {
    this.allPagesCheck = !this.allPagesCheck;

    if (this.allPagesCheck) {
      this.advertisingFormGroup.patchValue({
        allPagesFee: this._allPagesFee?.feeId,
      });
    } else {
      this.advertisingFormGroup.patchValue({
        allPagesFee: '',
      });
    }
  }

  submit() {
    const isValid = this.advertisingFormGroup.valid;
    console.log("🚀 ~ AdvertisingFormComponent ~ submit ~ this.advertisingFormGroup.value:", this.advertisingFormGroup.value)

    if (isValid && this.advertisingFormGroup.value.advertiserId !== 0) {
      const data: IAdvertisingRequest = this.advertisingFormGroup.value;
      this.advertisingId != 0
        ? this.updateAdvertising(data)
        : this.createAdvertising(data);
    } else {
      
      this.error =
        'Por favor, verifique todos los campos del formulario y asegúrese de que estén completos y cumplan con las especificaciones requeridas.';
      this.advertisingFormGroup.markAllAsTouched();
    }
  }

  createAdvertising(data: IAdvertisingRequest) {
    this._advertisingService.createAdvertising(data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ AdvertisingFormComponent ~ this._advertisingService.createAdvertising ~ error:',
          error
        );

        this.error = error.error;
      },
      complete: () => {
        this.advertisingFormGroup.reset();
        this.error = '';
        this.doRefresh.emit();
      },
    });
  }

  updateAdvertising(data: IAdvertisingRequest) {
    this._advertisingService
      .updateAdvertising(this.advertisingId, data)
      .subscribe({
        error: (error) => {
          console.log(
            '🚀 ~ AdvertisingFormComponent ~ this._advertisingService.updateAdvertising ~ error:',
            error
          );

          this.error = error.error;
        },
        complete: () => {
          this.advertisingFormGroup.reset();
          this.error = '';
          this.advertisingId = 0;
          this.doRefresh.emit();
        },
      });
  }

  resetForm() {
    this.advertisingFormGroup.reset({
      advertiserId: this.advertiserId,
      sizeId: '',
      allPagesFeeId: '',
      priorityId: '',
      redirectUrl: '',
      imageUrl: '',
      bannerText: '',
      bannerId: '',
      fromDate: this.fromDate,
      toDate: this.toDate,
    });
  }

  getDate(date: string | number | Date) {
    return formatDate(date, 'yyyy-MM-dd', 'en');
  }
}
