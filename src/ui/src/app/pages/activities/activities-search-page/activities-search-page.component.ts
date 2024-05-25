import { HttpClient } from '@angular/common/http';
import { AfterViewChecked, Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ActivityDetailsDto } from 'src/app/data/activity/activity-details-dto';

import { CalendarPairUnitDto } from 'src/app/data/calendar/calendar-pair-unit-dto';
import { FlightDetailsDto } from 'src/app/data/flight/flight-details-dto';
import { CalendarComponent } from 'src/app/utils/calendar/calendar.component';
import { environment } from 'src/environments/environment';

interface InputPair {
  key: string;
  value: any;
}

@Component({
  selector: 'app-activities-search-page',
  templateUrl: './activities-search-page.component.html',
  styleUrls: ['./activities-search-page.component.css'],
})
export class ActivitiesSearchPageComponent implements AfterViewChecked {
  beginDate: Date | null = null;
  endDate: Date | null = null;

  beginAvailableDates: CalendarPairUnitDto[] = [];
  endAvailableDates: CalendarPairUnitDto[] = [];

  beginViewedMonth: Date | null = null;
  beginFirstKnownDate: Date | null = null;
  beginLastKnownDate: Date | null = null;

  endFirstKnownDate: Date | null = null;
  endLastKnownDate: Date | null = null;

  onBeginSelectedDateChanged = (d: Date) => {
    this.beginDate = d;
    this.onInput('midate', d);
  };

  onEndSelectedDateChanged = (d: Date) => {
    this.endDate = d;
    this.onInput('mxdate', d);
  };

  showExtraFilters: boolean = false;

  sortingOptions: string[][] = [
    ['Price per day', 'price_per_day'],
    ['Average evalution', 'average_evaluation'],
    ['Spots', 'spots'],
    ['City', 'city'],
    ['Country', 'country'],
    ['Zip code', 'zip_code'],
    ['Title', 'title'],
    ['Description', 'description'],
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

  @ViewChild('beginCalendar', { static: true, read: CalendarComponent })
  beginCalendar!: CalendarComponent;
  @ViewChild('endCalendar', { static: true, read: CalendarComponent })
  endCalendar!: CalendarComponent;

  private params: ParamMap | null = null;
  private initializedParams: boolean = false;

  constructor(
    private http: HttpClient,
    builder: FormBuilder,
    activatedRoute: ActivatedRoute
  ) {
    this.group = builder.group({
      query: [null],
      country: [null],
      city: [null],
      minPrice: [null],
      maxPrice: [null],
      minEval: [null],
      maxEval: [null],
      sortBy: [null],
      sortOrder: [null],
    });

    activatedRoute.queryParamMap.subscribe((params) => {
      this.params = params;
    });
  }

  getSearchOptions(newInput?: InputPair) {
    let opts: any = {
      qry: this.group.get('query')?.value,
      page: this.page - 1,
      cntry: this.group.get('country')?.value,
      city: this.group.get('city')?.value,
      miprc: this.group.get('minPrice')?.value,
      mxprc: this.group.get('maxPrice')?.value,
      mieval: this.group.get('minEval')?.value,
      mxeval: this.group.get('maxEval')?.value,
      srtby: this.group.get('sortBy')?.value,
      srtordr: this.group.get('sortOrder')?.value,
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

  results: ActivityDetailsDto[] = [];

  fetchResults(newInput?: InputPair) {
    var strParams = this.getUrlParametersString(newInput);
    console.log(strParams);
    this.http
      .get<ActivityDetailsDto[]>(
        `${environment.backendUrl}/api/activity/search${
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

  ngOnInit(): void {}

  ngAfterViewChecked(): void {
    if (!this.params || this.initializedParams) return;
    this.initializedParams = true;

    for (let c in this.group.controls) {
      this.group.get(c)?.setValue(this.params.get(c));
    }
    if (this.params!.get('beginDate') != null) {
      this.beginCalendar.selectDateNoEvent(
        new Date(Number(this.params.get('beginDate')))
      );
    }
    if (this.params.get('endDate') != null) {
      this.endCalendar.selectDateNoEvent(
        new Date(new Date(Number(this.params.get('endDate'))))
      );
    }
    this.fetchResults();
  }
}
