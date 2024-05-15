import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-card-add-page',
  templateUrl: './card-add-page.component.html',
  styleUrls: ['./card-add-page.component.css'],
})
export class CardAddPageComponent implements OnInit, OnDestroy {
  private paramsSubscription: Subscription | null = null;
  errorDisplay: string = '';
  successDisplay: string = '';
  group!: FormGroup;
  loading: boolean = false;
  cardId: number | null = null;

  handleError(code: number) {
    switch (code) {
      default:
        this.errorDisplay = 'Internal server error.';
        break;
    }
  }

  onSubmit(e: Event) {
    if (this.cardId == null) {
      this.http
        .post(
          `${environment.backendUrl}/api/user/${this.authService.session?.id}/card/add`,
          this.getDto(),
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${this.authService.session?.token}`,
            },
            observe: 'response',
          }
        )
        .subscribe({
          next: () => {
            this.router.navigate(['/user', 'details'], {
              queryParams: { tab: 2 },
            });
          },
          error: (err) => {
            console.log(err);
            this.handleError(err.status);
          },
        });
    } else {
      this.http
        .put(
          `${environment.backendUrl}/api/user/${this.authService.session?.id}/card/${this.cardId}/edit`,
          this.getDto(),
          {
            headers: {
              'Content-Type': 'application/json',
              Authorization: `Bearer ${this.authService.session?.token}`,
            },
            observe: 'response',
          }
        )
        .subscribe({
          next: () => {
            this.router.navigate(['/user', 'details'], {
              queryParams: { tab: 2 },
            });
          },
          error: (err) => {
            console.log(err);
            this.handleError(err.status);
          },
        });
    }
  }

  constructor(
    builder: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.group = builder.group({
      cardHolder: ['', [Validators.required]],
      cardNumber: ['', [Validators.required]],
      cardCode: [
        '',
        [Validators.required, Validators.minLength(3), Validators.maxLength(3)],
      ],
      cardExpiration: ['', [Validators.required]],
      address: builder.group({
        street: ['', [Validators.required]],
        city: ['', [Validators.required]],
        zipCode: ['', [Validators.required]],
        country: ['', [Validators.required]],
      }),
    });
  }

  getDto() {
    return {
      creditCardName: this.group.get('cardHolder')?.value,
      creditCardNumber: this.group.get('cardNumber')?.value,
      creditCardCode: this.group.get('cardCode')?.value,
      expirationTime: new Date(
        this.group.get('cardExpiration')?.value
      ).getTime(),
      address: {
        street: this.group.get('address')?.get('street')?.value,
        city: this.group.get('address')?.get('city')?.value,
        zipCode: this.group.get('address')?.get('zipCode')?.value,
        country: this.group.get('address')?.get('country')?.value,
      },
    };
  }

  ngOnInit(): void {
    this.paramsSubscription = this.activatedRoute.queryParamMap.subscribe(
      (params) => {
        if (params.has('id')) {
          this.cardId = Number(params.get('id'));
        }
      }
    );
  }

  ngOnDestroy(): void {
    if (this.paramsSubscription != null) this.paramsSubscription.unsubscribe();
  }
}
