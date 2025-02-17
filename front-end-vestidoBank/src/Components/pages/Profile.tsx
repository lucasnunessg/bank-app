import { useEffect, useState } from "react";
import { useAuth } from "../contexts/useAuth";
import api from "../FetchApi";

interface User {
  id: number;
  name: string;
  contact: string;
  address: string;
  email: string;
}

export function Profile() {
  const { token, clientId } = useAuth();
  const [user, setUser] = useState<User[]>([]);
  const [error, setError] = useState("");

  console.log("token aquii:   ", token);
  
  console.log(clientId);
  
  useEffect(() => {

    if(!token || !clientId) {
      setError("token expirado");
      return;
    } 
    const fetchData = async () => {
      try {
        const response = await api.get(`/clients-bank/${clientId}`, {
          headers: { Authorization: `Bearer ${token}` },          
        });
        
        if (response.status === 200) {
          setUser(response.data);
        } else {
          setError("Erro ao carregar os dados."); 
        }
      } catch (e) {
        console.error(e);
        setError("Não foi possível trazer as informações.");
      }
    };

    if (token) {
      fetchData();
    }
  }, [token, clientId]);

  return (
    <div>
      {error && <p className="text-[red]">{error}</p>}
      {user.length > 0 ? (
        <ul>
          {user.map((user) => (
            <li key={user.id}>
              <h3 className="text-[white]">{user.name}</h3>
              <p className="text-[white]">{user.contact}</p>
              <p className="text-[white]">{user.address}</p>
              <p className="text-[white]">{user.email}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-[white]">Carregando usuários...</p>
      )}
    </div>
  );
}
