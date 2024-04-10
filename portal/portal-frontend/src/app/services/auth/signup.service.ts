import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { StorageService } from '../storage/storage.service';
import { BehaviorSubject } from 'rxjs';
import { ILoginResponse } from '../../models/login-response.model';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class SignupService {
  private http: HttpClient = inject(HttpClient) ;
  private storage: StorageService = inject(StorageService);
  
  private currentUserData: BehaviorSubject<ILoginResponse> =
    new BehaviorSubject<ILoginResponse>({ token: '', id: 0, role: '' });

  constructor() { }
}
