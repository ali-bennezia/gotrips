import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { FlightDetailsDto } from 'src/app/data/flight/flight-details-dto';

import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-flight-details-page',
  templateUrl: './flight-details-page.component.html',
  styleUrls: ['./flight-details-page.component.css'],
})
export class FlightDetailsPageComponent implements OnDestroy {
  loading: boolean = false;
  data: FlightDetailsDto | null = null;
  dataSubscription: Subscription | null = null;
  errorDisplay: string = '';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    activatedRoute: ActivatedRoute
  ) {
    activatedRoute.paramMap.subscribe((params) => {
      let id = Number(params.get('id') ?? '-1');
      if (id >= 0) {
        this.fetchData(id);
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
      .get<FlightDetailsDto>(
        `${environment.backendUrl}/api/flight/${id}/details`,
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

  ngOnDestroy(): void {
    this.cleanup();
  }
}
