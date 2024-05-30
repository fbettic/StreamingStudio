import { CommonModule, formatDate } from '@angular/common';
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
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { IAdvertisingRequest } from '../../../../core/models/advertising-request.model';
import { IBannerPriority } from '../../../../core/models/banner-priority.model';
import { IFee } from '../../../../core/models/fee.model';
import { ISizeType } from '../../../../core/models/size-type.model';
import { ITargetCategory } from '../../../../core/models/target-category.model';
import { AdvertiserService } from '../../../../shared/services/advertiser.service';
import { AdvertisingService } from '../../../../shared/services/advertising.service';
import { BannerPriorityService } from '../../../../shared/services/banner-priority.service';
import { FeeService } from '../../../../shared/services/fee.service';
import { SizeTypeService } from '../../../../shared/services/size-type.service';
import { TargetCategoryService } from '../../../../shared/services/target-category.service';

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
  private _targetService: TargetCategoryService = inject(TargetCategoryService);

  private _allPagesFee?: IFee;

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  advertiserId = input<number>(0);

  advertisingFormGroup: FormGroup;
  sizes: ISizeType[] = [];
  priorities: IBannerPriority[] = [];
  targets: ITargetCategory[] = [];
  selectedIds: number[] = [];
  allPagesCheck: boolean = false;
  allPagesFee: number = 0;
  error: string = '';
  id: number = 0;
  fromDate: string = '';
  toDate: string = '';
  useReferenceId: boolean = true;

  constructor() {
    effect(() => {
      if (this.advertiserId() !== 0) {
        this.getAdvertiserServiceType(this.advertiserId());
      }
    });

    this._targetService.getAllTargetCategories().subscribe({
      next: (res) => {
        this.targets = res;
      },
    });

    this._sizeService.getAllSizeTypes().subscribe({
      next: (res) => {
        this.sizes = res;
      },
    });

    this._priorityService.getAllBannerPriorities().subscribe({
      next: (res) => {
        this.priorities = res;
      },
    });

    this._feeService.getAllFees().subscribe({
      next: (res) => {
        this._allPagesFee = res.find((fee) => fee.feeType === 'ALL_PAGES');
        this.allPagesFee = this._allPagesFee!.feeValue;
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
      advertiserId: [this.advertiserId(), [Validators.required]],
      sizeId: ['', [Validators.required]],
      allPagesFeeId: [''],
      priorityId: ['', [Validators.required]],
      redirectUrl: [{ value: '', disabled: true }, [Validators.required]],
      imageUrl: [{ value: '', disabled: true }, [Validators.required]],
      bannerText: [{ value: '', disabled: true }, [Validators.required]],
      referenceId: [{ value: '', disabled: true }, []],
      fromDate: [this.fromDate, [Validators.required]],
      toDate: [this.toDate, [Validators.required]],
      targets: this._formBuilder.array([]),
    });
  }

  @Input()
  set advertisingId(advertisingId: number) {
    this.resetForm();
    if (!advertisingId || advertisingId === 0) {
      this.id = 0;
      this.error = '';
      return;
    }

    this._advertisingService.getAdvertisingById(advertisingId).subscribe({
      next: (advertising) => {
        this.getAdvertiserServiceType(advertising.advertiserId);

        this.id = advertising.advertisingId!;

        this.advertisingFormGroup.setValue({
          advertiserId: advertising.advertiserId,
          sizeId: advertising.sizeId,
          allPagesFeeId: advertising.allPagesFeeId
            ? advertising.allPagesFeeId
            : '',
          priorityId: advertising.priorityId,
          redirectUrl: advertising.redirectUrl,
          imageUrl: advertising.imageUrl,
          bannerText: advertising.bannerText,
          referenceId: advertising.referenceId,
          fromDate: this.getDate(advertising.fromDate),
          toDate: this.getDate(advertising.toDate),
          targets: [],
        });

        advertising.targets?.map((target) => this.addTarget(target));
      },
    });
  }

  private addTarget(id: number): void {
    this.advertisingTargets.push(new FormControl(id));
  }

  private removeTarget(id: number): void {
    this.advertisingTargets.removeAt(this.advertisingTargets.value.indexOf(id));
  }

  isTargetChecked(id: number): boolean {
    return (this.advertisingTargets.value as number[]).includes(id);
  }

  isAllPagesChecked(): boolean {
    return !!this.advertisingFormGroup.get('allPagesFeeId')?.value;
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

  getAdvertiserServiceType(id: number) {
    this._advertiserService.getAdvertiserById(id).subscribe({
      next: (data) => {
        this.useReferenceId = data.serviceType !== 'ACCOUNT';
        this.bannerDataSet();
      },
    });
    this.advertisingFormGroup.patchValue({
      advertiserId: id,
    });
  }

  bannerDataSet(): void {
    if (this.useReferenceId) {
      this.advertisingFormGroup.get('redirectUrl')?.disable();
      this.advertisingFormGroup.get('imageUrl')?.disable();
      this.advertisingFormGroup.get('bannerText')?.disable();
      this.advertisingFormGroup.get('referenceId')?.enable();
      return;
    } else {
      this.advertisingFormGroup.get('redirectUrl')?.enable();
      this.advertisingFormGroup.get('imageUrl')?.enable();
      this.advertisingFormGroup.get('bannerText')?.enable();
      this.advertisingFormGroup.get('referenceId')?.disable();
    }
  }

  onAllPagesCkeck() {
    this.allPagesCheck = !this.allPagesCheck;

    if (this.allPagesCheck) {
      this.advertisingFormGroup.patchValue({
        allPagesFeeId: this._allPagesFee?.feeId,
      });
    } else {
      this.advertisingFormGroup.patchValue({
        allPagesFeeId: '',
      });
    }
  }

  get advertisingTargets() {
    return this.advertisingFormGroup.get('targets') as FormArray;
  }

  submit() {
    const isValid = this.advertisingFormGroup.valid;
    console.log(
      '🚀 ~ AdvertisingFormComponent ~ submit ~ advertiserId:',
      this.advertiserId()
    );

    if (isValid && this.advertisingFormGroup.get('advertiserId')?.value > 0) {
      const data: IAdvertisingRequest = this.advertisingFormGroup.value;
      console.log('🚀 ~ AdvertisingFormComponent ~ submit ~ data:', data);

      this.id != 0
        ? this.updateAdvertising(data)
        : this.createAdvertising(data);
    } else {
      this.error = $localize`Please ensure that all fields are complete and meet the required specifications`;
      this.advertisingFormGroup.markAllAsTouched();
    }
  }

  createAdvertising(data: IAdvertisingRequest) {
    this._advertisingService.createAdvertising(data).subscribe({
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.resetForm();
        this.error = '';
        this.doRefresh.emit();
      },
    });
  }

  updateAdvertising(data: IAdvertisingRequest) {
    this._advertisingService.updateAdvertising(this.id, data).subscribe({
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.resetForm();
        this.error = '';
        this.id = 0;
        this.doRefresh.emit();
      },
    });
  }

  resetForm() {
    this.advertisingTargets.clear();
    this.advertisingFormGroup.reset({
      advertiserId: this.advertiserId,
      sizeId: '',
      allPagesFeeId: '',
      priorityId: '',
      redirectUrl: '',
      imageUrl: '',
      bannerText: '',
      referenceId: '',
      fromDate: this.fromDate,
      toDate: this.toDate,
      targets: this._formBuilder.array([]),
    });
  }

  getDate(date: string | number | Date) {
    return formatDate(date, 'yyyy-MM-dd', 'en');
  }
}
