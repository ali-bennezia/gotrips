import {
  Component,
  ViewChildren,
  QueryList,
  AfterViewInit,
  ElementRef,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { mustMatch } from 'src/app/forms/validators';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css'],
})
export class RegisterPageComponent {
  currentPage: number = 1;
  get pageNames() {
    return [
      ...(this.group.get('isFlightCompany')?.value === true ? ['flight'] : []),
      ...(this.group.get('isHotelCompany')?.value === true ? ['hotel'] : []),
      ...(this.group.get('isActivityCompany')?.value === true
        ? ['activity']
        : []),
    ];
  }
  get pages() {
    return (
      1 +
      (this.group.get('isFlightCompany')?.value === true ? 1 : 0) +
      (this.group.get('isHotelCompany')?.value === true ? 1 : 0) +
      (this.group.get('isActivityCompany')?.value === true ? 1 : 0)
    );
  }
  getPageIndex(name: string) {
    let res = this.pageNames.indexOf(name);
    return res == -1 ? res : res + 2;
  }
  group!: FormGroup;
  constructor(private builder: FormBuilder) {
    this.group = this.builder.group(
      {
        username: [
          '',
          [
            Validators.required,
            Validators.minLength(6),
            Validators.maxLength(60),
          ],
        ],
        email: [
          '',
          [
            Validators.required,
            Validators.email,
            Validators.minLength(3),
            Validators.maxLength(254),
          ],
        ],
        firstName: [
          '',
          [
            Validators.required,
            Validators.minLength(2),
            Validators.maxLength(50),
          ],
        ],
        lastName: [
          '',
          [
            Validators.required,
            Validators.minLength(2),
            Validators.maxLength(50),
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
        confirmPassword: [
          '',
          [
            Validators.required,
            Validators.minLength(8),
            Validators.maxLength(128),
          ],
        ],
        isFlightCompany: [false],
        isHotelCompany: [false],
        isActivityCompany: [false],
        agreeTermsConditions: [false],
        flightCompany: builder.group({}),
      },
      { validators: [mustMatch('password', 'confirmPassword')] }
    );
  }

  nextPage() {
    ++this.currentPage;
    if (this.currentPage > this.pages) this.currentPage = this.pages;
  }
  previousPage() {
    --this.currentPage;
    if (this.currentPage < 0) this.currentPage = 0;
  }
}
