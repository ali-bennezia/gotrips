import { UserDetailsDto } from '../user/user-details-dto';
import { HotelDetailsDto } from './hotel-details-dto';

export interface HotelReservationDetailsDto {
  id: number;
  user: UserDetailsDto;
  hotel: HotelDetailsDto;
  price: number;
  paymentTime: number;
  beginTime: number;
  endTime: number;
  days: number;
}
