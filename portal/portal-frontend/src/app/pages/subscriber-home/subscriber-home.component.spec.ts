import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriberHomeComponent } from './subscriber-home.component';

describe('SubscriberHomeComponent', () => {
  let component: SubscriberHomeComponent;
  let fixture: ComponentFixture<SubscriberHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SubscriberHomeComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SubscriberHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
