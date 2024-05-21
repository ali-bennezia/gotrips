import { Component, EventEmitter, Input, Output } from '@angular/core';

export interface ListGroupItem {
  title: string;
  content: string[];
  value: any;
}

@Component({
  selector: 'app-interactive-list-group',
  templateUrl: './interactive-list-group.component.html',
  styleUrls: ['./interactive-list-group.component.css'],
})
export class InteractiveListGroupComponent {
  @Input()
  list!: ListGroupItem[];

  @Input()
  showLabel!: string;
  @Input()
  hideLabel!: string;

  @Output()
  onSelectValue: EventEmitter<any> = new EventEmitter<any>();

  show: boolean = false;
  value: any | null = null;
  selectionIndex: number = -1;

  onClickButton = (e: Event) => {
    this.show = !this.show;
  };

  onSelect = (e: Event, val: any, index: number) => {
    this.value = val;
    this.selectionIndex = index;
    this.onSelectValue.emit(val);
  };
}
