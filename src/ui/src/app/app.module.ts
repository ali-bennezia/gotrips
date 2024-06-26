import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NavbarComponent } from './layout/navbar/navbar.component';

import { NgIconsModule } from '@ng-icons/core';
import { HttpClientModule } from '@angular/common/http';
import {
  ionPersonCircleOutline,
  ionAirplaneOutline,
  ionAirplane,
  ionBusinessOutline,
  ionBaseballOutline,
  ionSearchOutline,
  ionStar,
  ionCaretBackOutline,
  ionCaretForwardOutline,
  ionCalendarOutline,
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
import { ActivityDetailsPageComponent } from './pages/activities/crud/activity-details-page/activity-details-page.component';
import { HotelDetailsPageComponent } from './pages/hotels/crud/hotel-details-page/hotel-details-page.component';
import { FlightDetailsPageComponent } from './pages/flights/crud/flight-details-page/flight-details-page.component';
import { StarsEvaluationComponent } from './utils/evaluations/stars-evaluation/stars-evaluation.component';
import { StarsEvaluationInputComponent } from './utils/evaluations/stars-evaluation-input/stars-evaluation-input.component';
import { EvaluationInputComponent } from './utils/evaluations/evaluation-input/evaluation-input.component';
import { EvaluationListComponent } from './utils/evaluations/evaluation-list/evaluation-list.component';
import { EvaluationDisplayComponent } from './utils/evaluations/evaluation-display/evaluation-display.component';
import { PaginationControlsComponent } from './utils/pagination-controls/pagination-controls.component';
import { InteractiveListGroupComponent } from './utils/interactive-list-group/interactive-list-group.component';
import { ReservationPayPageComponent } from './pages/reservations/reservation-pay-page/reservation-pay-page.component';
import { ReservationsListPageComponent } from './pages/reservations/reservations-list-page/reservations-list-page.component';
import { FlightReservationDetailsMinComponent } from './pages/reservations/reservations-list-page/items/flight-reservation-details-min/flight-reservation-details-min.component';
import { HotelReservationDetailsMinComponent } from './pages/reservations/reservations-list-page/items/hotel-reservation-details-min/hotel-reservation-details-min.component';
import { ActivityReservationDetailsMinComponent } from './pages/reservations/reservations-list-page/items/activity-reservation-details-min/activity-reservation-details-min.component';
import { FlightReservationDetailsPageComponent } from './pages/reservations/flight-reservation-details-page/flight-reservation-details-page.component';
import { HotelReservationDetailsPageComponent } from './pages/reservations/hotel-reservation-details-page/hotel-reservation-details-page.component';
import { ActivityReservationDetailsPageComponent } from './pages/reservations/activity-reservation-details-page/activity-reservation-details-page.component';
import { CalendarComponent } from './utils/calendar/calendar.component';
import { FlightsSearchResultComponent } from './pages/flights/flights-search-page/flights-search-result/flights-search-result.component';
import { ActivitiesSearchPageComponent } from './pages/activities/activities-search-page/activities-search-page.component';
import { ActivitiesSearchResultComponent } from './pages/activities/activities-search-page/activities-search-result/activities-search-result.component';
import { PeriodCalendarComponent } from './utils/period-calendar/period-calendar.component';
import { HotelsSearchPageComponent } from './pages/hotels/hotels-search-page/hotels-search-page.component';
import { HotelsSearchResultComponent } from './pages/hotels/hotels-search-page/hotels-search-result/hotels-search-result.component';

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
    ActivityDetailsPageComponent,
    HotelDetailsPageComponent,
    FlightDetailsPageComponent,
    StarsEvaluationComponent,
    StarsEvaluationInputComponent,
    EvaluationInputComponent,
    EvaluationListComponent,
    EvaluationDisplayComponent,
    PaginationControlsComponent,
    InteractiveListGroupComponent,
    ReservationPayPageComponent,
    ReservationsListPageComponent,
    FlightReservationDetailsMinComponent,
    HotelReservationDetailsMinComponent,
    ActivityReservationDetailsMinComponent,
    FlightReservationDetailsPageComponent,
    HotelReservationDetailsPageComponent,
    ActivityReservationDetailsPageComponent,
    CalendarComponent,
    FlightsSearchResultComponent,
    ActivitiesSearchPageComponent,
    ActivitiesSearchResultComponent,
    PeriodCalendarComponent,
    HotelsSearchPageComponent,
    HotelsSearchResultComponent,
  ],
  imports: [
    BrowserModule,
    NgIconsModule.withIcons({
      ionStar,
      ionPersonCircleOutline,
      ionAirplaneOutline,
      ionAirplane,
      ionBusinessOutline,
      ionBaseballOutline,
      ionSearchOutline,
      ionCaretBackOutline,
      ionCaretForwardOutline,
      ionCalendarOutline,
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
