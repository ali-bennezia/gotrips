import { Component, Input, ViewEncapsulation } from '@angular/core';
import { ActivityReservationDetailsDto } from 'src/app/data/reservations/activity-reservation-details-dto';

@Component({
  selector: 'app-activity-reservation-details-min',
  templateUrl: './activity-reservation-details-min.component.html',
  styleUrls: ['./activity-reservation-details-min.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class ActivityReservationDetailsMinComponent {
  @Input()
  data!: ActivityReservationDetailsDto;

  getDate(time: number) {
    return new Date(time);
  }
}
