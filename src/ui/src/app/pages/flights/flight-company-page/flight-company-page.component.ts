import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { CompanyDetailsDto } from 'src/app/data/user/company-details-dto';

@Component({
  selector: 'app-flight-company-page',
  templateUrl: './flight-company-page.component.html',
  styleUrls: ['./flight-company-page.component.css'],
})
export class FlightCompanyPageComponent {
  currentTab: number = -1;

  setTab(i: number) {
    let previousTab = i;
    this.currentTab = i;
    if (previousTab >= 0) this.onTabClosed(previousTab);
    if (this.currentTab >= 0) this.onTabOpened(this.currentTab);
  }

  onTabClosed(i: number) {
    switch (i) {
      default:
        break;
    }
  }
  onTabOpened(i: number) {
    switch (i) {
      default:
        break;
    }
  }

  company!: CompanyDetailsDto;

  loading: boolean = false;
  companyId!: number;
  errorDisplay: string = '';

  constructor(
    private http: HttpClient,
    private activatedRoute: ActivatedRoute,
    public authService: AuthService,
    private router: Router
  ) {
    this.company = this.authService.session?.flightCompany!;
    this.activatedRoute.queryParamMap.subscribe((params) => {
      let loadTab = Number(params.get('tab') ?? '-1');

      if (loadTab >= 0) this.setTab(loadTab);
      else this.setTab(0);
    });
  }

  ngOnInit(): void {}

  ngOnDestroy(): void {}
}
