import { AddressDto } from '../auth/address-dto';

export interface FlightDto {
  departureTime: number;
  landingTime: number;
  departureAddress: AddressDto;
  arrivalAddress: AddressDto;
  departureAirport: string;
  arrivalAirport: string;
  price: number;
  seats: number;
}
