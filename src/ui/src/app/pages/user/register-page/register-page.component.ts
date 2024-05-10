import {
  Component,
  ViewChildren,
  QueryList,
  AfterViewInit,
  ElementRef,
  OnInit,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { mustMatch } from 'src/app/forms/validators';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css'],
})
export class RegisterPageComponent implements OnInit {
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
  getCompanyDTO(group: FormGroup) {
    return {
      name: group.get('name')?.value,
      description: group.get('description')?.value,
      address: {
        street: group.get('address')?.get('street')?.value,
        city: group.get('address')?.get('city')?.value,
        zipCode: Number(group.get('address')?.get('zipCode')?.value),
        country: group.get('address')?.get('country')?.value,
      },
    };
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
        agreeTermsConditions: [false, [Validators.requiredTrue]],
        flightCompany: builder.group({
          name: ['', [Validators.required]],
          description: ['', [Validators.required]],
          address: builder.group({
            street: ['', [Validators.required]],
            city: ['', [Validators.required]],
            zipCode: [0, [Validators.required]],
            country: ['', [Validators.required]],
          }),
        }),
        hotelCompany: builder.group({
          name: ['', [Validators.required]],
          description: ['', [Validators.required]],
          address: builder.group({
            street: ['', [Validators.required]],
            city: ['', [Validators.required]],
            zipCode: [0, [Validators.required]],
            country: ['', [Validators.required]],
          }),
        }),
        activityCompany: builder.group({
          name: ['', [Validators.required]],
          description: ['', [Validators.required]],
          address: builder.group({
            street: ['', [Validators.required]],
            city: ['', [Validators.required]],
            zipCode: [0, [Validators.required]],
            country: ['', [Validators.required]],
          }),
        }),
      },
      { validators: [mustMatch('password', 'confirmPassword')] }
    );
  }

  updateCompanyForms() {
    let fl: boolean = this.group?.get('isFlightCompany')?.value ?? false;
    let hl: boolean = this.group?.get('isHotelCompany')?.value ?? false;
    let at: boolean = this.group?.get('isActivityCompany')?.value ?? false;
    this.toggleCompanyForm('flightCompany', fl);
    this.toggleCompanyForm('hotelCompany', hl);
    this.toggleCompanyForm('activityCompany', at);
  }

  toggleCompanyForm(name: string, val: boolean) {
    console.log(val);
    if (val == true) this.group.get(name)?.enable();
    else this.group.get(name)?.disable();
  }

  nextPage() {
    ++this.currentPage;
    if (this.currentPage > this.pages) this.currentPage = this.pages;
  }
  previousPage() {
    --this.currentPage;
    if (this.currentPage < 0) this.currentPage = 0;
  }

  ngOnInit(): void {
    this.updateCompanyForms();
  }
}
