import { ViewportScroller } from '@angular/common';
import { Component, inject } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { IFilm } from '../../../../core/models/film.model';
import { AdvertisingFrameComponent } from '../../../../shared/components/advertising-frame/advertising-frame.component';
import { AdvertisingSlotComponent } from '../../../../shared/components/advertising-slot/advertising-slot.component';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { FilmService } from '../../../../shared/services/film.service';
import { SubscriberService } from '../../../../shared/services/subscriber.service';

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
export default class PlayFilmComponent {
  private _viewportScroller: ViewportScroller = inject(ViewportScroller);
  private _activatedRoute: ActivatedRoute = inject(ActivatedRoute);
  private _router: Router = inject(Router);
  private _sanitizar: DomSanitizer = inject(DomSanitizer);
  private _filmService: FilmService = inject(FilmService);
  private _subscriberService: SubscriberService = inject(SubscriberService);

  id: number = 0;
  film?: IFilm;
  filmUrl: SafeResourceUrl | undefined;
  error: string = '';

  constructor() {
    this._viewportScroller.scrollToPosition([0, 0]);
    this._activatedRoute.paramMap.subscribe((params) => {
      this.id = parseInt(params.get('id')!);

      this._subscriberService.markSessionAsUsed(this.id).subscribe({
        next: (session) => {
          this.filmUrl = this._sanitizar.bypassSecurityTrustResourceUrl(
            session.sessionUrl
          );
          this.getFilmInfo(session.filmId);
        },

        error: () => {
          this._router.navigateByUrl('/home');
        },
      });
    });
  }

  getFilmInfo(filmId: number) {
    this._filmService.getFilmById(filmId).subscribe({
      next: (film) => {
        this.film = film;
      },
    });
  }
}
