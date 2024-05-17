import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-stars-evaluation',
  templateUrl: './stars-evaluation.component.html',
  styleUrls: ['./stars-evaluation.component.css'],
})
export class StarsEvaluationComponent {
  @Input()
  note!: number;
  noteArray: number[] = Array(5)
    .fill(0)
    .map((e, i) => i);
}
