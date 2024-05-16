import { AddressDto } from '../auth/address-dto';
import { CompanyDetailsDto } from '../user/company-details-dto';

export interface HotelDetailsDto {
  id: number;
  company: CompanyDetailsDto;
  rooms: number;
  name: string;
  description: string;
  address: AddressDto;
  averageEvaluation: number;
  pricePerNight: number;
}
