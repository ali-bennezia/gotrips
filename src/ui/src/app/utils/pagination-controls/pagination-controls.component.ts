import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination-controls',
  templateUrl: './pagination-controls.component.html',
  styleUrls: ['./pagination-controls.component.css'],
})
export class PaginationControlsComponent {
  @Input()
  page!: number;
  @Output()
  onSetPage: EventEmitter<number> = new EventEmitter<number>();
  onClickPage = (i: number) => {
    this.onSetPage.emit(i);
  };
}
