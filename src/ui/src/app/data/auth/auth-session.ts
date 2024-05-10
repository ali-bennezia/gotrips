export interface AuthSession {
  token: string;
  username: string;
  email: string;
  roles: string[];
}
