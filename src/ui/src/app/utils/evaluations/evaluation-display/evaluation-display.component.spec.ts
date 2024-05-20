import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationDisplayComponent } from './evaluation-display.component';

describe('EvaluationDisplayComponent', () => {
  let component: EvaluationDisplayComponent;
  let fixture: ComponentFixture<EvaluationDisplayComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EvaluationDisplayComponent]
    });
    fixture = TestBed.createComponent(EvaluationDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
