import { Routes } from '@angular/router';
import { NotFoundPageComponent } from '../pages/not-found-page/not-found-page.component';
import { FlightsPageComponent } from '../pages/flights-page/flights-page.component';
import { HotelsPageComponent } from '../pages/hotels-page/hotels-page.component';
import { ActivitiesPageComponent } from '../pages/activities-page/activities-page.component';

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
    path: '**',
    component: NotFoundPageComponent,
  },
];

export default ROUTES;
