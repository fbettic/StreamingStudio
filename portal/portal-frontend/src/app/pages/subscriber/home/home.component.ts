import { Component, ViewChild, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AdvertisingFrameComponent } from '../../../components/advertising-frame/advertising-frame.component';
import { FilmInfoComponent } from '../../../components/film-info/film-info.component';
import { FilmListComponent } from '../../../components/lists/film-list/film-list.component';
import { CustomModalComponent } from '../../../components/shared/custom-modal/custom-modal.component';
import { IFilm } from '../../../models/film.model';
import { FilmService } from '../../../services/film.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    FilmListComponent,
    CustomModalComponent,
    FilmInfoComponent,
    AdvertisingFrameComponent,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;
  private _filmService: FilmService = inject(FilmService);

  private _router: Router = inject(Router);

  films: IFilm[] = [];
  highlightedFilms: IFilm[] = [];
  newFilms: IFilm[] = [];
  mostViewedFilms: IFilm[] = [];
  film: IFilm | null = null;

  constructor() {
    this._filmService.getAllFilms().subscribe({
      next: (res) => {
        this.films = res;
      },
    });

    this._filmService.getHighlightedFilms().subscribe({
      next: (res) => {
        this.highlightedFilms = res;
      },
    });

    this._filmService.getNewFilms().subscribe({
      next: (res) => {
        this.newFilms = res;
      },
    });

    this._filmService.getMostViewedFilms().subscribe({
      next: (res) => {
        this.mostViewedFilms = res;
      },
    });
  }

  onFilmClick(film: IFilm) {
    console.log('🚀 ~ HomeComponent ~ onFilmClick ~ film:', film);

    this.film = film;
    this.modal.open();
  }

  onPlayClick(id: number) {
    this.modal.close();
    this._router.navigateByUrl('/play/' + id);
  }
}
