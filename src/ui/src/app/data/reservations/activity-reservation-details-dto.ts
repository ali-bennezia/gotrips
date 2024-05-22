import { ActivityDetailsDto } from '../activity/activity-details-dto';
import { UserDetailsDto } from '../user/user-details-dto';

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
