import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';
import { ITargetCategory } from '../models/target-category.model';

@Injectable({
  providedIn: 'root',
})
export class TargetCategoryService {
  private http: HttpClient = inject(HttpClient);
  constructor() {}

  createTargetCategory(data: ITargetCategory): Observable<ITargetCategory> {
    return this.http
      .post<ITargetCategory>(environment.urlApi + 'targets', data)
      .pipe(catchError(this.handleError));
  }

  getAllTargetCategories(): Observable<ITargetCategory[]> {
    return this.http
      .get<ITargetCategory[]>(environment.urlApi + 'targets')
      .pipe(catchError(this.handleError));
  }

  updateTargetCategory(
    id: number,
    data: ITargetCategory
  ): Observable<ITargetCategory> {
    return this.http
      .put<ITargetCategory>(environment.urlApi + 'targets/' + id, data)
      .pipe(catchError(this.handleError));
  }

  deleteTargetCategory(id: number): Observable<number> {
    return this.http
      .delete<number>(environment.urlApi + 'targets/' + id)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.log('🚀 ~ TargetCategoryService ~ handleError ~ error:', error);

    return throwError(() => error);
  }
}
