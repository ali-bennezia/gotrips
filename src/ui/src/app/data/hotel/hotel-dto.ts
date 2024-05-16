import { AddressDto } from '../auth/address-dto';

export interface HotelDto {
  name: string;
  description: string;
  rooms: number;
  address: AddressDto;
  pricePerNight: number;
}
