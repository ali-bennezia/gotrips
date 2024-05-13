import { Routes } from '@angular/router';
import { NotFoundPageComponent } from '../pages/not-found-page/not-found-page.component';
import { FlightsPageComponent } from '../pages/flights-page/flights-page.component';
import { HotelsPageComponent } from '../pages/hotels-page/hotels-page.component';
import { ActivitiesPageComponent } from '../pages/activities-page/activities-page.component';
import { RegisterPageComponent } from '../pages/user/register-page/register-page.component';
import { SigninPageComponent } from '../pages/user/signin-page/signin-page.component';
import {
  isAnonymousCanActivateFn,
  isAuthenticatedOrFurnishesIdQueryParam,
} from './router-guards';
import { UserDetailsPageComponent } from '../pages/user/user-details-page/user-details-page.component';

const ROUTES: Routes = [
  {
    path: 'flights',
    component: FlightsPageComponent,
  },
  {
    path: 'hotels',
    component: HotelsPageComponent,
  },
  {
    path: 'activities',
    component: ActivitiesPageComponent,
  },
  {
    path: 'user/register',
    component: RegisterPageComponent,
    canActivate: [isAnonymousCanActivateFn],
  },
  {
    path: 'user/signin',
    component: SigninPageComponent,
    canActivate: [isAnonymousCanActivateFn],
  },
  {
    path: 'user/details',
    component: UserDetailsPageComponent,
    canActivate: [isAuthenticatedOrFurnishesIdQueryParam],
  },
  {
    path: '**',
    component: NotFoundPageComponent,
  },
];

export default ROUTES;