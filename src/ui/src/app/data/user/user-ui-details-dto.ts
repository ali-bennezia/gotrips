import { CompanyDetailsDto } from './company-details-dto';
import {
  CompanyUiDetailsDto,
  getCompanyUiDetailsDto,
} from './company-ui-details-dto';
import { UserDetailsDto } from './user-details-dto';

export interface UserUiDetailsDto {
  id: number;
  username: string;
  roles: string;
  joinedAtDate: Date;
  companies: CompanyUiDetailsDto[];
}

export const getUserUiDetailsDto = (data: UserDetailsDto): UserUiDetailsDto => {
  return {
    id: data.id,
    username: data.username,
    roles: data.roles
      .map((r) =>
        r.replaceAll('ROLE_', '').toLocaleLowerCase().replace('_', ' ')
      )
      .join(', '),
    joinedAtDate: new Date(data.joinedAtTime),
    companies: [
      ...(data.flightCompany
        ? [getCompanyUiDetailsDto(data.flightCompany, 0)]
        : []),
      ...(data.hotelCompany
        ? [getCompanyUiDetailsDto(data.hotelCompany, 1)]
        : []),
      ...(data.activityCompany
        ? [getCompanyUiDetailsDto(data.activityCompany, 2)]
        : []),
    ],
  };
};
