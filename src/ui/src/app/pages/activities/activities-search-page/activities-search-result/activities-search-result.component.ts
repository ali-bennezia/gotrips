import { Component, Input } from '@angular/core';
import { ActivityDetailsDto } from 'src/app/data/activity/activity-details-dto';

@Component({
  selector: 'app-activities-search-result',
  templateUrl: './activities-search-result.component.html',
  styleUrls: ['./activities-search-result.component.css'],
})
export class ActivitiesSearchResultComponent {
  @Input()
  details!: ActivityDetailsDto;
}
