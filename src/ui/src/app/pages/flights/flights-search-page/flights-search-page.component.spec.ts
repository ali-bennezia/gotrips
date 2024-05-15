import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightsSearchPageComponent } from './flights-search-page.component';

describe('FlightsSearchPageComponent', () => {
  let component: FlightsSearchPageComponent;
  let fixture: ComponentFixture<FlightsSearchPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FlightsSearchPageComponent]
    });
    fixture = TestBed.createComponent(FlightsSearchPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
