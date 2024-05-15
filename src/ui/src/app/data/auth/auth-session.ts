import { CompanyDetailsDto } from '../user/company-details-dto';

export interface AuthSession {
  token: string;
  username: string;
  id: string;
  email: string;
  roles: string[];
  flightCompany: CompanyDetailsDto | null;
  hotelCompany: CompanyDetailsDto | null;
  activityCompany: CompanyDetailsDto | null;
}
