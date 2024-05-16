import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelAddPageComponent } from './hotel-add-page.component';

describe('HotelAddPageComponent', () => {
  let component: HotelAddPageComponent;
  let fixture: ComponentFixture<HotelAddPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelAddPageComponent]
    });
    fixture = TestBed.createComponent(HotelAddPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
