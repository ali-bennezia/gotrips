import { Component, Input } from '@angular/core';
import { InteractionService } from 'src/app/services/interaction.service';

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

  constructor(private interactionService: InteractionService) {}

  triggerCallback = (e: Event) => {
    this.interactionService.onConfirmAccountDeleteSource.next(e);
  };
}
