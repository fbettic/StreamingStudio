import { NgFor } from '@angular/common';
import { Component } from '@angular/core';
import {MatGridListModule} from '@angular/material/grid-list';
import { Film } from 'src/app/models';
import { Films } from 'src/assets/placeholder';
import { FilmCardComponent } from '../film-card';

@Component({
  selector: 'app-films-grid',
  standalone: true,
  imports: [MatGridListModule, NgFor, FilmCardComponent],
  templateUrl: './films-grid.component.html',
  styleUrls: ['./films-grid.component.scss']
})

export class FilmsGridComponent {
  tiles: Film[]
  posterHeight = 450
  posterWidth = 300

  constructor() {
    this.tiles = Films
  }
}
