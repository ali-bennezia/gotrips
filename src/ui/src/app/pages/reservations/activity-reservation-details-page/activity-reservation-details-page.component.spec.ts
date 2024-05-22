import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityReservationDetailsPageComponent } from './activity-reservation-details-page.component';

describe('ActivityReservationDetailsPageComponent', () => {
  let component: ActivityReservationDetailsPageComponent;
  let fixture: ComponentFixture<ActivityReservationDetailsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActivityReservationDetailsPageComponent]
    });
    fixture = TestBed.createComponent(ActivityReservationDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
