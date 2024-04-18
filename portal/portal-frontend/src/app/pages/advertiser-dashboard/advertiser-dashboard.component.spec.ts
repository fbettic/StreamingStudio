import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertiserDashboardComponent } from './advertiser-dashboard.component';

describe('AdvertiserDashboardComponent', () => {
  let component: AdvertiserDashboardComponent;
  let fixture: ComponentFixture<AdvertiserDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvertiserDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdvertiserDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
