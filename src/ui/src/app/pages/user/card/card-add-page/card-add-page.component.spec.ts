import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardAddPageComponent } from './card-add-page.component';

describe('CardAddPageComponent', () => {
  let component: CardAddPageComponent;
  let fixture: ComponentFixture<CardAddPageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CardAddPageComponent]
    });
    fixture = TestBed.createComponent(CardAddPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
