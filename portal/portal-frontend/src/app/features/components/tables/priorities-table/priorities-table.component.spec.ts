import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrioritiesTableComponent } from './priorities-table.component';

describe('PrioritiesTableComponent', () => {
  let component: PrioritiesTableComponent;
  let fixture: ComponentFixture<PrioritiesTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrioritiesTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PrioritiesTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
