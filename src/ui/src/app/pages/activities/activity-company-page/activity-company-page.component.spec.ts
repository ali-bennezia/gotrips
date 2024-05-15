import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityCompanyPageComponent } from './activity-company-page.component';

describe('ActivityCompanyPageComponent', () => {
  let component: ActivityCompanyPageComponent;
  let fixture: ComponentFixture<ActivityCompanyPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ActivityCompanyPageComponent]
    });
    fixture = TestBed.createComponent(ActivityCompanyPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
