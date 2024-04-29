import { Injectable, inject } from '@angular/core';
import { IFee } from '../models/fee.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FeeService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  createFee(data: IFee): Observable<IFee> {
    return this.http
      .post<IFee>(environment.urlApi + 'invoicing/fees', data)
      .pipe(catchError(this.handleError));
  }

  getAllFees(): Observable<IFee[]> {
    return this.http
      .get<IFee[]>(environment.urlApi + 'invoicing/fees')
      .pipe(catchError(this.handleError));
  }

  getFeeById(id: number): Observable<IFee> {
    return this.http
      .get<IFee>(environment.urlApi + 'invoicing/fees/' + id)
      .pipe(catchError(this.handleError));
  }

  updateFee(id: number, data: IFee): Observable<IFee> {
    return this.http
      .put<IFee>(
        environment.urlApi + 'invoicing/fees/' + id,
        data
      )
      .pipe(catchError(this.handleError));
  }

  deleteFeeById(id: number): Observable<number> {
    return this.http
      .delete<number>(environment.urlApi + 'invoicing/fees/' + id)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.log('🚀 ~ FeeService ~ handleError ~ error:', error);

    return throwError(() => error);
  }
}
