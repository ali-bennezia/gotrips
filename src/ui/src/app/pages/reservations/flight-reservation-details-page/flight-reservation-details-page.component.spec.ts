import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightReservationDetailsPageComponent } from './flight-reservation-details-page.component';

describe('FlightReservationDetailsPageComponent', () => {
  let component: FlightReservationDetailsPageComponent;
  let fixture: ComponentFixture<FlightReservationDetailsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FlightReservationDetailsPageComponent]
    });
    fixture = TestBed.createComponent(FlightReservationDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
