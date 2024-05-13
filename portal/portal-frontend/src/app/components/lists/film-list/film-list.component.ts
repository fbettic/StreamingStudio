import { Component, EventEmitter, Output, input } from '@angular/core';
import { IFilm } from '../../../models/film.model';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-film-list',
  standalone: true,
  imports: [NgbModule, CommonModule],
  templateUrl: './film-list.component.html',
  styleUrl: './film-list.component.scss',
})
export class FilmListComponent {
  @Output() onFilmClick: EventEmitter<IFilm> = new EventEmitter<IFilm>();
  type = input<string>('grid')

  films = input<IFilm[]>([]);


}
