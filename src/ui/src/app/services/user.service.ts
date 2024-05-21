import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { HttpClient } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { CardDetailsDto } from '../data/user/card-details-dto';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private authService: AuthService, private http: HttpClient) {}

  getUserPaymentMethodsAsync(): Observable<CardDetailsDto[]> {
    return this.authService.authenticated
      ? this.http.get<CardDetailsDto[]>(
          `${environment.backendUrl}/api/user/${this.authService.session?.id}/card/getAll`,
          {
            headers: {
              Authorization: `Bearer ${this.authService.session?.token}`,
            },
          }
        )
      : of([]);
  }
}
