import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-flights-page',
  templateUrl: './flights-page.component.html',
  styleUrls: ['./flights-page.component.css'],
})
export class FlightsPageComponent {
  departureCalendarDaysDisabledPredicate: (date: Date) => boolean = (
    d: Date
  ) => {
    return true;
  };

  group!: FormGroup;
  constructor(builder: FormBuilder) {
    this.group = builder.group({});
  }

  onSubmit(e: Event) {}
}
