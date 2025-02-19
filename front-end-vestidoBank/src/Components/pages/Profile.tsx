import { useEffect, useState } from "react";
import { useAuth } from "../contexts/useAuth";
import api from "../FetchApi";
import { ExchangeInformation } from "./ExchangeInformation";

interface User {
  id: number;
  name: string;
  contact: string;
  address: string;
  email: string;
}

export function Profile() {
  const { token, clientId, loading } = useAuth();
  const [user, setUser] = useState<User | null>(null); // uso assm qdo é um so usuaroi
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState(false);

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
        console.log("Resposta da API:", response.data); 

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
      {!showForm ? (
        <button onClick={() => setShowForm(true)}>Deseja alterar algum dado?</button>
      ) : (
        <ExchangeInformation />
      )}

      {error && <p className="text-[red]">{error}</p>}

      {!showForm && user ? (
        <div>
          <p className="text-[white]">Nome: {user.name}</p>
          <p className="text-[white]">Contato: {user.contact}</p>
          <p className="text-[white]">Endereço: {user.address}</p>
          <p className="text-[white]">E-mail: {user.email}</p>
        </div>
      ) : !showForm && (
        <p className="text-[white]">Nenhum usuário encontrado.</p>
      )}
    </div>
  );
}