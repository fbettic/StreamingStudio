import { Component, inject, input } from '@angular/core';
import { IStreamingPlatform } from '../../../models/streaming-platform.model';
import { StreamingPlatformService } from '../../../services/streaming-platform.service';
import { IAssociationRequest } from '../../../models/association-request.model';
import { SubscriberService } from '../../../services/subscriber/subscriber.service';
import { Router } from '@angular/router';
import { IStreamingPlatformSubscriber } from '../../../models/streaming-platform-subscriber.model';

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
      error: (error) => {
        console.log(
          '🚀 ~ PlatformListComponent ~ this._subscriberService.createAsociationRequest ~ error:',
          error
        );

        alert('Se ha producido un error: ' + error);
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
      },
      error: (error) => {
        console.log(
          '🚀 ~ PlatformListComponent ~ this._subscriberService.createAsociationRequest ~ error:',
          error
        );

        alert('Se ha producido un error: ' + error);
      },
    });
  }
}
