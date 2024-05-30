import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ILoginRequest } from '../../core/models/login-request.model';
import { ILoginResponse } from '../../core/models/login-response.model';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private http: HttpClient = inject(HttpClient);
  private storage: StorageService = inject(StorageService);

  private currentUserLoggedIn: BehaviorSubject<boolean> =
    new BehaviorSubject<boolean>(false);

  private currentUserData: BehaviorSubject<ILoginResponse> =
    new BehaviorSubject<ILoginResponse>({ token: '', id: 0, role: '' });

  constructor() {
    this.currentUserLoggedIn = new BehaviorSubject<boolean>(
      this.storage.isLoggedIn()
    );
    this.currentUserData = new BehaviorSubject<ILoginResponse>(
      this.storage.getUser() || {
        token: '',
        id: 0,
        role: '',
      }
    );
  }

  login(credential: ILoginRequest): Observable<ILoginResponse> {
    return this.http
      .post<ILoginResponse>(environment.urlApi + 'auth/login', credential)
      .pipe(
        tap((userData: ILoginResponse) => {
          this.storage.saveUser(userData);
          console.log('🚀 ~ LoginService ~ tap ~ userData:', userData);
          this.currentUserData.next(userData);
          this.currentUserLoggedIn.next(true);
        }),
        catchError(this.handleError)
      );
  }

  logout(): void {
    this.storage.clean();
    this.currentUserLoggedIn.next(false);
  }

  private handleError(error: HttpErrorResponse) {
    console.error('Error', error);
    return throwError(() => error);
  }

  set setCurrentUserData(userData: ILoginResponse) {
    this.storage.saveUser(userData);
    this.currentUserData.next(userData);
    this.currentUserLoggedIn.next(true);
  }

  get userData(): Observable<ILoginResponse> {
    return this.currentUserData.asObservable();
  }

  get userLoggedIn(): Observable<boolean> {
    return this.currentUserLoggedIn.asObservable();
  }

  get userToken(): string {
    return this.currentUserData.value.token;
  }
}
