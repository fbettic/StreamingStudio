import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { IAdvertising } from '../../../models/advertising.model';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AdvertisingService } from '../../../services/advertising.service';
import { CommonModule } from '@angular/common';

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

  @Output() doRefresh: EventEmitter<void> = new EventEmitter<void>();

  newAdvertisingFormGroup: FormGroup;
  error: string = '';
  id: number = 0;

  constructor() {
    this.newAdvertisingFormGroup = this._formBuilder.group({
      advertiserId: ['', [Validators.required]],
      sizeId: ['', [Validators.required]],
      allPagesFeeId: ['', [Validators.required]],
      priorityId: ['', [Validators.required]],
      redirectUrl: [{ value: '', disabled: true }, [Validators.required]],
      imageUrl: [{ value: '', disabled: true }, [Validators.required]],
      bannerText: [{ value: '', disabled: true }, [Validators.required]],
      bannerId: [{ value: '', disabled: true }, [Validators.required]],
      fromDate: ['', [Validators.required]],
      toDate: ['', [Validators.required]],
    });
  }

  @Input()
  set advertising(advertising: IAdvertising | null) {
    console.log(
      '🚀 ~ AdvertisingFormComponent ~ setsetAdvertising ~ advertising:',
      advertising
    );

    if (!advertising) {
      this.id = 0;
      this.newAdvertisingFormGroup.reset();
      this.error = '';
      return;
    }

    this.id = advertising.advertisingId!;

    this.newAdvertisingFormGroup.setValue({
      advertiserId: advertising.advertiserId,
      sizeId: advertising.sizeId,
      allPagesFeeId: advertising.allPagesFeeId,
      priorityId: advertising.priorityId,
      redirectUrl: advertising.redirectUrl,
      imageUrl: advertising.imageUrl,
      bannerText: advertising.bannerText,
      bannerId: advertising.bannerId,
      fromDate: advertising.fromDate,
      toDate: advertising.toDate,
    });
  }
}
