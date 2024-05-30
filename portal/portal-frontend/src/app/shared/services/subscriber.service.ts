import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { IAssociationRequest } from '../../core/models/association-request.model';
import { IAssociationResponse } from '../../core/models/association-response.model';
import { IAssociation } from '../../core/models/association.model';
import { ISessionRequest } from '../../core/models/session-request.model';
import { ISession } from '../../core/models/session.model';
import { ISubscriber } from '../../core/models/subscriber.model';

@Injectable({
  providedIn: 'root',
})
export class SubscriberService {
  http: HttpClient = inject(HttpClient);

  constructor() {}

  updateSubscriber(data: ISubscriber): Observable<ISubscriber> {
    return this.http.put<ISubscriber>(environment.urlApi + 'subscribers', data);
  }

  getSubscriber(): Observable<ISubscriber> {
    return this.http.get<ISubscriber>(environment.urlApi + 'subscribers');
  }

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
    return throwError(() => error);
  }
}
