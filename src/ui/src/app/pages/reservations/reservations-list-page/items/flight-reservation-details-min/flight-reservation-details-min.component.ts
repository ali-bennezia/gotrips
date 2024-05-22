import { Component, Input, ViewEncapsulation } from '@angular/core';
import { FlightReservationDetailsDto } from 'src/app/data/reservations/flight-reservation-details-dto';

@Component({
  selector: 'app-flight-reservation-details-min',
  templateUrl: './flight-reservation-details-min.component.html',
  styleUrls: ['./flight-reservation-details-min.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class FlightReservationDetailsMinComponent {
  @Input()
  data!: FlightReservationDetailsDto;

  getDate(time: number) {
    return new Date(time);
  }
}
