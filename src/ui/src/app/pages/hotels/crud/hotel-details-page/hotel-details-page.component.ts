import { HttpClient } from '@angular/common/http';
import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { Subscription } from 'rxjs';
import { EvaluationDto } from 'src/app/data/user/evaluation-dto';
import { EvaluationListComponent } from 'src/app/utils/evaluations/evaluation-list/evaluation-list.component';
import { CardDetailsDto } from 'src/app/data/user/card-details-dto';
import { UserService } from 'src/app/services/user.service';
import { ListGroupItem } from 'src/app/utils/interactive-list-group/interactive-list-group.component';

import { areDatesOnSameDay } from 'src/app/utils/dateUtils';
import { CalendarPairUnitDto } from 'src/app/data/calendar/calendar-pair-unit-dto';
import { HotelDetailsDto } from 'src/app/data/hotel/hotel-details-dto';

interface InputPair {
  key: string;
  value: any;
}

@Component({
  selector: 'app-hotel-details-page',
  templateUrl: './hotel-details-page.component.html',
  styleUrls: ['./hotel-details-page.component.css'],
})
export class HotelDetailsPageComponent {
  loading: boolean = false;
  data: HotelDetailsDto | null = null;
  dataSubscription: Subscription | null = null;
  errorDisplay: string = '';

  @ViewChild('evaluationList', { read: EvaluationListComponent, static: false })
  evaluationList!: EvaluationListComponent;

  userPaymentMethods: CardDetailsDto[] = [];
  userPaymentList: ListGroupItem[] = [];
  paymentMeanId: number | null = null;
  getUserPaymentMethodListItems: () => ListGroupItem[] = () => {
    return this.userPaymentMethods.map((d) => {
      return {
        title: d.name,
        content: [
          `${d.partialCardNumber}`,
          `${d.address.street} ${d.address.zipCode}, ${d.address.city}`,
        ],
        value: d.id,
      };
    });
  };

  handleSentEvaluation = (dto: EvaluationDto) => {
    if (this.data != null) {
      this.fetchData(this.data.id);
    }
    if (this.evaluationList) {
      this.evaluationList.fetchList();
    }
  };

  constructor(
    private http: HttpClient,
    public authService: AuthService,
    activatedRoute: ActivatedRoute,
    private userService: UserService
  ) {
    activatedRoute.paramMap.subscribe((params) => {
      let id = Number(params.get('id') ?? '-1');
      if (id >= 0) {
        this.fetchData(id);
        this.fetchUserPaymentMethods();
      } else {
        this.data = null;
      }
    });
  }

  cleanup() {
    if (this.dataSubscription != null) {
      this.dataSubscription.unsubscribe();
    }
  }

  roundedAverageEval() {
    return Math.round(this.data!.averageEvaluation);
  }

  fetchData(id: number) {
    this.cleanup();
    this.dataSubscription = this.http
      .get<HotelDetailsDto>(
        `${environment.backendUrl}/api/hotel/${id}/details`,
        {
          observe: 'response',
          headers: {
            Authorization: `Bearer ${this.authService.session?.token}`,
          },
        }
      )
      .pipe(
        tap((_) => {
          this.loading = false;
        })
      )
      .subscribe({
        next: (resp) => {
          this.data = resp.body;
          this.tryFetchRservationDates();
        },
        error: (err) => {
          switch (err.status) {
            case 0:
              this.errorDisplay = 'Client-side error.';
              break;
            default:
              this.errorDisplay = 'Internal server error.';
              break;
          }
        },
      });
  }

  userPaymentMethodsSubscription: Subscription | null = null;
  authenticationSubscription: Subscription | null = null;
  fetchUserPaymentMethods() {
    if (this.userPaymentMethodsSubscription)
      this.userPaymentMethodsSubscription.unsubscribe();
    if (this.authenticationSubscription)
      this.authenticationSubscription.unsubscribe();
    this.userPaymentMethodsSubscription = this.userService
      .getUserPaymentMethodsAsync()
      .subscribe((data) => {
        this.userPaymentMethods = data;
        this.userPaymentList = this.getUserPaymentMethodListItems();
      });
  }

  ngOnInit(): void {
    this.authenticationSubscription =
      this.authService.onAuthenticated$.subscribe((sess) => {
        this.fetchUserPaymentMethods();
      });
  }

  ngOnDestroy(): void {
    if (this.userPaymentMethodsSubscription)
      this.userPaymentMethodsSubscription.unsubscribe();
    if (this.authenticationSubscription)
      this.authenticationSubscription.unsubscribe();
    this.cleanup();
  }

  availableReservationDates: CalendarPairUnitDto[] = [];

  fetchAvailableReservationDates() {
    this.http
      .get<CalendarPairUnitDto[]>(
        `${environment.backendUrl}/api/hotel/${
          this.data?.id
        }/calendar/search/${this.firstKnownDate?.getTime()}/${this.lastKnownDate?.getTime()}`,
        {
          observe: 'response',
        }
      )
      .subscribe({
        next: (resp) => {
          this.availableReservationDates = resp.body ?? [];
        },
        error: (err) => {},
      });
  }

  calendarDaysDisabledPredicate = (d: Date) => {
    return !(
      this.availableReservationDates
        .filter((o) => areDatesOnSameDay(new Date(o.timestamp), d))
        .map((o) => o.found)
        .filter((o) => o == true).length > 0
    );
  };

  tryFetchRservationDates() {
    if (
      this.firstKnownDate != null &&
      this.lastKnownDate != null &&
      this.data != null
    )
      this.fetchAvailableReservationDates();
  }

  beginDate: Date | null = null;
  endDate: Date | null = null;
  private _firstKnownDate: Date | null = null;
  private _lastKnownDate: Date | null = null;
  get firstKnownDate() {
    return this._firstKnownDate;
  }
  set firstKnownDate(val: Date | null) {
    this._firstKnownDate = val;
    this.tryFetchRservationDates();
  }
  get lastKnownDate() {
    return this._lastKnownDate;
  }
  get totalDays() {
    if (this.beginDate == null || this.endDate == null) return 0;
    return (
      (this.endDate.getTime() - this.beginDate.getTime()) / (1000 * 3600 * 24) +
      1
    );
  }
  set lastKnownDate(val: Date | null) {
    this._lastKnownDate = val;
    this.tryFetchRservationDates();
  }
}
