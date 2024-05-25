import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PeriodCalendarComponent } from './period-calendar.component';

describe('PeriodCalendarComponent', () => {
  let component: PeriodCalendarComponent;
  let fixture: ComponentFixture<PeriodCalendarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PeriodCalendarComponent]
    });
    fixture = TestBed.createComponent(PeriodCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
