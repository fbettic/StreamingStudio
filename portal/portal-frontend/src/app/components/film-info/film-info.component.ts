import {
  Component,
  EventEmitter,
  Output,
  effect,
  inject,
  input,
} from '@angular/core';
import { Router } from '@angular/router';
import { IFilm } from '../../models/film.model';
import { ISessionRequest } from '../../models/session-request.model';
import { IStreamingPlatformSubscriber } from '../../models/streaming-platform-subscriber.model';
import { StreamingPlatformService } from '../../services/streaming-platform.service';
import { SubscriberService } from '../../services/subscriber.service';

@Component({
  selector: 'app-film-info',
  standalone: true,
  imports: [],
  templateUrl: './film-info.component.html',
  styleUrl: './film-info.component.scss',
})
export class FilmInfoComponent {
  @Output() onPlayClick: EventEmitter<number> = new EventEmitter<number>();
  private _router: Router = inject(Router);
  private _platformService: StreamingPlatformService = inject(
    StreamingPlatformService
  );
  private _subscriberService: SubscriberService = inject(SubscriberService);

  platforms: IStreamingPlatformSubscriber[] = [];
  filteredPlatforms: IStreamingPlatformSubscriber[] = [];
  film = input<IFilm | null>();
  sessionId: number = 0;
  error: string = '';

  constructor() {
    effect(() => {
      const filmPlatforms = this.film()?.platforms.split(',');
      this.filteredPlatforms = this.platforms.filter(
        (platform) =>
          filmPlatforms?.includes(platform.platformName) &&
          platform.linked == true
      );
    });

    this._platformService.getStreamingPlatformSubscriber().subscribe({
      next: (platforms) => {
        console.log(
          '🚀 ~ FilmInfoComponent ~ this._platformService.getStreamingPlatformSubscriber ~ platforms:',
          platforms
        );

        this.platforms = platforms;
      },
      complete: () => {
        console.log(
          '🚀 ~ FilmInfoComponent ~ this._platformService.getStreamingPlatformSubscriber ~ platforms:',
          this.platforms
        );
      },
    });
  }

  onPlatformSelect(platformId: number) {
    const data: ISessionRequest = {
      platformId: platformId,
      filmCode: this.film()?.filmCode!,
    };
    this._subscriberService.createSession(data).subscribe({
      next: (session) => {
        this.sessionId = session.sessionId;
      },
      error: (error) => {
        this.error = error.error;
      },
      complete: () => {
        this.onPlayClick.emit(this.sessionId);
      },
    });
  }
}
