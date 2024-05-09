import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavbarComponent } from './layout/navbar/navbar.component';

import { NgIconsModule } from '@ng-icons/core';
import {
  ionPersonCircleOutline,
  ionAirplaneOutline,
  ionBusinessOutline,
  ionBaseballOutline,
} from '@ng-icons/ionicons';
import { RouterModule } from '@angular/router';
import ROUTES from './routing/ROUTES';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { FlightsPageComponent } from './pages/flights-page/flights-page.component';
import { HotelsPageComponent } from './pages/hotels-page/hotels-page.component';
import { ActivitiesPageComponent } from './pages/activities-page/activities-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    NotFoundPageComponent,
    FlightsPageComponent,
    HotelsPageComponent,
    ActivitiesPageComponent,
  ],
  imports: [
    BrowserModule,
    NgIconsModule.withIcons({
      ionPersonCircleOutline,
      ionAirplaneOutline,
      ionBusinessOutline,
      ionBaseballOutline,
    }),
    RouterModule.forRoot(ROUTES),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
