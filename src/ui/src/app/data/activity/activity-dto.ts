import { AddressDto } from '../auth/address-dto';

export interface ActivityDto {
  title: string;
  description: string;
  address: AddressDto;
  pricePerDay: number;
  spots: number;
}
