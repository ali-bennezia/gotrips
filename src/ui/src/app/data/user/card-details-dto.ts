import { AddressDto } from '../auth/address-dto';

export interface CardDetailsDto {
  id: number;
  name: string;
  partialCardNumber: string;
  address: AddressDto;
}
