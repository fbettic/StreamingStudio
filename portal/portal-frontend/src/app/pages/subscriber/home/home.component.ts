import { Component, ViewChild, inject } from '@angular/core';
import { Router } from '@angular/router';
import { AdvertisingSlotComponent } from '../../../components/advertising-slot/advertising-slot.component';
import { FilmInfoComponent } from '../../../components/film-info/film-info.component';
import { FilmListComponent } from '../../../components/lists/film-list/film-list.component';
import { CustomModalComponent } from '../../../components/shared/custom-modal/custom-modal.component';
import { IAdvertisingSlot } from '../../../models/advertising-slot.model';
import { IAdvertising } from '../../../models/advertising.model';
import { IFilm } from '../../../models/film.model';
import { AdvertisingService } from '../../../services/advertising.service';
import { FilmService } from '../../../services/film.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    FilmListComponent,
    CustomModalComponent,
    FilmInfoComponent,
    AdvertisingSlotComponent,
  ],
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
    this._filmService.getAllFilms().subscribe({
      next: (res) => {
        this.films = res;
      },
    });

    this._advertisingService.getAdvertisingsToShow(this.slots).subscribe({
      next: (res) => {
        this.slots = res;
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
    this.modal.close();
    this._router.navigateByUrl('/play/' + id);
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
