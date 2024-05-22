import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelReservationDetailsPageComponent } from './hotel-reservation-details-page.component';

describe('HotelReservationDetailsPageComponent', () => {
  let component: HotelReservationDetailsPageComponent;
  let fixture: ComponentFixture<HotelReservationDetailsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelReservationDetailsPageComponent]
    });
    fixture = TestBed.createComponent(HotelReservationDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
