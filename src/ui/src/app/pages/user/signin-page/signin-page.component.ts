import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-signin-page',
  templateUrl: './signin-page.component.html',
  styleUrls: ['./signin-page.component.css'],
})
export class SigninPageComponent {
  errorDisplay: string = '';
  successDisplay: string = '';

  group!: FormGroup;

  constructor(
    private builder: FormBuilder,
    private activatedRoute: ActivatedRoute
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

  onSubmit(e: Event) {}
}
