import { HttpClient } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/auth/auth.service';
import { EvaluationDto } from 'src/app/data/user/evaluation-dto';
import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';

@Component({
  selector: 'app-evaluation-input',
  templateUrl: './evaluation-input.component.html',
  styleUrls: ['./evaluation-input.component.css'],
})
export class EvaluationInputComponent {
  note!: number;
  @Input()
  targetId!: number;
  group!: FormGroup;
  loading: boolean = false;
  errorDisplay: string = '';

  constructor(
    builder: FormBuilder,
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.group = builder.group({
      title: ['', Validators.required],
      content: ['', [Validators.required]],
    });
  }

  getDto(): EvaluationDto {
    return {
      title: this.group.get('title')?.value ?? '',
      content: this.group.get('content')?.value ?? '',
      note: this.note,
    };
  }

  onSubmit = (e: Event) => {
    this.http
      .post(
        `${environment.backendUrl}/api/flight/${this.targetId}/reservations/create`,
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
        next: () => {
          this.errorDisplay = '';
          this.group.reset();
        },
        error: (err) => {
          switch (err.status) {
            case 0:
              this.errorDisplay = 'Client-side error.';
              break;
            case 404:
              this.errorDisplay = 'Id not found.';
              break;
            default:
              this.errorDisplay = 'Internal server error.';
              break;
          }
        },
      });
  };
}
