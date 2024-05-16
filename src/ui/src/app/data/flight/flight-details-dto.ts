import { CompanyDetailsDto } from '../user/company-details-dto';

export interface FlightDetailsDto {
  id: number;
  company: CompanyDetailsDto;
  averageEvaluation: number;
  departureDate: Date;
  landingDate: Date;
  departureAirport: string;
  arrivalAirport: string;
}
