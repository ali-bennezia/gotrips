import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { FlightReservationDto } from 'src/app/data/reservations/flight-reservation-dto';
import { PeriodReservationDto } from 'src/app/data/reservations/period-reservation-dto';
import { environment } from 'src/environments/environment';

import { lastValueFrom } from 'rxjs';
import { tap } from 'rxjs/operators';

enum PaymentState {
  UNINITIALIZED,
  LOADING,
  ERROR,
  SUCCESS,
}

export interface Period {
  beginTime: number;
  endTime: number;
}

interface PaymentInfo {
  offerType: string;
  offerId: number;
  paymentMeanId: number;
  period?: Period;
}

@Component({
  selector: 'app-reservation-pay-page',
  templateUrl: './reservation-pay-page.component.html',
  styleUrls: ['./reservation-pay-page.component.css'],
})
export class ReservationPayPageComponent {
  paymentStateEnum: typeof PaymentState = PaymentState;
  state: PaymentState = PaymentState.UNINITIALIZED;
  info: PaymentInfo | null = null;
  errorDisplay: string = '';

  queryParamMap: ParamMap | null = null;

  constructor(
    activatedRoute: ActivatedRoute,
    private http: HttpClient,
    private authService: AuthService
  ) {
    activatedRoute.paramMap.subscribe((params) => {
      if (
        !params.has('offerType') ||
        !params.has('offerId') ||
        !params.has('paymentMeanId')
      ) {
        this.info = null;
      } else {
        let offerType = params.get('offerType');
        let offerId = Number(params.get('offerId'));
        let paymentMeanId = Number(params.get('paymentMeanId'));
        this.info = {
          offerType: offerType ?? '',
          offerId: offerId ?? -1,
          paymentMeanId: paymentMeanId ?? -1,
        };
        if (
          activatedRoute.snapshot.queryParamMap.has('beginTime') &&
          activatedRoute.snapshot.queryParamMap.has('endTime')
        ) {
          this.info.period = {
            beginTime: Number(
              activatedRoute.snapshot.queryParamMap.get('beginTime')
            ),
            endTime: Number(
              activatedRoute.snapshot.queryParamMap.get('endTime')
            ),
          };
        }
      }

      if (
        !this.info ||
        this.info?.offerType == '' ||
        this.info?.offerId < 0 ||
        this.info?.paymentMeanId < 0
      ) {
        this.errorDisplay = "Couldn't load payment information.";
      } else {
        this.sendPaymentRequest();
      }
    });
  }

  getDto(): FlightReservationDto | PeriodReservationDto {
    switch (this.info?.offerType) {
      case 'flight':
        return {
          cardId: this.info!.paymentMeanId,
        };
      default:
        return {
          cardId: this.info!.paymentMeanId,
          beginTime: this.info!.period!.beginTime,
          endTime: this.info!.period!.endTime,
        };
    }
  }

  handleError(code: number) {
    switch (code) {
      case 0:
        this.errorDisplay = 'Client-side error.';
        break;
      case 404:
        this.errorDisplay = 'Means of payment not found.';
        break;
      case 400:
        this.errorDisplay = 'Incorrect parameters.';
        break;
      default:
        this.errorDisplay = 'Internal server error.';
        break;
    }
    this.state = PaymentState.ERROR;
  }

  sendPaymentRequest() {
    this.state = PaymentState.LOADING;
    this.http
      .post(
        `${environment.backendUrl}/api/${this.info?.offerType}/${this.info?.offerId}/reservations/create`,
        this.getDto(),
        {
          headers: {
            Authorization: `Bearer ${this.authService.session?.token}`,
            'Content-Type': 'application/json',
          },
        }
      )
      .subscribe({
        next: (resp) => {
          this.state = PaymentState.SUCCESS;
        },
        error: (err) => {
          this.handleError(err.status);
        },
      });
  }
}
