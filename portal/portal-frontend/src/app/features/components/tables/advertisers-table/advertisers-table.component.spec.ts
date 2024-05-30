import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisersTableComponent } from './advertisers-table.component';

describe('AdvertisersTableComponent', () => {
  let component: AdvertisersTableComponent;
  let fixture: ComponentFixture<AdvertisersTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvertisersTableComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdvertisersTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
