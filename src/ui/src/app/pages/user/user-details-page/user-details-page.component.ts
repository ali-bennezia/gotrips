import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

import { UserDetailsDto } from 'src/app/data/user/user-details-dto';
import { UserUiDetailsDto } from 'src/app/data/user/user-ui-details-dto';

import { environment } from 'src/environments/environment';

import { Observable, of } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';

@Component({
  selector: 'app-user-details-page',
  templateUrl: './user-details-page.component.html',
  styleUrls: ['./user-details-page.component.css'],
})
export class UserDetailsPageComponent implements OnInit {
  currentTab: number = 0;
  setTab(i: number) {
    this.currentTab = i;
  }
  loading: boolean = false;
  userId!: number;
  user$!: Observable<UserDetailsDto>;
  userDisplay: UserUiDetailsDto | null = null;
  errorDisplay: string = '';

  constructor(
    private http: HttpClient,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) {
    this.activatedRoute.queryParamMap.subscribe((params) => {
      this.userId = Number(params.get('id') ?? '-1');
      if (this.userId == -1) {
        this.userId = Number(this.authService.session?.id);
      }
      this.user$ = this.http
        .get<UserDetailsDto>(
          `${environment.backendUrl}/api/user/${this.userId}/details`
        )
        .pipe(
          tap((data: UserDetailsDto) => {
            this.userDisplay = {
              id: data.id,
              username: data.username,
              roles: data.roles
                .map((r) =>
                  r
                    .replaceAll('ROLE_', '')
                    .toLocaleLowerCase()
                    .replace('_', ' ')
                )
                .join(', '),
              joinedAtDate: new Date(data.joinedAtTime),
            };
          })
        );
      this.user$.subscribe();
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
