import { Component, ViewChild, inject } from '@angular/core';
import { CustomModalComponent } from '../../../components/shared/custom-modal/custom-modal.component';
import { FilmService } from '../../../services/film.service';
import { ActivatedRoute } from '@angular/router';
import { IFilm } from '../../../models/film.model';
import { StreamingPlatformService } from '../../../services/streaming-platform.service';
import { IStreamingPlatformSubscriber } from '../../../models/streaming-platform-subscriber.model';
import { SubscriberService } from '../../../services/subscriber/subscriber.service';
import { ISessionRequest } from '../../../models/session-request.model';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { AdvertisingSlotComponent } from '../../../components/advertising-slot/advertising-slot.component';
import { IAdvertisingSlot } from '../../../models/advertising-slot.model';
import { AdvertisingService } from '../../../services/advertising.service';

@Component({
  selector: 'app-play-film',
  standalone: true,
  imports: [CustomModalComponent,AdvertisingSlotComponent],
  templateUrl: './play-film.component.html',
  styleUrl: './play-film.component.scss',
})
export class PlayFilmComponent {
  private _activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private _advertisingService: AdvertisingService = inject(AdvertisingService);
  private _sanitizar: DomSanitizer = inject(DomSanitizer);
  private _filmService: FilmService = inject(FilmService);
  private _platformService: StreamingPlatformService = inject(
    StreamingPlatformService
  );
  private _subscriberService: SubscriberService = inject(SubscriberService);

  id: number = 0;
  film: IFilm | null = null;
  filmUrl: SafeResourceUrl | undefined;
  error: string = '';
  platforms: IStreamingPlatformSubscriber[] = [];
  slots: IAdvertisingSlot[] = [
    {
      slotId: 'top',
      sizeType: 'LARGE',
    },
    {
      slotId: 'right-medium',
      sizeType: 'MEDIUM',
    },
    {
      slotId: 'right-small1',
      sizeType: 'SMALL',
    },
    {
      slotId: 'right-small2',
      sizeType: 'SMALL',
    },
    {
      slotId: 'left-medium',
      sizeType: 'MEDIUM',
    },
    {
      slotId: 'left-small1',
      sizeType: 'SMALL',
    },
    {
      slotId: 'left-small2',
      sizeType: 'SMALL',
    },
  ];

  constructor() {
    this._advertisingService.getAdvertisingsToShow(this.slots).subscribe({
      next: (res) => {
        this.slots = res;
      },
    });
    this._activatedRoute.paramMap.subscribe((params) => {
      this.id = parseInt(params.get('id')!);
      this._filmService.getFilmById(this.id).subscribe({
        next: (film) => {
          this.film = film;
        },
        error: (error) => {
          console.log(
            '🚀 ~ PlayFilmComponent ~ this._filmService.getFilmById ~ error.error:',
            error.error
          );
          this.error = error.error;
          alert(error.error);
        },
        complete: () => {
          this.getValidPlatformsData();
        },
      });
    });
  }

  getValidPlatformsData() {
    this._platformService.getStreamingPlatformSubscriber().subscribe({
      next: (platforms) => {
        const platformNames = this.film?.platforms?.split(',');
        this.platforms = platforms.filter(
          (platform) =>
            platformNames?.includes(platform.platformName) &&
            platform.linked == true
        );
      },
      complete: () => {},
    });
  }

  onPlatformSelect(platformId: number) {
    const data: ISessionRequest = {
      platformId: platformId,
      filmCode: this.film?.filmCode!,
    };
    this._subscriberService.createSession(data).subscribe({
      next: (session) => {
        this.filmUrl = this._sanitizar.bypassSecurityTrustResourceUrl(
          session.sessionUrl
        );
      },
      error: (error) => {
        this.error = error.error;
      },
    });
  }
  getSlotData(id: string): IAdvertisingSlot {
    return this.slots.find((slot) => (slot.slotId == id))!;
  }

  getHeight(id: string): string {
    return this.getSlotData(id).advertising?.height!;
  }
  getWidth(id: string): string {
    return this.getSlotData(id).advertising?.width!;
  }
  getUrl(id: string): string {
    return this.getSlotData(id).advertising?.redirectUrl!;
  }
  getText(id: string): string {
    return this.getSlotData(id).advertising?.bannerText!;
  }
  getImage(id: string): string {
    return this.getSlotData(id).advertising?.imageUrl!;
  }
}
