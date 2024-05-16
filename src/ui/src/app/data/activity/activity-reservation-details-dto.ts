import { UserDetailsDto } from '../user/user-details-dto';
import { ActivityDetailsDto } from './activity-details-dto';

export interface ActivityReservationDetailsDto {
  id: number;
  user: UserDetailsDto;
  activity: ActivityDetailsDto;
  price: number;
  paymentTime: number;
  beginTime: number;
  endTime: number;
  days: number;
}
