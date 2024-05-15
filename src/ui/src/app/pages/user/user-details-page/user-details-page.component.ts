import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

import { UserDetailsDto } from 'src/app/data/user/user-details-dto';
import {
  UserUiDetailsDto,
  getUserUiDetailsDto,
} from 'src/app/data/user/user-ui-details-dto';

import { environment } from 'src/environments/environment';

import { Observable, Subscription, of } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';
import { CardDetailsDto } from 'src/app/data/user/card-details-dto';

@Component({
  selector: 'app-user-details-page',
  templateUrl: './user-details-page.component.html',
  styleUrls: ['./user-details-page.component.css'],
})
export class UserDetailsPageComponent implements OnInit {
  currentTab: number = -1;

  setTab(i: number) {
    let previousTab = i;
    this.currentTab = i;
    if (previousTab >= 0) this.onTabClosed(previousTab);
    if (this.currentTab >= 0) this.onTabOpened(this.currentTab);
  }

  isOwnProfile(): boolean {
    let strUserId = String(this.userId);
    let strSessId = this.authService.session?.id ?? '';
    return strUserId == strSessId && strUserId != '';
  }

  isOwnProfileOrAdmin(): boolean {
    return this.isOwnProfile() || this.authService.admin;
  }

  cardDetails: CardDetailsDto[] = [];
  cardDetailsSubscription: Subscription | null = null;
  loadingCards: boolean = false;

  removeCardDetails(id: number) {
    this.cardDetails = this.cardDetails.filter((c) => c.id != id);
  }

  onTabClosed(i: number) {
    switch (i) {
      case 2:
        if (this.cardDetailsSubscription) {
          this.cardDetailsSubscription?.unsubscribe();
          this.cardDetailsSubscription = null;
        }
        break;
      default:
        break;
    }
  }
  onTabOpened(i: number) {
    switch (i) {
      case 2:
        this.loadingCards = true;
        this.cardDetailsSubscription = this.http
          .get<CardDetailsDto[]>(
            `${environment.backendUrl}/api/user/${this.userId}/card/getAll`,
            {
              headers: {
                Authorization: `Bearer ${this.authService.session?.token}`,
              },
            }
          )
          .subscribe({
            next: (data) => {
              this.cardDetails = data;
              console.log(this.cardDetails);
              this.loadingCards = false;
            },
            error: (err) => {
              this.loadingCards = false;
              this.errorDisplay = "Couldn' fetch payment data.";
            },
          });
        break;
      default:
        break;
    }
  }

  loading: boolean = false;
  userId!: number;
  user$!: Observable<UserDetailsDto>;
  userDisplay: UserUiDetailsDto | null = null;
  errorDisplay: string = '';

  constructor(
    private http: HttpClient,
    private activatedRoute: ActivatedRoute,
    public authService: AuthService,
    private router: Router
  ) {
    this.activatedRoute.queryParamMap.subscribe((params) => {
      this.userId = Number(params.get('id') ?? '-1');
      let loadTab = Number(params.get('tab') ?? '-1');

      if (this.userId == -1) {
        this.userId = Number(this.authService.session?.id);
      }
      this.user$ = this.http
        .get<UserDetailsDto>(
          `${environment.backendUrl}/api/user/${this.userId}/details`
        )
        .pipe(
          tap((data: UserDetailsDto) => {
            this.userDisplay = getUserUiDetailsDto(data);
          })
        );
      this.user$.subscribe();

      if (loadTab >= 0) this.setTab(loadTab);
      else this.setTab(0);
    });
  }

  ngOnInit(): void {}

  onConfirmDeleteAccount = (e: Event) => {
    this.http
      .delete(`${environment.backendUrl}/api/user/${this.userId}/delete`, {
        headers: { Authorization: `Bearer ${this.authService.session?.token}` },
      })
      .subscribe({
        next: () => {
          this.authService.logout();
          this.router.navigate(['/user', 'signin']);
        },
        error: (err) => {
          console.error(err);
          this.errorDisplay = 'Failed to delete account.';
        },
      });
  };
}
