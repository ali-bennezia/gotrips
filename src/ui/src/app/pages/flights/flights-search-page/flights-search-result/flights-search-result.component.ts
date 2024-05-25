import { Component, Input } from '@angular/core';
import { FlightDetailsDto } from 'src/app/data/flight/flight-details-dto';

@Component({
  selector: 'app-flights-search-result',
  templateUrl: './flights-search-result.component.html',
  styleUrls: ['./flights-search-result.component.css'],
})
export class FlightsSearchResultComponent {
  @Input()
  details!: FlightDetailsDto;
}
