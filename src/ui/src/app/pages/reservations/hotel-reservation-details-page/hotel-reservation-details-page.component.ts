import { Component, OnDestroy, OnInit } from '@angular/core';

import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { HotelReservationDetailsDto } from 'src/app/data/reservations/hotel-reservation-details-dto';
import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-hotel-reservation-details-page',
  templateUrl: './hotel-reservation-details-page.component.html',
  styleUrls: ['./hotel-reservation-details-page.component.css'],
})
export class HotelReservationDetailsPageComponent implements OnInit, OnDestroy {
  errorDisplay: string = '';
  loading: boolean = false;
  id: number | null = null;
  data: HotelReservationDetailsDto | null = null;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    activatedRoute: ActivatedRoute
  ) {
    activatedRoute.paramMap.subscribe((params) => {
      this.id = (params.has('id') ? Number(params.get('id')) : -1) ?? -1;
      if (isNaN(this.id)) this.id = null;
      this.fetchData();
    });
  }

  handleError(code: number) {
    switch (code) {
      case 404:
        this.errorDisplay = "Couldn't find reservation.";
        break;
      case 403:
        this.errorDisplay = 'Insufficient authorizations.';
        break;
      case 0:
        this.errorDisplay = 'Client-side error.';
        break;
      default:
        this.errorDisplay = 'Internal server error.';
        break;
    }
  }

  cleanupSubscription() {
    if (this.subscription) this.subscription.unsubscribe();
    this.subscription = null;
  }

  subscription: Subscription | null = null;
  fetchData() {
    if (this.loading == true) return;
    this.cleanupSubscription();
    this.loading = true;
    if (this.id == null) {
      this.errorDisplay = 'Incorrect id parameter.';
      this.loading = false;
      return;
    }
    this.subscription = this.http
      .get<HotelReservationDetailsDto>(
        `${environment.backendUrl}/api/hotel/reservations/get/${this.id}`,
        {
          headers: {
            Authorization: `Bearer ${this.authService.session?.token}`,
          },
          observe: 'response',
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
          this.handleError(err.status);
        },
      });
  }

  ngOnInit(): void {}

  ngOnDestroy(): void {
    this.cleanupSubscription();
  }
}
