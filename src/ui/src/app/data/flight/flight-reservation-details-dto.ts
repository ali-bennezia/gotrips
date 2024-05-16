import { UserDetailsDto } from '../user/user-details-dto';
import { FlightDetailsDto } from './flight-details-dto';

export interface FlightReservationDetailsDto {
  id: number;
  user: UserDetailsDto;
  flight: FlightDetailsDto;
  price: number;
  paymentTime: number;
}
