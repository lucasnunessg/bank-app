import { useEffect, useState, ReactNode } from "react";
import { jwtDecode } from "jwt-decode";
import { AuthContext } from "../contexts/AuthContextType";

interface DecodedToken {
  clientId: number;
  name: string;
}

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(localStorage.getItem("token"));
  const [clientId, setClientId] = useState<number | null>(null);
  const [name, setName] = useState<string | null>(null);
  const [loading, setLoading] = useState(true); 

  useEffect(() => {
    if (token) {
      try {
        const decoded = jwtDecode<DecodedToken>(token);
        setClientId(decoded.clientId);
        setName(decoded.name);
      } catch (e) {
        console.error(e);
        logout();
      } finally {
        setLoading(false); 
      }
    } else {
      setLoading(false); 
    }
  }, [token]);

  const login = (token: string) => {
    localStorage.setItem("token", token);
    const decodedToken = jwtDecode<DecodedToken>(token);
    setToken(token);
    setClientId(decodedToken.clientId);
    setName(decodedToken.name);
  };

  const logout = () => {
    localStorage.removeItem("token");
    sessionStorage.removeItem("token");
    setToken(null);
    setClientId(null);
    setName(null);
  };

  return (
    <AuthContext.Provider value={{ clientId, name, token, login, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
}