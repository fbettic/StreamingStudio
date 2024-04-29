import { Component, EventEmitter, Output, inject, input } from '@angular/core';
import { IFilm } from '../../models/film.model';
import { FilmService } from '../../services/film.service';

@Component({
  selector: 'app-film-info',
  standalone: true,
  imports: [],
  templateUrl: './film-info.component.html',
  styleUrl: './film-info.component.scss',
})
export class FilmInfoComponent {
  @Output() onPlayClick: EventEmitter<number> = new EventEmitter<number>();
  film = input<IFilm | null>();

  
}
