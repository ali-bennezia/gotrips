import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalBtnComponent } from './modal-btn.component';

describe('ModalBtnComponent', () => {
  let component: ModalBtnComponent;
  let fixture: ComponentFixture<ModalBtnComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalBtnComponent]
    });
    fixture = TestBed.createComponent(ModalBtnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
