import { Component, Input, ViewEncapsulation } from '@angular/core';
import { HotelReservationDetailsDto } from 'src/app/data/reservations/hotel-reservation-details-dto';

@Component({
  selector: 'app-hotel-reservation-details-min',
  templateUrl: './hotel-reservation-details-min.component.html',
  styleUrls: ['./hotel-reservation-details-min.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class HotelReservationDetailsMinComponent {
  @Input()
  data!: HotelReservationDetailsDto;

  getDate(time: number) {
    return new Date(time);
  }
}
