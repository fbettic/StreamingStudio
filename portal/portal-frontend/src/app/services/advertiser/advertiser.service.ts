import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Inject, Injectable, inject } from '@angular/core';
import { IAdvertiser } from '../../models/advertiser.model';
import { ILoginResponse } from '../../models/login-response.model';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AdvertiserService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  createAdvertiser(userData: IAdvertiser): Observable<ILoginResponse> {
    return this.http
      .post<ILoginResponse>(environment.urlApi + 'admin/advertisers', userData)
      .pipe(catchError(this.handleError));
  }

  updateAdvertiser(
    id: number,
    userData: IAdvertiser
  ): Observable<ILoginResponse> {
    /*
    const headers = new HttpHeaders({
      'Content-Type':
      'application/x-www-form-urlencoded'
  });
*/

    return this.http
      .put<ILoginResponse>(
        environment.urlApi + 'admin/advertisers/' + id,
        userData,
        //{ 'headers':headers }
      )
      .pipe(catchError(this.handleError));
  }

  deleteAdvertiser(id: number): Observable<ILoginResponse> {
    return this.http
      .delete<ILoginResponse>(environment.urlApi + 'admin/advertisers/' + id)
      .pipe(catchError(this.handleError));
  }

  getAdvertisers(): Observable<IAdvertiser[]> {
    return this.http
      .get<IAdvertiser[]>(environment.urlApi + 'admin/advertisers')
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.error('🚀 ~ AdvertiserService ~ handleError ~ error:', error);
    return throwError(() => error);
  }
}
