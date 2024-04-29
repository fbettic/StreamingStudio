import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SizeTypeFormComponent } from './size-type-form.component';

describe('SizeTypeFormComponent', () => {
  let component: SizeTypeFormComponent;
  let fixture: ComponentFixture<SizeTypeFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SizeTypeFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SizeTypeFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
