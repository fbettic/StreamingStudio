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

@Component({
  selector: 'app-play-film',
  standalone: true,
  imports: [CustomModalComponent],
  templateUrl: './play-film.component.html',
  styleUrl: './play-film.component.scss',
})
export class PlayFilmComponent {
  private _activatedRoute: ActivatedRoute = inject(ActivatedRoute);
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

  constructor() {
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
}
