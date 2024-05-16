import { AddressDto } from '../auth/address-dto';
import { CompanyDetailsDto } from '../user/company-details-dto';

export interface ActivityDetailsDto {
  id: number;
  company: CompanyDetailsDto;
  title: string;
  description: string;
  averageEvaluation: number;
  address: AddressDto;
  pricePerDay: number;
  spots: number;
}
