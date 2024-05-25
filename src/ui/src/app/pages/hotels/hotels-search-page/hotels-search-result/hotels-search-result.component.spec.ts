import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelsSearchResultComponent } from './hotels-search-result.component';

describe('HotelsSearchResultComponent', () => {
  let component: HotelsSearchResultComponent;
  let fixture: ComponentFixture<HotelsSearchResultComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HotelsSearchResultComponent]
    });
    fixture = TestBed.createComponent(HotelsSearchResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
