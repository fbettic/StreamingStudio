import { Component, inject, input } from '@angular/core';
import { IAssociationRequest } from '../../../core/models/association-request.model';
import { IStreamingPlatformSubscriber } from '../../../core/models/streaming-platform-subscriber.model';
import { StreamingPlatformService } from '../../../shared/services/streaming-platform.service';
import { SubscriberService } from '../../../shared/services/subscriber.service';

@Component({
  selector: 'app-platform-list',
  standalone: true,
  imports: [],
  templateUrl: './platform-list.component.html',
  styleUrl: './platform-list.component.scss',
})
export class PlatformListComponent {
  private _platformService: StreamingPlatformService = inject(
    StreamingPlatformService
  );
  private _subscriberService: SubscriberService = inject(SubscriberService);

  platforms: IStreamingPlatformSubscriber[] = [];
  subscriberId = input<number>(0);
  authUrl = '';

  constructor() {
    this.authUrl = '';
    this.getStreamingPlatforms();
  }

  getStreamingPlatforms() {
    this._platformService.getStreamingPlatformSubscriber().subscribe({
      next: (res) => {
        this.platforms = res;
      },
    });
  }

  link(data: IStreamingPlatformSubscriber, linkType: string) {
    const request: IAssociationRequest = {
      platformId: data.platformId,
      subscriberId: this.subscriberId(),
      associationType: linkType,
    };

    this._subscriberService.createAsociationRequest(request).subscribe({
      next: (res) => {
        this.authUrl = res.authUrl;
      },
      complete: () => {
        window.location.href = this.authUrl;
        this.authUrl = '';
      },
    });
  }

  unlink(data: IStreamingPlatformSubscriber) {
    this._subscriberService.cancelAssociation(data.platformId).subscribe({
      next: () => {
        this.getStreamingPlatforms();
      }
    });
  }
}
