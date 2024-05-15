import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelCompanyPageComponent } from './hotel-company-page.component';

describe('HotelCompanyPageComponent', () => {
  let component: HotelCompanyPageComponent;
  let fixture: ComponentFixture<HotelCompanyPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelCompanyPageComponent]
    });
    fixture = TestBed.createComponent(HotelCompanyPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
