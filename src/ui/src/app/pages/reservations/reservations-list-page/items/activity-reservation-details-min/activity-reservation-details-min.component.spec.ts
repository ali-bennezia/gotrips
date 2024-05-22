import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityReservationDetailsMinComponent } from './activity-reservation-details-min.component';

describe('ActivityReservationDetailsMinComponent', () => {
  let component: ActivityReservationDetailsMinComponent;
  let fixture: ComponentFixture<ActivityReservationDetailsMinComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActivityReservationDetailsMinComponent]
    });
    fixture = TestBed.createComponent(ActivityReservationDetailsMinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
