import { Component, ViewChild, inject } from '@angular/core';
import { Router } from '@angular/router';
import { IFilm } from '../../../../core/models/film.model';
import { AdvertisingFrameComponent } from '../../../../shared/components/advertising-frame/advertising-frame.component';
import { CustomModalComponent } from '../../../../shared/components/custom-modal/custom-modal.component';
import { FilmService } from '../../../../shared/services/film.service';
import { FilmInfoComponent } from '../../../components/film-info/film-info.component';
import { FilmListComponent } from '../../../components/film-list/film-list.component';
import { SplashScreenComponent } from '../../../components/splash-screen/splash-screen.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    FilmListComponent,
    CustomModalComponent,
    FilmInfoComponent,
    AdvertisingFrameComponent,
    SplashScreenComponent,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export default class HomeComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;
  private _filmService: FilmService = inject(FilmService);

  private _router: Router = inject(Router);

  private _query: string = '';
  private _searchBy: string = '';

  films: IFilm[] = [];
  highlightedFilms: IFilm[] = [];
  newFilms: IFilm[] = [];
  mostViewedFilms: IFilm[] = [];
  film: IFilm | null = null;

  showPrefilteredFilms: boolean = true;

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
    this.film = film;
    this.modal.open();
  }

  onPlayClick(id: number) {
    this.modal.close();
    this._router.navigateByUrl('/play/' + id);
  }

  searchBy(by: string) {

    this._searchBy = by;
  }

  filterFilms(query: string) {

    this._query = query;
  }

  onSearchClick() {


    this._filmService.getAllFilms(this._query, this._searchBy).subscribe({
      next: (res) => {
        this.films = res;
      },
      complete: () => {
        this.showPrefilteredFilms = false;
      },
    });
  }
}
