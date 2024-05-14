import { CompanyDetailsDto } from './company-details-dto';

export interface UserDetailsDto {
  id: number;
  username: string;
  roles: string[];
  joinedAtTime: number;
  flightCompany: CompanyDetailsDto | null;
  hotelCompany: CompanyDetailsDto | null;
  activityCompany: CompanyDetailsDto | null;
}
