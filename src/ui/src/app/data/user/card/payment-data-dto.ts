import { AddressDto } from '../../auth/address-dto';

export interface PaymentDataDto {
  creditCardName: string;
  creditCardNumber: string;
  creditCardCode: string;
  expirationTime: number;
  address: AddressDto;
}
