import { Component, EventEmitter, Output, input } from '@angular/core';
import { IFilm } from '../../../models/film.model';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-film-list',
  standalone: true,
  imports: [NgbModule],
  templateUrl: './film-list.component.html',
  styleUrl: './film-list.component.scss',
})
export class FilmListComponent {
  @Output() onFilmClick: EventEmitter<number> = new EventEmitter<number>();
  films = input<IFilm[]>([]);

  
}
