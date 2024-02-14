import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilmsCarouselComponent } from './films-carousel.component';

describe('FilmsCarouselComponent', () => {
  let component: FilmsCarouselComponent;
  let fixture: ComponentFixture<FilmsCarouselComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FilmsCarouselComponent]
    });
    fixture = TestBed.createComponent(FilmsCarouselComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
