import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivitiesSearchResultComponent } from './activities-search-result.component';

describe('ActivitiesSearchResultComponent', () => {
  let component: ActivitiesSearchResultComponent;
  let fixture: ComponentFixture<ActivitiesSearchResultComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActivitiesSearchResultComponent]
    });
    fixture = TestBed.createComponent(ActivitiesSearchResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
