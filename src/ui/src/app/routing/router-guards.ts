import { inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';

export const isAuthenticatedCanActivateFn: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
):
  | Observable<boolean | UrlTree>
  | Promise<boolean | UrlTree>
  | boolean
  | UrlTree => {
  let authService: AuthService = inject(AuthService);
  return authService.authenticated;
};

export const isAnonymousCanActivateFn: CanActivateFn = (
  route: ActivatedRouteSnapshot,
  state: RouterStateSnapshot
):
  | Observable<boolean | UrlTree>
  | Promise<boolean | UrlTree>
  | boolean
  | UrlTree => {
  let authService: AuthService = inject(AuthService);
  return !authService.authenticated;
};

export const isAuthenticatedOrFurnishesIdQueryParamCanActivateFn: CanActivateFn =
  (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree => {
    let authService: AuthService = inject(AuthService);
    return authService.authenticated || route.queryParamMap.has('id');
  };

export const hasRoleCanActivateFnFactory: (role: string) => CanActivateFn = (
  role: string
) => {
  return (
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree => {
    let authService: AuthService = inject(AuthService);
    return authService.authenticated && authService.hasRole(role);
  };
};
