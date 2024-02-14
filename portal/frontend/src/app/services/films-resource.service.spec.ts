import { TestBed } from '@angular/core/testing';

import { FilmsResourceService } from './films-resource.service';

describe('FilmsResourceService', () => {
  let service: FilmsResourceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FilmsResourceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
