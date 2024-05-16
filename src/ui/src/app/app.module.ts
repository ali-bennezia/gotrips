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
  ionSearchOutline,
} from '@ng-icons/ionicons';
import { RouterModule } from '@angular/router';
import ROUTES from './routing/ROUTES';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { FlightsPageComponent } from './pages/flights/flights-page/flights-page.component';
import { HotelsPageComponent } from './pages/hotels/hotels-page/hotels-page.component';
import { ActivitiesPageComponent } from './pages/activities/activities-page/activities-page.component';
import { RegisterPageComponent } from './pages/user/register-page/register-page.component';
import { SigninPageComponent } from './pages/user/signin-page/signin-page.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserDetailsPageComponent } from './pages/user/user-details-page/user-details-page.component';
import { ModalComponent } from './utils/modal/modal.component';
import { ModalBtnComponent } from './utils/modal-btn/modal-btn.component';
import { CompanyDetailsMinComponent } from './pages/user/user-details-page/company-details-min/company-details-min.component';
import { CardDetailsComponent } from './pages/user/user-details-page/card-details/card-details.component';
import { CardAddPageComponent } from './pages/user/card/card-add-page/card-add-page.component';
import { FlightsSearchPageComponent } from './pages/flights/flights-search-page/flights-search-page.component';
import { FlightCompanyPageComponent } from './pages/flights/flight-company-page/flight-company-page.component';
import { HotelCompanyPageComponent } from './pages/hotels/hotel-company-page/hotel-company-page.component';
import { ActivityCompanyPageComponent } from './pages/activities/activity-company-page/activity-company-page.component';
import { FlightListPageComponent } from './pages/flights/crud/flight-list-page/flight-list-page.component';
import { FlightAddPageComponent } from './pages/flights/crud/flight-add-page/flight-add-page.component';
import { HotelAddPageComponent } from './pages/hotels/crud/hotel-add-page/hotel-add-page.component';
import { HotelListPageComponent } from './pages/hotels/crud/hotel-list-page/hotel-list-page.component';
import { ActivityListPageComponent } from './pages/activities/crud/activity-list-page/activity-list-page.component';
import { ActivityAddPageComponent } from './pages/activities/crud/activity-add-page/activity-add-page.component';

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
    ModalComponent,
    ModalBtnComponent,
    CompanyDetailsMinComponent,
    CardDetailsComponent,
    CardAddPageComponent,
    FlightsSearchPageComponent,
    FlightCompanyPageComponent,
    HotelCompanyPageComponent,
    ActivityCompanyPageComponent,
    FlightListPageComponent,
    FlightAddPageComponent,
    HotelAddPageComponent,
    HotelListPageComponent,
    ActivityListPageComponent,
    ActivityAddPageComponent,
  ],
  imports: [
    BrowserModule,
    NgIconsModule.withIcons({
      ionPersonCircleOutline,
      ionAirplaneOutline,
      ionBusinessOutline,
      ionBaseballOutline,
      ionSearchOutline,
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
