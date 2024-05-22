import { HotelDetailsDto } from '../hotel/hotel-details-dto';
import { UserDetailsDto } from '../user/user-details-dto';

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
