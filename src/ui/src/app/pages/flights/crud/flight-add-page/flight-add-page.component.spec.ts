import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightAddPageComponent } from './flight-add-page.component';

describe('FlightAddPageComponent', () => {
  let component: FlightAddPageComponent;
  let fixture: ComponentFixture<FlightAddPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FlightAddPageComponent]
    });
    fixture = TestBed.createComponent(FlightAddPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
