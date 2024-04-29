import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { ISizeType } from '../models/size-type.model';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SizeTypeService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  createSizeType(data: ISizeType): Observable<ISizeType> {
    return this.http
      .post<ISizeType>(environment.urlApi + 'advertisings/sizes', data)
      .pipe(catchError(this.handleError));
  }

  getAllSizeTypes(): Observable<ISizeType[]> {
    return this.http
      .get<ISizeType[]>(environment.urlApi + 'advertisings/sizes')
      .pipe(catchError(this.handleError));
  }

  getSizeTypeById(id: number): Observable<ISizeType> {
    return this.http
      .get<ISizeType>(environment.urlApi + 'advertisings/sizes/' + id)
      .pipe(catchError(this.handleError));
  }

  updateSizeType(id: number, data: ISizeType): Observable<ISizeType> {
    return this.http
      .put<ISizeType>(
        environment.urlApi + 'advertisings/sizes/' + id,
        data
      )
      .pipe(catchError(this.handleError));
  }

  deleteSizeTypeById(id: number): Observable<number> {
    return this.http
      .delete<number>(environment.urlApi + 'advertisings/sizes/' + id)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.log('🚀 ~ SizeTypeService ~ handleError ~ error:', error);

    return throwError(() => error);
  }
}
