import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityDetailsPageComponent } from './activity-details-page.component';

describe('ActivityDetailsPageComponent', () => {
  let component: ActivityDetailsPageComponent;
  let fixture: ComponentFixture<ActivityDetailsPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActivityDetailsPageComponent]
    });
    fixture = TestBed.createComponent(ActivityDetailsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
