import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightCompanyPageComponent } from './flight-company-page.component';

describe('FlightCompanyPageComponent', () => {
  let component: FlightCompanyPageComponent;
  let fixture: ComponentFixture<FlightCompanyPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FlightCompanyPageComponent]
    });
    fixture = TestBed.createComponent(FlightCompanyPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
