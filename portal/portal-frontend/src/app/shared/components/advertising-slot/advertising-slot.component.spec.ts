import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisingSlotComponent } from './advertising-slot.component';

describe('AdvertisingSlotComponent', () => {
  let component: AdvertisingSlotComponent;
  let fixture: ComponentFixture<AdvertisingSlotComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvertisingSlotComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdvertisingSlotComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
