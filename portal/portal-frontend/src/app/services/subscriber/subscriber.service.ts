import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ILoginResponse } from '../../models/login-response.model';
import { ISubscriber } from '../../models/subscriber.model';
import { IAssociationResponse } from '../../models/association-response.model';
import { IAssociationRequest } from '../../models/association-request.model';
import { IAssociation } from '../../models/association.model';
import { ISessionRequest } from '../../models/session-request.model';
import { ISession } from '../../models/session.model';

@Injectable({
  providedIn: 'root',
})
export class SubscriberService {
  http: HttpClient = inject(HttpClient);

  constructor() {}

  createAsociationRequest(
    data: IAssociationRequest
  ): Observable<IAssociationResponse> {
    return this.http.post<IAssociationResponse>(
      environment.urlApi + 'associations/link',
      data
    );
  }

  getCompleteAssociation(uuid: string): Observable<IAssociation> {
    return this.http.get<IAssociation>(
      environment.urlApi + 'associations?uuid=' + uuid
    );
  }

  getAllAssociations(): Observable<IAssociation[]> {
    return this.http.get<IAssociation[]>(
      environment.urlApi + 'associations/linked'
    );
  }

  cancelAssociation(id: number): Observable<IAssociation> {
    return this.http.get<IAssociation>(
      environment.urlApi + 'associations/unlink/' + id
    );
  }

  createSession(data: ISessionRequest): Observable<ISession> {
    return this.http.post<ISession>(
      environment.urlApi + 'associations/sessions',
      data
    );
  }

  markSessionAsUsed(id: number): Observable<ISession> {
    return this.http.get<ISession>(
      environment.urlApi + 'associations/sessions/' + id
    );
  }

  private handleError(error: HttpErrorResponse) {
    console.log('🚀 ~ SubscriberService ~ handleError ~ error:', error);

    return throwError(() => error);
  }
}
