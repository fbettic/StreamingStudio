import { Component, CUSTOM_ELEMENTS_SCHEMA, Input } from '@angular/core';
import { NgFor } from '@angular/common';
import { Film } from 'src/app/models';
import { FilmCardComponent } from '../film-card';

@Component({
  selector: 'app-films-carousel',
  standalone: true,
  imports: [NgFor, FilmCardComponent],
  templateUrl: './films-carousel.component.html',
  styleUrls: ['./films-carousel.component.scss'],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class FilmsCarouselComponent {
  @Input() films: Film[] = []
}
