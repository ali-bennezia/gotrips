import { UserDetailsDto } from './user-details-dto';

export interface EvaluationDetailsDto {
  id: number;
  title: string;
  content: string;
  note: number;
  author: UserDetailsDto;
}
