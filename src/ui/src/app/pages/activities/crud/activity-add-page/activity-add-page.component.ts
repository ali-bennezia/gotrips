import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddressDto } from 'src/app/data/auth/address-dto';
import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ActivityDto } from 'src/app/data/activity/activity-dto';

@Component({
  selector: 'app-activity-add-page',
  templateUrl: './activity-add-page.component.html',
  styleUrls: ['./activity-add-page.component.css'],
})
export class ActivityAddPageComponent {
  errorDisplay: string = '';
  successDisplay: string = '';
  group!: FormGroup;
  loading: boolean = false;
  id: number | null = null;
  constructor(
    builder: FormBuilder,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router,
    activatedRoute: ActivatedRoute
  ) {
    this.group = builder.group({
      title: [null, [Validators.required]],
      description: [null, [Validators.required]],
      address: builder.group({
        street: ['', [Validators.required]],
        city: ['', [Validators.required]],
        zipCode: ['', [Validators.required]],
        country: ['', [Validators.required]],
      }),
      pricePerDay: [null, [Validators.required]],
      spots: [null, [Validators.required]],
    });
    activatedRoute.queryParamMap.subscribe((params) => {
      let n = Number(params.get('id') ?? '-1');
      this.id = n >= 0 ? n : null;
    });
  }

  getAddressDto(addressGroup: FormGroup): AddressDto {
    return {
      street: addressGroup.get('street')?.value,
      city: addressGroup.get('city')?.value,
      zipCode: Number(addressGroup.get('zipCode')?.value),
      country: addressGroup.get('country')?.value,
    };
  }

  getDto(): ActivityDto {
    return {
      title: this.group.get('title')?.value ?? '',
      description: this.group.get('description')?.value ?? '',
      address: this.getAddressDto(this.group.get('address')! as FormGroup),
      pricePerDay: this.group.get('pricePerDay')?.value ?? 0,
      spots: this.group.get('spots')?.value ?? 0,
    };
  }

  onSubmit = (e: Event) => {
    this.loading = true;
    if (this.id == null) {
      this.http
        .post(`${environment.backendUrl}/api/activity/create`, this.getDto(), {
          headers: {
            Authorization: `Bearer ${this.authService.session?.token}`,
            'Content-Type': 'application/json',
          },
          observe: 'response',
        })
        .pipe(
          tap((_) => {
            this.loading = false;
          })
        )
        .subscribe({
          next: (response) => {
            this.router.navigate(['/activities', 'manage']);
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
    } else {
      this.http
        .put(
          `${environment.backendUrl}/api/activity/${this.id}/edit`,
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
          next: (response) => {
            this.router.navigate(['/activities', 'manage']);
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
  };

  onChangePricePerDay(e: Event) {
    let targ = e.target as HTMLInputElement;
    let n = Number(targ.value);
    n = Math.abs(Math.round(n * 100) / 100);
    targ.value = String(n);
  }
  onChangeSpots(e: Event) {
    let targ = e.target as HTMLInputElement;
    let n = Number(targ.value);
    n = Math.abs(Math.round(n));
    targ.value = String(n);
  }
}
