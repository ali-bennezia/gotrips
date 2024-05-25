import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelsSearchPageComponent } from './hotels-search-page.component';

describe('HotelsSearchPageComponent', () => {
  let component: HotelsSearchPageComponent;
  let fixture: ComponentFixture<HotelsSearchPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelsSearchPageComponent]
    });
    fixture = TestBed.createComponent(HotelsSearchPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
