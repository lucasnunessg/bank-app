import { useEffect, useState } from "react";
import api from "./FetchApi";

function Transf() {
  const [valor, setValor] = useState<number>(); // Corrigido para "number"
  const [clientId, setClientId] = useState<string | number | null>(null);
  const [contaDestinoId, setContaDestinoId] = useState<string | number | null>(null);
  const [loading, setLoading] = useState<boolean>(false);

  useEffect(() => {
    const storedClientId = localStorage.getItem("clientId");
    if (storedClientId !== null) {
      try {
        setClientId(JSON.parse(storedClientId));
      } catch (error) {
        console.error("Erro ao parsear clientId:", error);
      }
    }
  }, []);

  useEffect(() => {
    async function fetchContaDestino() {
      if (!clientId) return;
      setLoading(true);

      try {
        const response = await api.get(`/clients-bank/${clientId}`);
        if (response.status === 200) {
          setContaDestinoId(response.data.id);
        }
      } catch (e) {
        console.error("Erro ao buscar conta destino:", e);
      } finally {
        setLoading(false);
      }
    }
    fetchContaDestino();
  }, [clientId]);

  return (
    <div>
      <h1>Oi</h1>
    </div>
  );
}

export default Transf;
