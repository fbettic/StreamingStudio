import { Component, ViewChild, inject } from '@angular/core';
import { FilmService } from '../../../services/film.service';
import { IFilm } from '../../../models/film.model';
import { IAdvertising } from '../../../models/advertising.model';
import { CustomModalComponent } from '../../../components/shared/custom-modal/custom-modal.component';
import { AdvertisingService } from '../../../services/advertising.service';
import { FilmListComponent } from '../../../components/lists/film-list/film-list.component';
import { FilmInfoComponent } from '../../../components/film-info/film-info.component';
import { Router } from '@angular/router';
import { routes } from '../../../app.routes';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FilmListComponent, CustomModalComponent, FilmInfoComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  @ViewChild(CustomModalComponent) modal!: CustomModalComponent;
  private _filmService: FilmService = inject(FilmService);
  private _advertisingService: AdvertisingService = inject(AdvertisingService);
  private _router: Router = inject(Router);

  films: IFilm[] = [];
  film: IFilm | null = null;
  advertisings: IAdvertising[] = [];

  constructor() {
    this._filmService.getAllFilms().subscribe({
      next: (res) => {
        this.films = res;
      },
    });

    this._advertisingService.getAllAdvertisings().subscribe({
      next: (res) => {
        this.advertisings = res;
      },
    });
  }

  onFilmClick(id: number) {
    this._filmService.getFilmById(id).subscribe({
      next: (res) => {
        this.film = res;
      },
      complete: () => {
        this.modal.open();
      },
    });
  }

  onPlayClick(id: number) {
    this.modal.close()
    this._router.navigateByUrl('/play/' + id);
  }
}
