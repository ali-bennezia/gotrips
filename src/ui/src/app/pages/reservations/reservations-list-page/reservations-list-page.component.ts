import { Component, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { HotelReservationDetailsDto } from 'src/app/data/hotel/hotel-reservation-details-dto';
import { ActivityReservationDetailsDto } from 'src/app/data/reservations/activity-reservation-details-dto';
import { FlightReservationDetailsDto } from 'src/app/data/reservations/flight-reservation-details-dto';

import { Observable, Subscription, of } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-reservations-list-page',
  templateUrl: './reservations-list-page.component.html',
  styleUrls: ['./reservations-list-page.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class ReservationsListPageComponent {
  currentTab: number = -1;
  errorDisplay: string = '';

  flights: FlightReservationDetailsDto[] = [];
  hotels: HotelReservationDetailsDto[] = [];
  activities: ActivityReservationDetailsDto[] = [];

  page: number = 1;

  setTab(i: number) {
    let previousTab = i;
    this.currentTab = i;
    if (previousTab >= 0) this.onTabClosed(previousTab);
    if (this.currentTab >= 0) this.onTabOpened(this.currentTab);
  }

  onTabClosed(i: number) {
    switch (i) {
      case 0:
        this.cleanupFlightReservations();
        break;
      case 1:
        this.cleanupHotelReservations();
        break;
      case 2:
        this.cleanupActivityReservations();
        break;
      default:
        break;
    }
  }
  onTabOpened(i: number) {
    switch (i) {
      case 0:
        this.fetchFlightReservations();
        break;
      case 1:
        this.fetchHotelReservations();
        break;
      case 2:
        this.fetchActivityReservations();
        break;
      default:
        break;
    }
  }

  constructor(
    activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private http: HttpClient
  ) {
    activatedRoute.queryParamMap.subscribe((params) => {
      let loadTab = Number(params.get('tab') ?? '-1');

      if (loadTab >= 0) this.setTab(loadTab);
      else this.setTab(0);
    });
  }

  handleError(code: number) {
    switch (code) {
      case 0:
        this.errorDisplay = 'Client-side error.';
        break;
      case 403:
        this.errorDisplay = 'Insufficient authorization.';
        break;
      case 400:
        this.errorDisplay = 'Incorrect parameters.';
        break;
      default:
        this.errorDisplay = 'Internal server error.';
        break;
    }
  }

  fetchList<T>(offerType: string): Observable<HttpResponse<T[]>> {
    return this.http.get<T[]>(
      `${environment.backendUrl}/api/${offerType}/reservations/getAll?page=${
        this.page - 1
      }`,
      {
        headers: {
          Authorization: `Bearer ${this.authService.session?.token}`,
        },
        observe: 'response',
      }
    );
  }

  cleanupFlightReservations() {
    if (this.flightSubscription) this.flightSubscription.unsubscribe();
    this.flights = [];
  }

  cleanupHotelReservations() {
    if (this.hotelSubscription) this.hotelSubscription.unsubscribe();
    this.hotels = [];
  }

  cleanupActivityReservations() {
    if (this.activitySubscription) this.activitySubscription.unsubscribe();
    this.activities = [];
  }

  flightSubscription: Subscription | null = null;
  fetchFlightReservations() {
    this.cleanupFlightReservations();
    this.flightSubscription = this.fetchList<FlightReservationDetailsDto>(
      'flight'
    ).subscribe({
      next: (resp) => {
        this.flights = resp.body ?? [];
      },
      error: (err) => {},
    });
  }

  hotelSubscription: Subscription | null = null;
  fetchHotelReservations() {
    this.cleanupHotelReservations();
    this.hotelSubscription = this.fetchList<HotelReservationDetailsDto>(
      'hotel'
    ).subscribe({
      next: (resp) => {
        this.hotels = resp.body ?? [];
      },
      error: (err) => {},
    });
  }

  activitySubscription: Subscription | null = null;
  fetchActivityReservations() {
    this.cleanupActivityReservations();
    this.activitySubscription = this.fetchList<ActivityReservationDetailsDto>(
      'activity'
    ).subscribe({
      next: (resp) => {
        this.activities = resp.body ?? [];
      },
      error: (err) => {},
    });
  }
}
