import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisingsTableComponent } from './advertisings-table.component';

describe('AdvertisingsTableComponent', () => {
  let component: AdvertisingsTableComponent;
  let fixture: ComponentFixture<AdvertisingsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvertisingsTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdvertisingsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
