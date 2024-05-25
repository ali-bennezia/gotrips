import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { FlightDetailsDto } from 'src/app/data/flight/flight-details-dto';

import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { Subscription } from 'rxjs';
import { EvaluationDto } from 'src/app/data/user/evaluation-dto';
import { EvaluationListComponent } from 'src/app/utils/evaluations/evaluation-list/evaluation-list.component';
import { CardDetailsDto } from 'src/app/data/user/card-details-dto';
import { UserService } from 'src/app/services/user.service';
import { ListGroupItem } from 'src/app/utils/interactive-list-group/interactive-list-group.component';
import { ActivityDetailsDto } from 'src/app/data/activity/activity-details-dto';

@Component({
  selector: 'app-activity-details-page',
  templateUrl: './activity-details-page.component.html',
  styleUrls: ['./activity-details-page.component.css'],
})
export class ActivityDetailsPageComponent {
  loading: boolean = false;
  data: ActivityDetailsDto | null = null;
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
      .get<ActivityDetailsDto>(
        `${environment.backendUrl}/api/activity/${id}/details`,
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

  calendarDaysDisabledPredicate = (d: Date) => {
    return false;
  };

  beginDate: Date | null = null;
  endDate: Date | null = null;
  firstKnownDate: Date | null = null;
  lastKnownDate: Date | null = null;
}
