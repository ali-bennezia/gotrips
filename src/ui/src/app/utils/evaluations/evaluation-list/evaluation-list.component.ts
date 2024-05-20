import { HttpClient } from '@angular/common/http';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { EvaluationDetailsDto } from 'src/app/data/user/evaluation-details-dto';
import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-evaluation-list',
  templateUrl: './evaluation-list.component.html',
  styleUrls: ['./evaluation-list.component.css'],
})
export class EvaluationListComponent implements OnInit, OnDestroy {
  @Input()
  offerType!: string;
  @Input()
  offerId!: number;
  page: number = 1;
  data: EvaluationDetailsDto[] = [];
  loading: boolean = false;

  constructor(private http: HttpClient) {}

  handleError(code: number) {}

  fetchList() {
    this.loading = true;
    this.http
      .get<EvaluationDetailsDto[]>(
        `${environment.backendUrl}/api/${this.offerType}/${this.offerId}/evaluations/getAll?page=${this.page}`,
        { observe: 'response' }
      )
      .pipe(
        tap((_) => {
          this.loading = false;
        })
      )
      .subscribe({
        next: (resp) => {
          this.data = resp.body ?? [];
          console.log(this.data);
        },
        error: (err) => {
          this.handleError(err.status);
        },
      });
  }

  ngOnInit(): void {
    this.fetchList();
  }

  ngOnDestroy(): void {}
}
