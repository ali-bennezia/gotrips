import { Component, Input } from '@angular/core';
import { CompanyUiDetailsDto } from 'src/app/data/user/company-ui-details-dto';

@Component({
  selector: 'app-company-details-min',
  templateUrl: './company-details-min.component.html',
  styleUrls: ['./company-details-min.component.css'],
})
export class CompanyDetailsMinComponent {
  @Input()
  company!: CompanyUiDetailsDto;

  typeString() {
    switch (this.company.type) {
      case 1:
        return 'hotels';
      case 2:
        return 'activities';
      default:
        return 'flights';
    }
  }
}
