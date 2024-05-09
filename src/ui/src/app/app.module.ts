import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NavbarComponent } from './layout/navbar/navbar.component';

import { NgIconsModule } from '@ng-icons/core';
import { bootstrapLuggageFill } from '@ng-icons/bootstrap-icons';
import {
  ionPersonCircleOutline,
  ionAirplaneOutline,
  ionBusinessOutline,
  ionBaseballOutline,
} from '@ng-icons/ionicons';

@NgModule({
  declarations: [AppComponent, NavbarComponent],
  imports: [
    BrowserModule,
    NgbModule,
    NgIconsModule.withIcons({
      bootstrapLuggageFill,
      ionPersonCircleOutline,
      ionAirplaneOutline,
      ionBusinessOutline,
      ionBaseballOutline,
    }),
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
