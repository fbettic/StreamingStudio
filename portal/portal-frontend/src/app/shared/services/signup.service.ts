import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ILoginResponse } from '../../core/models/login-response.model';
import { ISubscriber } from '../../core/models/subscriber.model';

@Injectable({
  providedIn: 'root',
})
export class SignupService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  signup(signupData: ISubscriber): Observable<ILoginResponse> {
    return this.http
      .post<ILoginResponse>(
        environment.urlApi + 'auth/signup/subscriber',
        signupData
      )
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    return throwError(() => error);
  }
}
