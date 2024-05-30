import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output, input } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { IFilm } from '../../../core/models/film.model';

@Component({
  selector: 'app-film-list',
  standalone: true,
  imports: [NgbModule, CommonModule],
  templateUrl: './film-list.component.html',
  styleUrl: './film-list.component.scss',
})
export class FilmListComponent {
  @Output() onFilmClick: EventEmitter<IFilm> = new EventEmitter<IFilm>();
  type = input<string>('grid');

  films = input<IFilm[]>([]);
}
