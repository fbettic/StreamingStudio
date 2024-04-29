import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BannerPriorityFormComponent } from './banner-priority-form.component';

describe('BannerPriorityFormComponent', () => {
  let component: BannerPriorityFormComponent;
  let fixture: ComponentFixture<BannerPriorityFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BannerPriorityFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(BannerPriorityFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
