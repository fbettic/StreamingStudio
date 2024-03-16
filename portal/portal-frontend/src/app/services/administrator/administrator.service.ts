import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ILoginResponse } from '../../models/login-response.model';
import { IAdministrator } from '../../models/administrator.model';

@Injectable({
  providedIn: 'root',
})
export class AdministratorService {
  private http: HttpClient = inject(HttpClient);

  getTest(): Observable<any> {
    return this.http
      .get(environment.urlApi + 'admin/gettest', { responseType: 'text' })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.log('🚀 ~ AdministratorService ~ handleError ~ error:', error);

    return throwError(() => error);
  }
}
