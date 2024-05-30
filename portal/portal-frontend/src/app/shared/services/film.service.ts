import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IFilm } from '../../core/models/film.model';

@Injectable({
  providedIn: 'root',
})
export class FilmService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  getAllFilms(query?: string, by?: string): Observable<IFilm[]> {
    let url: string = 'films';

    if (query) {
      url = url.concat('?query=', query);
      if (by) {
        url = url.concat('&by=', by);
      }
    }

    return this.http
      .get<IFilm[]>(environment.urlApi + url)
      .pipe(catchError(this.handleError));
  }

  getFilmById(id: number): Observable<IFilm> {
    return this.http.get<IFilm>(environment.urlApi + 'films/' + id);
  }

  getHighlightedFilms(): Observable<IFilm[]> {
    return this.http
      .get<IFilm[]>(environment.urlApi + 'films/highlighted')
      .pipe(catchError(this.handleError));
  }

  getNewFilms(): Observable<IFilm[]> {
    return this.http
      .get<IFilm[]>(environment.urlApi + 'films/new')
      .pipe(catchError(this.handleError));
  }

  getMostViewedFilms(): Observable<IFilm[]> {
    return this.http
      .get<IFilm[]>(environment.urlApi + 'films/top')
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(() => error);
  }
}
