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
import { FlightListPageComponent } from '../pages/flights/crud/flight-list-page/flight-list-page.component';
import { FlightAddPageComponent } from '../pages/flights/crud/flight-add-page/flight-add-page.component';
import { HotelListPageComponent } from '../pages/hotels/crud/hotel-list-page/hotel-list-page.component';
import { HotelAddPageComponent } from '../pages/hotels/crud/hotel-add-page/hotel-add-page.component';
import { FlightDetailsPageComponent } from '../pages/flights/crud/flight-details-page/flight-details-page.component';
import { ActivityDetailsPageComponent } from '../pages/activities/crud/activity-details-page/activity-details-page.component';
import { HotelDetailsPageComponent } from '../pages/hotels/crud/hotel-details-page/hotel-details-page.component';
import { ReservationPayPageComponent } from '../pages/reservations/reservation-pay-page/reservation-pay-page.component';
import { ReservationsListPageComponent } from '../pages/reservations/reservations-list-page/reservations-list-page.component';
import { FlightReservationDetailsPageComponent } from '../pages/reservations/flight-reservation-details-page/flight-reservation-details-page.component';
import { HotelReservationDetailsPageComponent } from '../pages/reservations/hotel-reservation-details-page/hotel-reservation-details-page.component';
import { ActivityReservationDetailsPageComponent } from '../pages/reservations/activity-reservation-details-page/activity-reservation-details-page.component';

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
    path: 'flights/manage',
    component: FlightListPageComponent,
    canActivate: [hasRoleCanActivateFnFactory('ROLE_FLIGHT_COMPANY')],
  },
  {
    path: 'flights/form',
    component: FlightAddPageComponent,
    canActivate: [hasRoleCanActivateFnFactory('ROLE_FLIGHT_COMPANY')],
  },
  {
    path: 'flights/details/:id',
    component: FlightDetailsPageComponent,
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
    path: 'hotels/manage',
    component: HotelListPageComponent,
    canActivate: [hasRoleCanActivateFnFactory('ROLE_HOTEL_COMPANY')],
  },
  {
    path: 'hotels/form',
    component: HotelAddPageComponent,
    canActivate: [hasRoleCanActivateFnFactory('ROLE_HOTEL_COMPANY')],
  },
  {
    path: 'hotels/details/:id',
    component: HotelDetailsPageComponent,
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
    path: 'activities/manage',
    component: HotelListPageComponent,
    canActivate: [hasRoleCanActivateFnFactory('ROLE_ACTIVITY_COMPANY')],
  },
  {
    path: 'activities/form',
    component: HotelAddPageComponent,
    canActivate: [hasRoleCanActivateFnFactory('ROLE_ACTIVITY_COMPANY')],
  },
  {
    path: 'activities/details',
    component: ActivityDetailsPageComponent,
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
    path: 'reservations/pay/:offerType/:offerId/:paymentMeanId',
    component: ReservationPayPageComponent,
    canActivate: [isAuthenticatedCanActivateFn],
  },
  {
    path: 'reservations/list',
    component: ReservationsListPageComponent,
    canActivate: [isAuthenticatedCanActivateFn],
  },
  {
    path: 'reservations/details/flights/:id',
    component: FlightReservationDetailsPageComponent,
    canActivate: [isAuthenticatedCanActivateFn],
  },
  {
    path: 'reservations/details/hotels/:id',
    component: HotelReservationDetailsPageComponent,
    canActivate: [isAuthenticatedCanActivateFn],
  },
  {
    path: 'reservations/details/activities/:id',
    component: ActivityReservationDetailsPageComponent,
    canActivate: [isAuthenticatedCanActivateFn],
  },
  {
    path: '**',
    component: NotFoundPageComponent,
  },
];

export default ROUTES;
