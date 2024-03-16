import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertiserFormComponent } from './advertiser-form.component';

describe('AdvertiserFormComponent', () => {
  let component: AdvertiserFormComponent;
  let fixture: ComponentFixture<AdvertiserFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvertiserFormComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdvertiserFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
