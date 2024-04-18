import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { StorageService } from '../storage/storage.service';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { ILoginResponse } from '../../models/login-response.model';
import { LoginService } from './login.service';
import { ISubscriber } from '../../models/subscriber.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SignupService {
  private http: HttpClient = inject(HttpClient);

  constructor() {}

  signup(signupData: ISubscriber): Observable<ILoginResponse> {
    return this.http
      .post<ILoginResponse>(environment.urlApi + 'auth/subscribers', signupData)
      .pipe(
        tap((userData: ILoginResponse) => {
          console.log('🚀 ~ SignupService ~ signup ~ userData:', userData);
        }),
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    console.log("🚀 ~ SignupService ~ handleError ~ error:", error)
    return throwError(() => error);
  }
}
