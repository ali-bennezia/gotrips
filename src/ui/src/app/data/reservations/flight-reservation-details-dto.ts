import { FlightDetailsDto } from '../flight/flight-details-dto';
import { UserDetailsDto } from '../user/user-details-dto';

export interface FlightReservationDetailsDto {
  id: number;
  user: UserDetailsDto;
  flight: FlightDetailsDto;
  price: number;
  paymentTime: number;
}
