import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightDetailsPageComponent } from './flight-details-page.component';

describe('FlightDetailsPageComponent', () => {
  let component: FlightDetailsPageComponent;
  let fixture: ComponentFixture<FlightDetailsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FlightDetailsPageComponent]
    });
    fixture = TestBed.createComponent(FlightDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
