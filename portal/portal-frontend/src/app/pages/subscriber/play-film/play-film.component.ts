import { Component, inject } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { AdvertisingFrameComponent } from '../../../components/advertising-frame/advertising-frame.component';
import { AdvertisingSlotComponent } from '../../../components/advertising-slot/advertising-slot.component';
import { CustomModalComponent } from '../../../components/shared/custom-modal/custom-modal.component';
import { FilmService } from '../../../services/film.service';
import { StreamingPlatformService } from '../../../services/streaming-platform.service';
import { SubscriberService } from '../../../services/subscriber.service';

@Component({
  selector: 'app-play-film',
  standalone: true,
  imports: [
    CustomModalComponent,
    AdvertisingSlotComponent,
    AdvertisingFrameComponent,
  ],
  templateUrl: './play-film.component.html',
  styleUrl: './play-film.component.scss',
})
export class PlayFilmComponent {
  private _activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private _router: Router = inject(Router)
  private _sanitizar: DomSanitizer = inject(DomSanitizer);
  private _filmService: FilmService = inject(FilmService);
  private _subscriberService: SubscriberService = inject(SubscriberService);

  id: number = 0;
  filmUrl: SafeResourceUrl | undefined;
  error: string = '';

  constructor() {
    this._activatedRoute.paramMap.subscribe((params) => {
      this.id = parseInt(params.get('id')!);

      this._subscriberService.markSessionAsUsed(this.id).subscribe({
        next: (session) => {
          this.filmUrl = this._sanitizar.bypassSecurityTrustResourceUrl(
            session.sessionUrl
          );
        },
        error: (error) => {
          this._router.navigateByUrl('/home')
        },
      });
    });
  }
}
