import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { CalendarPairUnitDto } from 'src/app/data/calendar/calendar-pair-unit-dto';
import { FlightDetailsDto } from 'src/app/data/flight/flight-details-dto';
import { areDatesOnSameDay } from 'src/app/utils/dateUtils';
import { environment } from 'src/environments/environment';

interface SearchOptions {
  departureCountry?: string;
  arrivalCountry?: string;
  departureDate?: Date;
  arrivalDate?: Date;
  minPrice?: number;
  maxPrice?: number;
  minEval?: number;
  maxEval?: number;
  sortBy?: string;
  sortOrder?: number;
  query?: string;
  page?: number;
}

@Component({
  selector: 'app-flights-search-page',
  templateUrl: './flights-search-page.component.html',
  styleUrls: ['./flights-search-page.component.css'],
})
export class FlightsSearchPageComponent {
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
    this.onInput();
  };

  onArrivalSelectedDateChanged = (d: Date) => {
    this.arrivalDate = d;
    this.fetchDepartureAvailableDates();
    this.onInput();
  };

  onDepartureViewedMonthChanged = (d: Date) => {
    this.fetchDepartureAvailableDates();
  };
  onArrivalViewedMonthChanged = (d: Date) => {
    this.fetchArrivalAvailableDates();
  };

  showExtraFilters: boolean = false;

  sortingOptions: string[][] = [
    ['Price', 'price'],
    ['Average evalution', 'average_evaluation'],
    ['Seats', 'seats'],
    ['Arrival city', 'arrival_city'],
    ['Arrival country', 'arrival_country'],
    ['Departure city', 'departure_city'],
    ['Departure country', 'departure_country'],
  ];

  page: number = 1;

  group!: FormGroup;

  constructor(private http: HttpClient, private builder: FormBuilder) {
    this.group = builder.group({
      departureCountry: [null],
      arrivalCountry: [null],
      minPrice: [null],
      maxPrice: [null],
      minEval: [null],
      maxEval: [null],
      sortBy: [null],
      sortOrder: [null],
      query: [null],
    });
  }

  getSearchOptions() {
    return {
      ocntry: this.group.get('departureCountry')?.value,
      dcntry: this.group.get('arrivalCountry')?.value,
      midate: this.departureDate?.getTime(),
      mxdate: this.arrivalDate?.getTime(),
      miprc: this.group.get('minPrice')?.value,
      mxprc: this.group.get('maxPrice')?.value,
      mieval: this.group.get('minEval')?.value,
      mxeval: this.group.get('maxEval')?.value,
      srtby: this.group.get('sortBy')?.value,
      srtordr: this.group.get('sortOrder')?.value,
      qry: this.group.get('query')?.value,
      page: this.page - 1,
    };
  }

  getUrlParametersString() {
    let params: URLSearchParams = new URLSearchParams();
    let opts: any = this.getSearchOptions();

    for (let p in opts) {
      let val = opts[p];
      if (val != null && val != undefined) params.append(p, val);
    }
    return params.toString();
  }

  results: FlightDetailsDto[] = [];

  fetchResults() {
    let strParams = this.getUrlParametersString();
    this.http
      .get<FlightDetailsDto[]>(
        `${environment.backendUrl}/api/flight/search${
          strParams != '' && strParams ? `?${strParams}` : ''
        }`
      )
      .subscribe({
        next: (data) => {
          this.results = data ?? [];
          console.log(this.results);
        },
        error: (err) => {},
      });
  }

  onInput = () => {
    this.fetchResults();
  };
}
