import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

const USER_KEY = environment.userKeyStorage

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  clean():void{
    sessionStorage.clear()
  }

  public saveUser(user:any): void {
    sessionStorage.removeItem(USER_KEY);
    sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }
  
  public getUser(): any {
    const user = sessionStorage.getItem(USER_KEY);
    if(user){
      return JSON.parse(user);
    }
    return {}
  }

  public isLoggedIn(): boolean {
    const user = sessionStorage.getItem(USER_KEY);
    return !!user
  }

}
