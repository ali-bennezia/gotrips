import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-activities-page',
  templateUrl: './activities-page.component.html',
  styleUrls: ['./activities-page.component.css'],
})
export class ActivitiesPageComponent {
  group!: FormGroup;
  constructor(
    builder: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.group = builder.group({
      country: [null, [Validators.required]],
      query: [''],
    });
  }

  onSubmit(e: Event) {
    let qry = this.group.get('query')?.value;
    qry = qry == '' || qry == undefined ? undefined : qry;

    let country = this.group.get('country')?.value;
    country = country == '' || country == null ? undefined : country;

    this.router.navigate(['/activities', 'search'], {
      queryParams: {
        query: qry,
        country: country,
      },
    });
  }
}
