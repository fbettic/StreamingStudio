import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IBannerPriority } from '../../core/models/banner-priority.model';

@Injectable({
  providedIn: 'root',
})
export class BannerPriorityService {
  private http: HttpClient = inject(HttpClient);
  constructor() {}

  createBannerPriority(data: IBannerPriority): Observable<IBannerPriority> {
    return this.http
      .post<IBannerPriority>(
        environment.urlApi + 'advertisings/priorities',
        data
      )
      .pipe(catchError(this.handleError));
  }

  getAllBannerPriorities():Observable<IBannerPriority[]> {
    return this.http
      .get<IBannerPriority[]>(
        environment.urlApi + 'advertisings/priorities',
      )
      .pipe(catchError(this.handleError));
  }

  getBannerPriorityById(id: number):Observable<IBannerPriority> {
    return this.http
      .get<IBannerPriority>(
        environment.urlApi + 'advertisings/priorities/' + id
      )
      .pipe(catchError(this.handleError));
  }

  updateBannerPriority(id: number, data: IBannerPriority):Observable<IBannerPriority> {
    return this.http
      .put<IBannerPriority>(
        environment.urlApi + 'advertisings/priorities/' + id,
        data
      )
      .pipe(catchError(this.handleError));
  }

  deleteBannerPriorityById(id: number):Observable<number>{
    return this.http
      .delete<number>(
        environment.urlApi + 'advertisings/priorities/' + id,
      )
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
  
    return throwError(() => error);
  }
}
