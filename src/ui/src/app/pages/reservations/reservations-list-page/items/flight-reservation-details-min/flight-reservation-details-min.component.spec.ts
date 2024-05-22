import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightReservationDetailsMinComponent } from './flight-reservation-details-min.component';

describe('FlightReservationDetailsMinComponent', () => {
  let component: FlightReservationDetailsMinComponent;
  let fixture: ComponentFixture<FlightReservationDetailsMinComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FlightReservationDetailsMinComponent]
    });
    fixture = TestBed.createComponent(FlightReservationDetailsMinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
