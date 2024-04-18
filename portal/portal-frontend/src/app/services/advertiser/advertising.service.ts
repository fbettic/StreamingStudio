import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { IAdvertisingRequest } from '../../models/advertising-request.model';
import { environment } from '../../../environments/environment';
import { IAdvertising } from '../../models/advertising.model';

@Injectable({
  providedIn: 'root',
})
export class AdvertisingService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  createAdvertising(userData: IAdvertisingRequest): Observable<IAdvertising> {
    return this.http
      .post<IAdvertising>(environment.urlApi + 'advertiser/advertisings', userData)
      .pipe(catchError(this.handleError));
  }

  updateAdvertising(
    id: number,
    advertisingData: IAdvertisingRequest
  ): Observable<IAdvertising> {

    return this.http
      .put<IAdvertising>(
        environment.urlApi + 'advertiser/advertisings/' + id,
        advertisingData
      )
      .pipe(catchError(this.handleError));
  }

  deleteAdvertiser(id: number): Observable<IAdvertising> {
    return this.http
      .delete<IAdvertising>(environment.urlApi + 'advertiser/advertisings/' + id)
      .pipe(catchError(this.handleError));
  }

  getAdvertisers(): Observable<IAdvertising[]> {
    return this.http
      .get<IAdvertising[]>(environment.urlApi + 'advertiser/advertisings')
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.error('🚀 ~ AdvertiserService ~ handleError ~ error:', error);
    return throwError(() => error);
  }
}
