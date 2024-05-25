import { Component, Input } from '@angular/core';
import { HotelDetailsDto } from 'src/app/data/hotel/hotel-details-dto';

@Component({
  selector: 'app-hotels-search-result',
  templateUrl: './hotels-search-result.component.html',
  styleUrls: ['./hotels-search-result.component.css'],
})
export class HotelsSearchResultComponent {
  @Input()
  details!: HotelDetailsDto;
}
