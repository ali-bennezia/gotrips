import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelReservationDetailsMinComponent } from './hotel-reservation-details-min.component';

describe('HotelReservationDetailsMinComponent', () => {
  let component: HotelReservationDetailsMinComponent;
  let fixture: ComponentFixture<HotelReservationDetailsMinComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelReservationDetailsMinComponent]
    });
    fixture = TestBed.createComponent(HotelReservationDetailsMinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
