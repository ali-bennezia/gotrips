import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';

import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { HotelDetailsDto } from 'src/app/data/hotel/hotel-details-dto';

@Component({
  selector: 'app-hotel-list-page',
  templateUrl: './hotel-list-page.component.html',
  styleUrls: ['./hotel-list-page.component.css'],
})
export class HotelListPageComponent implements OnInit {
  fetchingSubscription!: Subscription;
  loading: boolean = false;
  errorDisplay: string = '';
  list: HotelDetailsDto[] = [];
  constructor(private http: HttpClient, private authService: AuthService) {}

  loadings: number[] = [];

  getCompany() {
    return this.authService.session?.hotelCompany;
  }

  isRowLoading(id: number) {
    return this.loadings.includes(id);
  }

  deleteHotel = (id: number) => {
    this.loadings.push(id);
    this.http
      .delete(`${environment.backendUrl}/api/hotel/${id}/delete`, {
        headers: {
          Authorization: `Bearer ${this.authService.session?.token}`,
        },
      })
      .pipe(
        tap((_) => {
          this.loadings = this.loadings.filter((d) => d != id);
        })
      )
      .subscribe({
        next: () => {
          this.fetchList();
        },
        error: (err) => {
          switch (err.status) {
            case 0:
              this.errorDisplay = 'Client-side error.';
              break;
            case 403:
              this.errorDisplay = 'Insufficient authorizations.';
              break;
            default:
              this.errorDisplay = 'Internal server error.';
              break;
          }
        },
      });
  };

  fetchList() {
    this.loading = true;
    this.fetchingSubscription = this.http
      .get<HotelDetailsDto[]>(
        `${environment.backendUrl}/api/hotel/company/${
          this.getCompany()?.id
        }/hotels/getAll`,
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
        next: (data) => {
          this.list = data.body ?? [];
          console.log(this.list);
        },
        error: (err) => {
          switch (err.status) {
            case 0:
              this.errorDisplay = 'Client-side error.';
              break;
            case 400:
              this.errorDisplay = 'Bad request.';
              break;
            case 403:
            case 400:
              this.errorDisplay = 'Insufficient authorizations.';
              break;
            default:
              this.errorDisplay = 'Internal server error.';
              break;
          }
        },
      });
  }

  ngOnInit(): void {
    this.fetchList();
  }
}
