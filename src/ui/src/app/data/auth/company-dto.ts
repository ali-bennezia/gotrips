import { AddressDto } from './address-dto';

export interface CompanyDto {
  name: string;
  description: string;
  address: AddressDto;
}
