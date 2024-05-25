import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';

import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { ActivityDetailsDto } from 'src/app/data/activity/activity-details-dto';

@Component({
  selector: 'app-activity-list-page',
  templateUrl: './activity-list-page.component.html',
  styleUrls: ['./activity-list-page.component.css'],
})
export class ActivityListPageComponent {
  fetchingSubscription!: Subscription;
  loading: boolean = false;
  errorDisplay: string = '';
  list: ActivityDetailsDto[] = [];
  constructor(private http: HttpClient, private authService: AuthService) {}

  loadings: number[] = [];

  getCompany() {
    return this.authService.session?.activityCompany;
  }

  isRowLoading(id: number) {
    return this.loadings.includes(id);
  }

  deleteActivity = (id: number) => {
    this.loadings.push(id);
    this.http
      .delete(`${environment.backendUrl}/api/activity/${id}/delete`, {
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
      .get<ActivityDetailsDto[]>(
        `${environment.backendUrl}/api/activity/company/${
          this.getCompany()?.id
        }/activities/getAll`,
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

  ngOnDestroy(): void {}
}
