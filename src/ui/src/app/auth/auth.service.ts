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
import { switchMap, catchError, map, tap, last } from 'rxjs/operators';

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

  get admin() {
    return this.hasRole('ROLE_ADMIN');
  }

  hasRole(role: string) {
    return (this._session?.roles ?? []).includes(role);
  }

  constructor(private http: HttpClient) {
    this.initializeService();
  }

  public fetchLastSavedSession() {
    if (localStorage.getItem('session') != null) {
      let lastSession: AuthSession = JSON.parse(
        localStorage.getItem('session')!
      ) as AuthSession;
      this.http
        .get(`${environment.backendUrl}/api/user/authentify`, {
          observe: 'response',
          headers: {
            Authorization: `Bearer ${lastSession.token}`,
          },
        })
        .subscribe({
          next: () => {
            this._session = lastSession;
          },
          error: () => {
            this.logout();
          },
        });
    }
  }

  public saveCurrentSession() {
    if (this._session != null) {
      localStorage.setItem('session', JSON.stringify(this._session));
    } else {
      localStorage.removeItem('session');
    }
  }

  public initializeService() {
    this.fetchLastSavedSession();
  }

  public terminateService() {
    this.saveCurrentSession();
  }

  public logout() {
    this._session = null;
    localStorage.removeItem('session');
  }

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
          this.logout();
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
            this._session = {
              token: (resp.body! as any).jwtToken,
              username: (resp.body! as any).username,
              id: (resp.body! as any).id,
              email: (resp.body! as any).email,
              roles: (resp.body! as any).roles,
              flightCompany: (resp.body! as any).flightCompany,
              hotelCompany: (resp.body! as any).hotelCompany,
              activityCompany: (resp.body! as any).activityCompany,
            };
            this.saveCurrentSession();
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
