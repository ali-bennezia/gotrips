import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityAddPageComponent } from './activity-add-page.component';

describe('ActivityAddPageComponent', () => {
  let component: ActivityAddPageComponent;
  let fixture: ComponentFixture<ActivityAddPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActivityAddPageComponent]
    });
    fixture = TestBed.createComponent(ActivityAddPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
