import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TargetsTableComponent } from './targets-table.component';

describe('TargetsTableComponent', () => {
  let component: TargetsTableComponent;
  let fixture: ComponentFixture<TargetsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TargetsTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(TargetsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
