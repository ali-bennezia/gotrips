import { HttpClient } from '@angular/common/http';
import { Component, HostListener, Input } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { EvaluationDetailsDto } from 'src/app/data/user/evaluation-details-dto';
import { EvaluationListComponent } from '../evaluation-list/evaluation-list.component';
import { environment } from 'src/environments/environment';
import { Validators } from '@angular/forms';
import { EvaluationDto } from 'src/app/data/user/evaluation-dto';

import { tap } from 'rxjs/operators';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-evaluation-display',
  templateUrl: './evaluation-display.component.html',
  styleUrls: ['./evaluation-display.component.css'],
})
export class EvaluationDisplayComponent {
  @Input()
  evaluation!: EvaluationDetailsDto;
  @Input()
  placement!: string;
  @Input()
  offerType!: string;
  @Input()
  offerId!: number;

  errorDisplay: string = '';

  loading: boolean = false;
  confirmDeletion: boolean = false;

  editMode: boolean = false;

  group!: FormGroup;

  editNote!: number;

  @HostListener('mouseleave')
  onMouseLeave() {
    this.confirmDeletion = false;
  }

  constructor(
    public authService: AuthService,
    private http: HttpClient,
    private evaluationListComponent: EvaluationListComponent,
    builder: FormBuilder
  ) {
    this.group = builder.group({
      title: ['', Validators.required],
      content: ['', [Validators.required]],
    });
  }

  handleError(code: number) {
    switch (code) {
      case 400:
        this.errorDisplay = 'Incorrect input.';
        break;
      case 403:
        this.errorDisplay = 'Insufficient authorizations.';
        break;
      case 404:
        this.errorDisplay = "Evaluation couldn't be found.";
        break;
      case 0:
        this.errorDisplay = 'Client-side error.';
        break;
      default:
        this.errorDisplay = 'Internal server error.';
        break;
    }
  }

  getDto(): EvaluationDto {
    return {
      title: this.group.get('title')?.value ?? '',
      content: this.group.get('content')?.value ?? '',
      note: this.editNote,
    };
  }

  onClickDelete = (e: Event) => {
    if (this.confirmDeletion) {
      this.loading = true;
      this.http
        .delete(
          `${environment.backendUrl}/api/${this.offerType}/${this.offerId}/evaluations/delete/${this.evaluation.id}`,
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
            this.evaluationListComponent.fetchList();
          },
          error: (err) => {
            this.handleError(err.status);
          },
        });
    } else {
      this.confirmDeletion = true;
    }
  };

  onClickEdit = (e: Event) => {
    this.editMode = !this.editMode;
  };

  onClickSubmitEdit = (e: Event) => {
    this.http
      .put<EvaluationDetailsDto>(
        `${environment.backendUrl}/api/${this.offerType}/${this.offerId}/evaluations/edit/${this.evaluation.id}`,
        this.getDto(),
        {
          headers: {
            Authorization: `Bearer ${this.authService.session?.token}`,
            'Content-Type': 'application/json',
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
          this.editMode = false;
          this.evaluation = resp.body!;
        },
        error: (err) => {
          this.handleError(err.status);
        },
      });
  };
}
