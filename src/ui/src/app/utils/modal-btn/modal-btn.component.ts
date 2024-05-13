import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-modal-btn',
  templateUrl: './modal-btn.component.html',
  styleUrls: ['./modal-btn.component.css'],
})
export class ModalBtnComponent {
  @Input()
  modalIdentifier!: string;
  @Input()
  label!: string;
  @Input()
  btnClass!: string;
}
