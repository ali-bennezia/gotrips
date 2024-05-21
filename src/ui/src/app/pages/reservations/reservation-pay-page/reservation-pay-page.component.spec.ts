import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationPayPageComponent } from './reservation-pay-page.component';

describe('ReservationPayPageComponent', () => {
  let component: ReservationPayPageComponent;
  let fixture: ComponentFixture<ReservationPayPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReservationPayPageComponent]
    });
    fixture = TestBed.createComponent(ReservationPayPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
