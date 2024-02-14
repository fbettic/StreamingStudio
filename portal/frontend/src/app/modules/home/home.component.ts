import { Component, OnInit } from '@angular/core';
import { Film } from 'src/app/models';
import { FilmsResourceService } from 'src/app/services/films-resource.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  films: Film[] = [];

  constructor(private _service: FilmsResourceService) {}

  ngOnInit(): void {
    this._service.getFilms().subscribe((films) => {
      console.log({ films });
      this.films = films;
    });
  }
}
