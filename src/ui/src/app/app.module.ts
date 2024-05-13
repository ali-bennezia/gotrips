import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavbarComponent } from './layout/navbar/navbar.component';

import { NgIconsModule } from '@ng-icons/core';
import { HttpClientModule } from '@angular/common/http';
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
import { RegisterPageComponent } from './pages/user/register-page/register-page.component';
import { SigninPageComponent } from './pages/user/signin-page/signin-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserDetailsPageComponent } from './pages/user/user-details-page/user-details-page.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    NotFoundPageComponent,
    FlightsPageComponent,
    HotelsPageComponent,
    ActivitiesPageComponent,
    RegisterPageComponent,
    SigninPageComponent,
    UserDetailsPageComponent,
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
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
