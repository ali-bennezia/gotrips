import { UserDetailsDto } from './user-details-dto';

export interface EvaluationDetailsDto {
  title: string;
  content: string;
  note: number;
  author: UserDetailsDto;
}
