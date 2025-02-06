import { useContext } from "react";
import { AuthContext } from "../AuthContextType";

export const useAuth = () => {
  const context = useContext(AuthContext)
  if(!context) {
    throw new Error("erro ao utiilizar auth context");
  }
  return context;
}