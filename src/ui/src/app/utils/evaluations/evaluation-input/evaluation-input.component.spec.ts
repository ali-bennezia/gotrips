import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationInputComponent } from './evaluation-input.component';

describe('EvaluationInputComponent', () => {
  let component: EvaluationInputComponent;
  let fixture: ComponentFixture<EvaluationInputComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EvaluationInputComponent]
    });
    fixture = TestBed.createComponent(EvaluationInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
