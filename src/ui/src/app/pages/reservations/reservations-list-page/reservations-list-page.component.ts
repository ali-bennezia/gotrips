import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { HotelReservationDetailsDto } from 'src/app/data/hotel/hotel-reservation-details-dto';
import { ActivityReservationDetailsDto } from 'src/app/data/reservations/activity-reservation-details-dto';
import { FlightReservationDetailsDto } from 'src/app/data/reservations/flight-reservation-details-dto';

import { Observable, of } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-reservations-list-page',
  templateUrl: './reservations-list-page.component.html',
  styleUrls: ['./reservations-list-page.component.css'],
})
export class ReservationsListPageComponent {
  currentTab: number = -1;
  errorDisplay: string = '';

  flights: FlightReservationDetailsDto[] = [];
  hotels: HotelReservationDetailsDto[] = [];
  activity: ActivityReservationDetailsDto[] = [];

  page: number = 1;

  setTab(i: number) {
    let previousTab = i;
    this.currentTab = i;
    if (previousTab >= 0) this.onTabClosed(previousTab);
    if (this.currentTab >= 0) this.onTabOpened(this.currentTab);
  }

  onTabClosed(i: number) {
    switch (i) {
      case 2:

      default:
        break;
    }
  }
  onTabOpened(i: number) {
    switch (i) {
      case 2:
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

  fetchFlightReservations() {
    this.fetchList<FlightReservationDetailsDto>('flight').subscribe({
      next: (resp) => {},
      error: (err) => {},
    });
  }
}
