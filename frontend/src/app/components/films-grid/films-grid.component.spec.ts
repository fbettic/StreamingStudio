import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilmsGridComponent } from './films-grid.component';

describe('FilmsGridComponent', () => {
  let component: FilmsGridComponent;
  let fixture: ComponentFixture<FilmsGridComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FilmsGridComponent]
    });
    fixture = TestBed.createComponent(FilmsGridComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
