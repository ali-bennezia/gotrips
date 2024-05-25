import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild, AfterViewChecked } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, ParamMap } from '@angular/router';

import { CalendarPairUnitDto } from 'src/app/data/calendar/calendar-pair-unit-dto';
import { FlightDetailsDto } from 'src/app/data/flight/flight-details-dto';
import { CalendarComponent } from 'src/app/utils/calendar/calendar.component';
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

interface InputPair {
  key: string;
  value: any;
}

@Component({
  selector: 'app-flights-search-page',
  templateUrl: './flights-search-page.component.html',
  styleUrls: ['./flights-search-page.component.css'],
})
export class FlightsSearchPageComponent implements OnInit, AfterViewChecked {
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
    this.onInput('midate', d.getTime());
  };

  onArrivalSelectedDateChanged = (d: Date) => {
    this.arrivalDate = d;
    this.fetchDepartureAvailableDates();
    this.onInput('mxdate', d.getTime());
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

  private _page: number = 1;
  set page(val: number) {
    this._page = val;
    this.onInput('page', val);
  }
  get page() {
    return this._page;
  }

  group!: FormGroup;

  @ViewChild('departureCalendar', { static: true, read: CalendarComponent })
  departureCalendar!: CalendarComponent;
  @ViewChild('arrivalCalendar', { static: true, read: CalendarComponent })
  arrivalCalendar!: CalendarComponent;

  private params: ParamMap | null = null;
  private initializedParams: boolean = false;

  constructor(
    private http: HttpClient,
    builder: FormBuilder,
    activatedRoute: ActivatedRoute
  ) {
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

    activatedRoute.queryParamMap.subscribe((params) => {
      this.params = params;
    });
  }

  getSearchOptions(newInput?: InputPair) {
    let opts: any = {
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
    if (newInput != undefined) {
      opts[newInput.key] = newInput.value;
    }
    for (let p in opts) {
      if (opts[p] == null || opts[p] == 'null') delete opts[p];
    }
    return opts;
  }

  getUrlParametersString(newInput?: InputPair) {
    let params: URLSearchParams = new URLSearchParams();
    let opts: any = this.getSearchOptions(newInput);

    for (let p in opts) {
      let val = opts[p];
      if (val != null && val != undefined && val != 'null')
        params.append(p, val);
    }

    return params.toString();
  }

  results: FlightDetailsDto[] = [];

  fetchResults(newInput?: InputPair) {
    var strParams = this.getUrlParametersString(newInput);
    console.log(strParams);
    this.http
      .get<FlightDetailsDto[]>(
        `${environment.backendUrl}/api/flight/search${
          strParams != '' && strParams ? `?${strParams}` : ''
        }`
      )
      .subscribe({
        next: (data) => {
          console.log(this.results);
          this.results = data ?? [];
        },
        error: (err) => {},
      });
  }

  onInput(inputName: string, value: any) {
    setTimeout(() => {
      let newInput: InputPair = { key: inputName, value: value };
      this.fetchResults(newInput);
    }, 100);
  }

  ngOnInit(): void {
    this.fetchResults();
  }

  ngAfterViewChecked(): void {
    if (!this.params || this.initializedParams) return;
    this.initializedParams = true;

    for (let c in this.group.controls) {
      this.group.get(c)?.setValue(this.params.get(c));
    }
    if (this.params!.get('departureDate') != null) {
      this.departureCalendar.selectDateNoEvent(
        new Date(Number(this.params.get('departureDate')))
      );
    }
    if (this.params.get('arrivalDate') != null) {
      this.arrivalCalendar.selectDateNoEvent(
        new Date(new Date(Number(this.params.get('arrivalDate'))))
      );
    }
  }
}
