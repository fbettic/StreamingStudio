import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Inject, Injectable, inject } from '@angular/core';
import { IAdvertiser } from '../models/advertiser.model';
import { ILoginResponse } from '../models/login-response.model';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AdvertiserService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  createAdvertiser(data: IAdvertiser): Observable<ILoginResponse> {
    return this.http
      .post<ILoginResponse>(environment.urlApi + 'advertisers', data)
      .pipe(catchError(this.handleError));
  }

  updateAdvertiser(
    id: number,
    data: IAdvertiser
  ): Observable<IAdvertiser> {
    return this.http
      .put<IAdvertiser>(environment.urlApi + 'advertisers/' + id, data)
      .pipe(catchError(this.handleError));
  }

  deleteAdvertiser(id: number): Observable<number> {
    return this.http
      .delete<number>(environment.urlApi + 'advertisers/' + id)
      .pipe(catchError(this.handleError));
  }

  getAdvertiserById(id: number): Observable<IAdvertiser> {
    return this.http
      .get<IAdvertiser>(environment.urlApi + 'advertisers/' + id)
      .pipe(catchError(this.handleError));
  }

  getAllAdvertisers(): Observable<IAdvertiser[]> {
    return this.http
      .get<IAdvertiser[]>(environment.urlApi + 'advertisers')
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.error('🚀 ~ AdvertiserService ~ handleError ~ error:', error);
    return throwError(() => error);
  }
}
