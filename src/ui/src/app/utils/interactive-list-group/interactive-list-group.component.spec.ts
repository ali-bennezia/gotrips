import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InteractiveListGroupComponent } from './interactive-list-group.component';

describe('InteractiveListGroupComponent', () => {
  let component: InteractiveListGroupComponent;
  let fixture: ComponentFixture<InteractiveListGroupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InteractiveListGroupComponent]
    });
    fixture = TestBed.createComponent(InteractiveListGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
