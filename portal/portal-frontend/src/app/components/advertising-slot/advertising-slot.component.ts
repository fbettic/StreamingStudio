import { Component, Input, inject, input, model } from '@angular/core';
import { IAdvertisingClick } from '../../models/advertising-click.model';
import { IAdvertising } from '../../models/advertising.model';
import { AdvertisingService } from '../../services/advertising.service';

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
    this._advertisingService.createSubscriberAdvertisingClick(data).subscribe({
      error: (error) => {
        console.log(
          '🚀 ~ AdvertisingSlotComponent ~ this._advertisingService.createSubscriberAdvertisingClick ~ error:',
          error.error
        );
      },
    });
  }
}
