import { HttpClient } from '@angular/common/http';
import { Component, HostListener, Input } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { CardDetailsDto } from 'src/app/data/user/card-details-dto';

import { environment } from 'src/environments/environment';
import { UserDetailsPageComponent } from '../user-details-page.component';

@Component({
  selector: 'app-card-details',
  templateUrl: './card-details.component.html',
  styleUrls: ['./card-details.component.css'],
})
export class CardDetailsComponent {
  @Input()
  card!: CardDetailsDto;
  confirmDeletion: boolean = false;

  errorDisplay: string = '';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private userDetailsPage: UserDetailsPageComponent
  ) {}

  @HostListener('mouseleave')
  onMouseLeave() {
    this.confirmDeletion = false;
  }

  onClickDelete(e: Event) {
    if (!this.confirmDeletion) {
      this.confirmDeletion = true;
    } else {
      this.http
        .delete(
          `${environment.backendUrl}/api/user/${this.authService.session?.id}/card/${this.card.id}/delete`,
          {
            headers: {
              Authorization: `Bearer ${this.authService.session?.token}`,
            },
          }
        )
        .subscribe({
          next: (data) => {
            this.userDetailsPage.removeCardDetails(this.card.id);
          },
          error: (err) => {
            this.errorDisplay = "Couldn't delete payment data.";
          },
        });
    }
  }
}
