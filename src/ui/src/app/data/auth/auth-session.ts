export interface AuthSession {
  token: string;
  username: string;
  id: string;
  email: string;
  roles: string[];
}
