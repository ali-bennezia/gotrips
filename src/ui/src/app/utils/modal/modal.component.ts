import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css'],
})
export class ModalComponent {
  @Input()
  title!: string;
  @Input()
  content!: string;
  @Input()
  button1Label!: string;
  @Input()
  button2Label!: string;
  @Input()
  modalIdentifier!: string;

  @Input()
  onConfirmCallback!: (e: Event) => void;

  triggerCallback = (e: Event) => {
    this.onConfirmCallback(e);
  };
}
