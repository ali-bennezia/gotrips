import {
  HttpClient,
  HttpErrorResponse,
  HttpResponse,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthSession } from '../data/auth/auth-session';
import { UserLoginDto } from '../data/auth/user-login-dto';
import { UserRegisterDto } from '../data/auth/user-register-dto';

import { Observable, of } from 'rxjs';
import { switchMap, catchError, map, tap } from 'rxjs/operators';

import { environment } from 'src/environments/environment';
import { AuthOperationResult } from '../data/auth/auth-operation-result';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private _session: AuthSession | null = null;

  get session() {
    return this._session;
  }

  get authenticated() {
    return this._session != null;
  }

  constructor(private http: HttpClient) {}

  public login(dto: UserLoginDto): Observable<AuthOperationResult> {
    return this.http
      .post(`${environment.backendUrl}/api/user/signin`, dto, {
        observe: 'response',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .pipe(
        catchError((resp: HttpErrorResponse) => {
          return of({
            success: false,
            statusCode: resp.status,
            statusText: resp.statusText,
          });
        }),
        switchMap((resp) => {
          if (
            resp instanceof HttpResponse &&
            !(resp instanceof HttpErrorResponse)
          ) {
            return of({
              success: true,
              statusCode: resp.status,
              statusText: resp.statusText,
            });
          } else return of(resp as AuthOperationResult);
        })
      );
  }

  public register(dto: UserRegisterDto): Observable<AuthOperationResult> {
    return this.http
      .post(`${environment.backendUrl}/api/user/register`, dto, {
        observe: 'response',
        responseType: 'text',
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .pipe(
        catchError((resp: HttpErrorResponse) => {
          return of({
            success: false,
            statusCode: resp.status,
            statusText: resp.statusText,
          });
        }),
        switchMap((resp) => {
          if (
            resp instanceof HttpResponse &&
            !(resp instanceof HttpErrorResponse)
          ) {
            return of({
              success: true,
              statusCode: resp.status,
              statusText: resp.statusText,
            });
          } else return of(resp as AuthOperationResult);
        })
      );
  }
}
