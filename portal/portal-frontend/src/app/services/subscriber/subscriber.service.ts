import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ILoginResponse } from '../../models/login-response.model';
import { ISubscriber } from '../../models/subscriber.model';

@Injectable({
  providedIn: 'root'
})
export class SubscriberService {
  http: HttpClient = inject(HttpClient);

  constructor() {}

  signup(userData: ISubscriber): Observable<ILoginResponse> {
    return this.http
      .post<ILoginResponse>(environment.urlApi + 'subscriber/signup', userData)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    console.log("🚀 ~ SubscriberService ~ handleError ~ error:", error)
    
    return throwError(() => error);
  }
  
}
