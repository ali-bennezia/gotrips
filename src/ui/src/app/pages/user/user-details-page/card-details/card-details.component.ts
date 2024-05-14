import { Component, Input } from '@angular/core';
import { CardDetailsDto } from 'src/app/data/user/card-details-dto';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css'],
})
export class CardDetailsComponent {
  @Input()
  card!: CardDetailsDto;
}
