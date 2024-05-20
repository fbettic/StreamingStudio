import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadingScreenService {
  private _loading: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor() { }

  get loading(): Observable<boolean> {
    return this._loading.asObservable()
  }

  show() {
    this._loading.next(true);
  }

  hide() {
    this._loading.next(false);
  }
}
