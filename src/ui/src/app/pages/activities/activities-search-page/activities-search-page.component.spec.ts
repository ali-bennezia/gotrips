import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivitiesSearchPageComponent } from './activities-search-page.component';

describe('ActivitiesSearchPageComponent', () => {
  let component: ActivitiesSearchPageComponent;
  let fixture: ComponentFixture<ActivitiesSearchPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActivitiesSearchPageComponent]
    });
    fixture = TestBed.createComponent(ActivitiesSearchPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
