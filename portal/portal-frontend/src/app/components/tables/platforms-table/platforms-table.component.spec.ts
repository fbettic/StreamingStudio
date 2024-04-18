import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlatformsTableComponent } from './platforms-table.component';

describe('PlatformsTableComponent', () => {
  let component: PlatformsTableComponent;
  let fixture: ComponentFixture<PlatformsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlatformsTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PlatformsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
