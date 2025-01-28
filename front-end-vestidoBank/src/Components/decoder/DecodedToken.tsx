import { jwtDecode } from "jwt-decode";

export interface DecodedToken {
  clientId: string;
  sub: string;
  name: string;
}

export const decodeToken = (token: string): DecodedToken | null => {
  try {
    const decoded: DecodedToken = jwtDecode(token)
    return decoded;
  }catch(e) {
    console.error(e)
    return null;
  }
}