import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-stars-evaluation-input',
  templateUrl: './stars-evaluation-input.component.html',
  styleUrls: ['./stars-evaluation-input.component.css'],
})
export class StarsEvaluationInputComponent implements OnInit {
  note: number = 0;
  @Output()
  noteChanged: EventEmitter<number> = new EventEmitter<number>();
  displayedNote: number = 0;
  noteArray: number[] = Array(5)
    .fill(0)
    .map((e, i) => i);

  setNote(n: number) {
    this.note = n;
    this.noteChanged.emit(n);
  }

  onMouseOver = (i: number, e: Event) => {
    this.displayedNote = i;
  };
  onMouseLeave = (i: number, e: Event) => {
    this.displayedNote = this.note;
  };

  onClick = (i: number, e: Event) => {
    this.setNote(i);
  };

  ngOnInit(): void {
    this.setNote(0);
  }
}
