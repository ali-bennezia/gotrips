import { Routes } from '@angular/router';
import { NotFoundPageComponent } from '../pages/not-found-page/not-found-page.component';
import { FlightsPageComponent } from '../pages/flights/flights-page/flights-page.component';
import { HotelsPageComponent } from '../pages/hotels/hotels-page/hotels-page.component';
import { ActivitiesPageComponent } from '../pages/activities/activities-page/activities-page.component';
import { RegisterPageComponent } from '../pages/user/register-page/register-page.component';
import { SigninPageComponent } from '../pages/user/signin-page/signin-page.component';
import {
  hasRoleCanActivateFnFactory,
  isAnonymousCanActivateFn,
  isAuthenticatedCanActivateFn,
  isAuthenticatedOrFurnishesIdQueryParamCanActivateFn,
} from './router-guards';
import { UserDetailsPageComponent } from '../pages/user/user-details-page/user-details-page.component';
import { CardAddPageComponent } from '../pages/user/card/card-add-page/card-add-page.component';
import { FlightCompanyPageComponent } from '../pages/flights/flight-company-page/flight-company-page.component';
import { HotelCompanyPageComponent } from '../pages/hotels/hotel-company-page/hotel-company-page.component';
import { ActivityCompanyPageComponent } from '../pages/activities/activity-company-page/activity-company-page.component';

const ROUTES: Routes = [
  {
    path: 'flights',
    component: FlightsPageComponent,
    pathMatch: 'full',
  },
  {
    path: 'flights/company/details',
    component: FlightCompanyPageComponent,
    canActivate: [hasRoleCanActivateFnFactory('ROLE_FLIGHT_COMPANY')],
  },
  {
    path: 'hotels',
    component: HotelsPageComponent,
    pathMatch: 'full',
  },
  {
    path: 'hotels/company/details',
    component: HotelCompanyPageComponent,
    canActivate: [hasRoleCanActivateFnFactory('ROLE_HOTEL_COMPANY')],
  },
  {
    path: 'activities',
    component: ActivitiesPageComponent,
    pathMatch: 'full',
  },
  {
    path: 'activity/company/details',
    component: ActivityCompanyPageComponent,
    canActivate: [hasRoleCanActivateFnFactory('ROLE_ACTIVITY_COMPANY')],
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
    canActivate: [isAuthenticatedOrFurnishesIdQueryParamCanActivateFn],
  },
  {
    path: 'user/card/form',
    component: CardAddPageComponent,
    canActivate: [isAuthenticatedCanActivateFn],
  },
  {
    path: '**',
    component: NotFoundPageComponent,
  },
];

export default ROUTES;
