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
  const { token, clientId, loading } = useAuth();
  const [user, setUser] = useState<User | null>(null);
  const [error, setError] = useState("");

  useEffect(() => {
    if (loading) return;

    if (!token || !clientId) {
      setError("Token expirado ou clientId não disponível.");
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

    fetchData();
  }, [token, clientId, loading]);

  if (loading) {
    return <p className="text-[white]">Carregando...</p>;
  }

  return (
    <div>
      {error && <p className="text-[red]">{error}</p>}
      {user && (
        <div>
          <p className="text-[white]">Nome: {user.name}</p>
          <p className="text-[white]">Contato: {user.contact}</p>
          <p className="text-[white]">Endereço: {user.address}</p>
          <p className="text-[white]">E-mail: {user.email}</p>
        </div>
      )}
    </div>
  );
}
