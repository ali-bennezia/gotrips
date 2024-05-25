import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { CompanyDetailsDto } from 'src/app/data/user/company-details-dto';

@Component({
  selector: 'app-activity-company-page',
  templateUrl: './activity-company-page.component.html',
  styleUrls: ['./activity-company-page.component.css'],
})
export class ActivityCompanyPageComponent {
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
    this.company = this.authService.session?.activityCompany!;
    this.activatedRoute.queryParamMap.subscribe((params) => {
      let loadTab = Number(params.get('tab') ?? '-1');

      if (loadTab >= 0) this.setTab(loadTab);
      else this.setTab(0);
    });
  }
}
