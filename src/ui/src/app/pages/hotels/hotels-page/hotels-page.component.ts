import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-hotels-page',
  templateUrl: './hotels-page.component.html',
  styleUrls: ['./hotels-page.component.css'],
})
export class HotelsPageComponent {
  group!: FormGroup;
  constructor(builder: FormBuilder, private router: Router) {
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

    this.router.navigate(['/hotels', 'search'], {
      queryParams: {
        query: qry,
        country: country,
      },
    });
  }
}
