import { Component, Input, inject, model } from '@angular/core';
import { IAdvertisingClick } from '../../../core/models/advertising-click.model';
import { IAdvertising } from '../../../core/models/advertising.model';
import { AdvertisingService } from '../../../shared/services/advertising.service';

@Component({
  selector: 'app-advertising-slot',
  standalone: true,
  imports: [],
  templateUrl: './advertising-slot.component.html',
  styleUrl: './advertising-slot.component.scss',
})
export class AdvertisingSlotComponent {
  private _advertisingService: AdvertisingService = inject(AdvertisingService);

  advertising = model<IAdvertising>();

  @Input() id: string = '';
  @Input() width: string = '';
  @Input() height: string = '';

  onClick() {
    const data: IAdvertisingClick = {
      advertisingId: this.advertising()?.advertisingId!,
      clickedAt: new Date(),
    };
    this._advertisingService
      .createSubscriberAdvertisingClick(data)
      .subscribe({});
  }
}
