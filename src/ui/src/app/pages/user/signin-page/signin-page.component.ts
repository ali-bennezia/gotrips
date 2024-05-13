import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-signin-page',
  templateUrl: './signin-page.component.html',
  styleUrls: ['./signin-page.component.css'],
})
export class SigninPageComponent {
  errorDisplay: string = '';
  successDisplay: string = '';

  group!: FormGroup;

  getLoginDTO() {
    return {
      email: this.group.get('email')?.value ?? '',
      password: this.group.get('password')?.value ?? '',
    };
  }

  constructor(
    private builder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) {
    this.group = builder.group({
      email: [
        '',
        [
          Validators.required,
          Validators.email,
          Validators.minLength(3),
          Validators.maxLength(254),
        ],
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(128),
        ],
      ],
    });
    this.activatedRoute.queryParamMap.subscribe((map) => {
      if ((map.get('registered') ?? 'false') === 'true') {
        this.successDisplay = "You've successfully registered.";
      }
    });
  }

  onSubmit(e: Event) {
    this.authService.login(this.getLoginDTO()).subscribe((res) => {
      if (res.success) {
        this.router.navigate(['/flights']);
      } else {
        switch (res.statusCode) {
          case 403:
            this.errorDisplay = 'Invalid credentials.';
            break;
          default:
            this.errorDisplay = 'Internal server error.';
            break;
        }
      }
    });
  }
}
