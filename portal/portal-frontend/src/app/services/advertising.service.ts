import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { IAdvertisingRequest } from '../models/advertising-request.model';
import { IAdvertisingSlot } from '../models/advertising-slot.model';
import { IAdvertising } from '../models/advertising.model';
import { ITargetCategory } from '../models/target-category.model';
import { IAdvertisingClick } from '../models/advertising-click.model';

@Injectable({
  providedIn: 'root',
})
export class AdvertisingService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  createAdvertising(userData: IAdvertisingRequest): Observable<IAdvertising> {
    return this.http
      .post<IAdvertising>(environment.urlApi + 'advertisings', userData)
      .pipe(catchError(this.handleError));
  }

  updateAdvertising(
    id: number,
    advertisingData: IAdvertisingRequest
  ): Observable<IAdvertising> {
    return this.http
      .put<IAdvertising>(
        environment.urlApi + 'advertisings/' + id,
        advertisingData
      )
      .pipe(catchError(this.handleError));
  }

  getAdvertisingById(id: number): Observable<IAdvertising> {
    return this.http
      .get<IAdvertising>(environment.urlApi + 'advertisings/' + id)
      .pipe(catchError(this.handleError));
  }

  deleteAdvertising(id: number): Observable<IAdvertising> {
    return this.http
      .delete<IAdvertising>(environment.urlApi + 'advertisings/' + id)
      .pipe(catchError(this.handleError));
  }

  getAllAdvertisings(): Observable<IAdvertising[]> {
    return this.http
      .get<IAdvertising[]>(environment.urlApi + 'advertisings')
      .pipe(catchError(this.handleError));
  }

  getAdvertisingsByAdvertiser(id: number): Observable<IAdvertising[]> {
    return this.http
      .get<IAdvertising[]>(
        environment.urlApi + 'advertisers/' + id + '/advertisings'
      )
      .pipe(catchError(this.handleError));
  }

  getAdvertisingsForSubscriber(
    data: IAdvertisingSlot[],
    allPages: Boolean
  ): Observable<IAdvertisingSlot[]> {
    
    return this.http
      .post<IAdvertisingSlot[]>(environment.urlApi + 'advertisings/subscriber?allpages=' + allPages , data)
      .pipe(catchError(this.handleError));
  }

  getAllAdvertisingTargetByAdvertisingId(
    id: number
  ): Observable<ITargetCategory[]> {
    return this.http
      .get<ITargetCategory[]>(
        environment.urlApi + 'advertising/' + id + '/targets'
      )
      .pipe(catchError(this.handleError));
  }

  createSubscriberAdvertisingClick(
    data: IAdvertisingClick,
    
  ): Observable<String> {
    
    return this.http
      .post<String>(environment.urlApi + 'advertisings/track', data)
      .pipe(catchError(this.handleError));
  }


  private handleError(error: HttpErrorResponse) {
    console.log('🚀 ~ AdvertisingService ~ handleError ~ error:', error);

    return throwError(() => error);
  }
}
