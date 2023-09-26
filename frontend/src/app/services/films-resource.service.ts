import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import {
  IResourceMethodObservable,
  Resource,
  ResourceAction,
  ResourceParams,
  ResourceRequestMethod,
  ResourceResponseBodyType,
} from '@kkoehn/ngx-resource-core';
import { Film } from '../models';

@Injectable({
  providedIn: 'root'
})
@ResourceParams({
  patPrefix: `${environment.apiUrl}/api/films`
})
export class FilmsResourceService extends Resource {

  constructor() {
    super();
  }

  @ResourceAction({
    method: ResourceRequestMethod.Get,
    path: '/films',
    responseBodyType: ResourceResponseBodyType.Json,
  })
  getFilms!: IResourceMethodObservable<void, Film[]>;
}
