import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AddressDto } from 'src/app/data/auth/address-dto';
import { FlightDto } from 'src/app/data/flight/flight-dto';
import { environment } from 'src/environments/environment';

import { tap } from 'rxjs/operators';
import { AuthService } from 'src/app/auth/auth.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-flight-add-page',
  templateUrl: './flight-add-page.component.html',
  styleUrls: ['./flight-add-page.component.css'],
})
export class FlightAddPageComponent {
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
      departureDate: [null, [Validators.required]],
      landingDate: [null, [Validators.required]],
      departureAddress: builder.group({
        street: ['', [Validators.required]],
        city: ['', [Validators.required]],
        zipCode: ['', [Validators.required]],
        country: ['', [Validators.required]],
      }),
      arrivalAddress: builder.group({
        street: ['', [Validators.required]],
        city: ['', [Validators.required]],
        zipCode: ['', [Validators.required]],
        country: ['', [Validators.required]],
      }),
      departureAirport: ['', Validators.required],
      arrivalAirport: ['', [Validators.required]],
      price: [null, [Validators.required]],
      seats: [null, [Validators.required]],
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

  getDto(): FlightDto {
    return {
      departureTime: new Date(
        this.group.get('departureDate')?.value ?? ''
      ).getTime(),
      landingTime: new Date(
        this.group.get('landingDate')?.value ?? ''
      ).getTime(),
      departureAddress: this.getAddressDto(
        this.group.get('departureAddress')! as FormGroup
      ),
      arrivalAddress: this.getAddressDto(
        this.group.get('arrivalAddress')! as FormGroup
      ),
      departureAirport: this.group.get('departureAirport')?.value ?? '',
      arrivalAirport: this.group.get('arrivalAirport')?.value ?? '',
      price: this.group.get('price')?.value ?? 0,
      seats: this.group.get('seats')?.value ?? 0,
    };
  }

  onSubmit = (e: Event) => {
    this.loading = true;
    if (this.id == null) {
      this.http
        .post(`${environment.backendUrl}/api/flight/create`, this.getDto(), {
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
            this.router.navigate(['/flights', 'manage']);
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
        .put(`${environment.backendUrl}/api/flight/edit`, this.getDto(), {
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
            this.router.navigate(['/flights', 'manage']);
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

  onChangePrice(e: Event) {
    let targ = e.target as HTMLInputElement;
    let n = Number(targ.value);
    n = Math.abs(Math.round(n * 100) / 100);
    targ.value = String(n);
  }
  onChangeSeats(e: Event) {
    let targ = e.target as HTMLInputElement;
    let n = Number(targ.value);
    n = Math.abs(Math.round(n));
    targ.value = String(n);
  }
}
