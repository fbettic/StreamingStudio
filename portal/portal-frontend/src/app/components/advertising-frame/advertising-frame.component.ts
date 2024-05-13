import { Component, effect, inject, input, viewChildren } from '@angular/core';
import { IAdvertisingSlot } from '../../models/advertising-slot.model';
import { IAdvertising } from '../../models/advertising.model';
import { AdvertisingService } from '../../services/advertising.service';
import { AdvertisingSlotComponent } from '../advertising-slot/advertising-slot.component';

@Component({
  selector: 'app-advertising-frame',
  standalone: true,
  imports: [AdvertisingSlotComponent],
  templateUrl: './advertising-frame.component.html',
  styleUrl: './advertising-frame.component.scss',
})
export class AdvertisingFrameComponent {
  private _advertisingService: AdvertisingService = inject(AdvertisingService);
  advertisingSlots = viewChildren(AdvertisingSlotComponent);
  home = input<boolean>();

  advertisings: IAdvertising[] = [];
  slots: IAdvertisingSlot[] = [
    {
      slotId: 'top',
      sizeType: 'LARGE',
    },
    {
      slotId: 'rightMedium',
      sizeType: 'MEDIUM',
    },
    {
      slotId: 'rightSmall1',
      sizeType: 'SMALL',
    },
    {
      slotId: 'rightSmall2',
      sizeType: 'SMALL',
    },
    {
      slotId: 'leftMedium',
      sizeType: 'MEDIUM',
    },
    {
      slotId: 'leftSmall1',
      sizeType: 'SMALL',
    },
    {
      slotId: 'leftSmall2',
      sizeType: 'SMALL',
    },
  ];

  constructor() {
    effect(() => {
      this.slots.map((slot) => {});

      this._advertisingService
        .getAdvertisingsForSubscriber(this.slots, !this.home())
        .subscribe({
          next: (res) => {
            this.slots = res;
          },
          complete: () => {
            this.setAdvertisingSlots()
          },
        });
    });
  }

  setAdvertisingSlots() {
    this.slots.map((advertising) => {
      this.advertisingSlots()
        .find((slot) => slot.id === advertising.slotId)
        ?.advertising.update(() => advertising.advertising);
    });
  }
}
