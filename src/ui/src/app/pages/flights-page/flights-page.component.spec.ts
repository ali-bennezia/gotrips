import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightsPageComponent } from './flights-page.component';

describe('FlightsPageComponent', () => {
  let component: FlightsPageComponent;
  let fixture: ComponentFixture<FlightsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FlightsPageComponent]
    });
    fixture = TestBed.createComponent(FlightsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
