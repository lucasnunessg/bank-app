import { createContext } from "react";

interface AuthContextType {
  clientId: number | null;
  name: string | null;
  token: string | null;
  login: (token: string) => void;
  logout: () => void;
  loading: boolean;
}

export const AuthContext = createContext<AuthContextType | null>(null);
