import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CalendarPairUnitDto } from 'src/app/data/calendar/calendar-pair-unit-dto';
import { areDatesOnSameDay } from 'src/app/utils/dateUtils';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-flights-page',
  templateUrl: './flights-page.component.html',
  styleUrls: ['./flights-page.component.css'],
})
export class FlightsPageComponent {
  departureCalendarDaysDisabledPredicate: (date: Date) => boolean = (
    d: Date
  ) => {
    return !(
      this.departureAvailableDates
        .filter((o) => areDatesOnSameDay(new Date(o.timestamp), d))
        .map((o) => o.found)
        .filter((o) => o == true).length > 0
    );
  };
  arrivalCalendarDaysDisabledPredicate: (date: Date) => boolean = (d: Date) => {
    return !(
      this.arrivalAvailableDates
        .filter((o) => areDatesOnSameDay(new Date(o.timestamp), d))
        .map((o) => o.found)
        .filter((o) => o == true).length > 0
    );
  };

  fetchDepartureAvailableDates() {
    this.http
      .get<CalendarPairUnitDto[]>(
        `${
          environment.backendUrl
        }/api/flight/calendar/search/departure/${this.departureFirstKnownDate?.getTime()}/${this.departureLastKnownDate?.getTime()}${
          this.departureDate != null
            ? `?landing=${this.arrivalDate?.getTime()}`
            : ''
        }`,
        {
          observe: 'response',
        }
      )
      .subscribe({
        next: (resp) => {
          this.departureAvailableDates = resp.body ?? [];
        },
        error: (err) => {},
      });
  }
  fetchArrivalAvailableDates() {
    this.http
      .get<CalendarPairUnitDto[]>(
        `${
          environment.backendUrl
        }/api/flight/calendar/search/landing/${this.arrivalFirstKnownDate?.getTime()}/${this.arrivalLastKnownDate?.getTime()}${
          this.departureDate != null
            ? `?departure=${this.departureDate.getTime()}`
            : ''
        }`,
        {
          observe: 'response',
        }
      )
      .subscribe({
        next: (resp) => {
          this.arrivalAvailableDates = resp.body ?? [];
        },
        error: (err) => {},
      });
  }

  departureDate: Date | null = null;
  arrivalDate: Date | null = null;

  departureAvailableDates: CalendarPairUnitDto[] = [];
  arrivalAvailableDates: CalendarPairUnitDto[] = [];

  departureViewedMonth: Date | null = null;
  departureFirstKnownDate: Date | null = null;
  departureLastKnownDate: Date | null = null;

  arrivalFirstKnownDate: Date | null = null;
  arrivalLastKnownDate: Date | null = null;

  onDepartureSelectedDateChanged = (d: Date) => {
    this.departureDate = d;
    this.fetchArrivalAvailableDates();
  };

  onArrivalSelectedDateChanged = (d: Date) => {
    this.arrivalDate = d;
    this.fetchDepartureAvailableDates();
  };

  onDepartureViewedMonthChanged = (d: Date) => {
    this.fetchDepartureAvailableDates();
  };
  onArrivalViewedMonthChanged = (d: Date) => {
    this.fetchArrivalAvailableDates();
  };

  group!: FormGroup;
  constructor(
    builder: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.group = builder.group({
      originCountry: [null, [Validators.required]],
      destinationCountry: [null, [Validators.required]],
      query: [''],
    });
  }

  onSubmit(e: Event) {
    let qry = this.group.get('query')?.value;
    qry = qry == '' || qry == undefined ? undefined : qry;

    let oCountry = this.group.get('originCountry')?.value;
    oCountry = oCountry == '' || oCountry == null ? undefined : oCountry;

    let dCountry = this.group.get('destinationCountry')?.value;
    dCountry = dCountry == '' || dCountry == null ? undefined : dCountry;

    this.router.navigate(['/flights', 'search'], {
      queryParams: {
        query: qry,
        departureCountry: oCountry,
        arrivalCountry: dCountry,
        departureDate: this.departureDate?.getTime(),
        arrivalDate: this.arrivalDate?.getTime(),
      },
    });
  }
}
