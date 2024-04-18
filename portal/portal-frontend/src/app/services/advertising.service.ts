import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { IAdvertisingRequest } from '../models/advertising-request.model';
import { IAdvertising } from '../models/advertising.model';

@Injectable({
  providedIn: 'root',
})
export class AdvertisingService {
  private http: HttpClient = inject(HttpClient);
  private userUrl: string = '';

  constructor() {}

  createAdvertising(userData: IAdvertisingRequest): Observable<IAdvertising> {
    return this.http
      .post<IAdvertising>(
        environment.urlApi + this.userUrl + '/advertisings',
        userData
      )
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

  deleteAdvertising(id: number): Observable<IAdvertising> {
    return this.http
      .delete<IAdvertising>(
        environment.urlApi + this.userUrl + '/advertisings/' + id
      )
      .pipe(catchError(this.handleError));
  }

  getAdvertising(): Observable<IAdvertising[]> {
    return this.http
      .get<IAdvertising[]>(environment.urlApi + 'advertiser/advertisings')
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.error('🚀 ~ AdvertiserService ~ handleError ~ error:', error);
    return throwError(() => error);
  }

  set userType(userType: string) {
    this.userUrl = userType === 'ADMINISTRATOR' ? 'admin' : 'advertiser';
  }
}
