import { Component, Input } from '@angular/core';
import { EvaluationDetailsDto } from 'src/app/data/user/evaluation-details-dto';

@Component({
  selector: 'app-evaluation-display',
  templateUrl: './evaluation-display.component.html',
  styleUrls: ['./evaluation-display.component.css'],
})
export class EvaluationDisplayComponent {
  @Input()
  evaluation!: EvaluationDetailsDto;
}
