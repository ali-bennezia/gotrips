import { CompanyDetailsDto } from './company-details-dto';

export interface CompanyUiDetailsDto {
  id: number;
  userId: number;
  name: string;
  description: string;
  type: number;
}

export const getCompanyUiDetailsDto = (
  data: CompanyDetailsDto,
  type: number
): CompanyUiDetailsDto => {
  return {
    id: data.id,
    userId: data.userId,
    name: data.name,
    description: data.description,
    type: type,
  };
};
