import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IStreamingPlatformRequest } from '../../core/models/streaming-platform-request.model';
import { IStreamingPlatformSubscriber } from '../../core/models/streaming-platform-subscriber.model';
import { IStreamingPlatform } from '../../core/models/streaming-platform.model';

@Injectable({
  providedIn: 'root',
})
export class StreamingPlatformService {
  private http: HttpClient = inject(HttpClient);
  constructor() {}

  createStreamingPlatform(
    data: IStreamingPlatformRequest
  ): Observable<IStreamingPlatform> {
    return this.http
      .post<IStreamingPlatform>(environment.urlApi + 'platforms', data)
      .pipe(catchError(this.handleError));
  }

  getStreamingPlatformById(id: number): Observable<IStreamingPlatform> {
    return this.http
      .get<IStreamingPlatform>(environment.urlApi + 'platforms/' + id)
      .pipe(catchError(this.handleError));
  }

  getAllStreamingPlatforms(): Observable<IStreamingPlatform[]> {
    return this.http
      .get<IStreamingPlatform[]>(environment.urlApi + 'platforms')
      .pipe(catchError(this.handleError));
  }

  getStreamingPlatformSubscriber(): Observable<IStreamingPlatformSubscriber[]> {
    return this.http
      .get<IStreamingPlatformSubscriber[]>(environment.urlApi + 'platforms')
      .pipe(catchError(this.handleError));
  }

  updateStreamingPlatform(
    id: number,
    data: IStreamingPlatformRequest
  ): Observable<IStreamingPlatform> {
    return this.http
      .put<IStreamingPlatform>(environment.urlApi + 'platforms/' + id, data)
      .pipe(catchError(this.handleError));
  }

  deleteStreamingPlatformById(id: number): Observable<number> {
    return this.http
      .delete<number>(environment.urlApi + 'platforms/' + id)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(() => error);
  }
}
