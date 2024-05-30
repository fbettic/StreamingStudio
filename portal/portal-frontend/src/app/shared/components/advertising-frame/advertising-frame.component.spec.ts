import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdvertisingFrameComponent } from './advertising-frame.component';

describe('AdvertisingFrameComponent', () => {
  let component: AdvertisingFrameComponent;
  let fixture: ComponentFixture<AdvertisingFrameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdvertisingFrameComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AdvertisingFrameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
