import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { IFilm } from '../models/film.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class FilmService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  getAllFilms(): Observable<IFilm[]> {
    return this.http.get<IFilm[]>(environment.urlApi + 'films')
    .pipe(catchError(this.handleError));
  }

  getFilmById(id: number): Observable<IFilm> {
    return this.http.get<IFilm>(environment.urlApi + 'films/' + id)
  }

  private handleError(error: HttpErrorResponse) {
    console.log('🚀 ~ AdvertisingService ~ handleError ~ error:', error);

    return throwError(() => error);
  }
}
