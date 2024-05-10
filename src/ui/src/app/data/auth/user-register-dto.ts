import { CompanyDto } from './company-dto';

export interface UserRegisterDto {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  password: string;
  flightCompany: CompanyDto | null;
  hotelCompany: CompanyDto | null;
  activityCompany: CompanyDto | null;
}
