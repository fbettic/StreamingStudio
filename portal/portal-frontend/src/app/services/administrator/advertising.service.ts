import { Injectable, inject } from '@angular/core';
import { IAdvertising } from '../../models/advertising.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IAdvertisingRequest } from '../../models/advertising-request.model';

@Injectable({
  providedIn: 'root'
})
export class AdvertisingService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  createAdvertising(userData: IAdvertisingRequest): Observable<IAdvertising> {
    return this.http
      .post<IAdvertising>(environment.urlApi + 'admin/advertisings', userData)
      .pipe(catchError(this.handleError));
  }

  deleteAdvertising(id: number): Observable<IAdvertising> {
    return this.http
      .delete<IAdvertising>(environment.urlApi + 'admin/advertisings/' + id)
      .pipe(catchError(this.handleError));
  }

  getAdvertisingByAdvertiser(): Observable<IAdvertising[]> {
    return this.http
      .get<IAdvertising[]>(environment.urlApi + 'admin/advertisings')
      .pipe(catchError(this.handleError));
  }

  getAdvertisingPreferences():

  private handleError(error: HttpErrorResponse) {
    console.error('🚀 ~ AdvertiserService ~ handleError ~ error:', error);
    return throwError(() => error);
  }
}
