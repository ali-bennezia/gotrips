import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CompanyDetailsMinComponent } from './company-details-min.component';

describe('CompanyDetailsMinComponent', () => {
  let component: CompanyDetailsMinComponent;
  let fixture: ComponentFixture<CompanyDetailsMinComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CompanyDetailsMinComponent]
    });
    fixture = TestBed.createComponent(CompanyDetailsMinComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
