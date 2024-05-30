import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssociationProcessingComponent } from './association-processing.component';

describe('AssociationProcessingComponent', () => {
  let component: AssociationProcessingComponent;
  let fixture: ComponentFixture<AssociationProcessingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssociationProcessingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AssociationProcessingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
