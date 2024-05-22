import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReservationsListPageComponent } from './reservations-list-page.component';

describe('ReservationsListPageComponent', () => {
  let component: ReservationsListPageComponent;
  let fixture: ComponentFixture<ReservationsListPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ReservationsListPageComponent]
    });
    fixture = TestBed.createComponent(ReservationsListPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
