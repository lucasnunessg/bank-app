import { useEffect, useState } from "react";
import api from "../FetchApi";
import { jwtDecode } from "jwt-decode";
import { DecodedToken } from "../decoder/DecodedToken";

function HomeAuthClient() {
  const [saldo, setSaldo] = useState("");
  const [error, setError] = useState("");
  const[name, setName] = useState<string | null>(null);
  const [clientId, setClientId] = useState<number | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = jwtDecode<DecodedToken>(token);
      const extractedClientId = decodedToken.clientId;
      const extractName = decodedToken.name
      setClientId(extractedClientId);
      setName(extractName);
    }
  }, []);


  useEffect(() => {
    const fetchSaldo = async () => {
      if (clientId !== null) {
        try {
          const token = localStorage.getItem("token")
          const response = await api.get(`/conta-poupanca/${clientId}/saldo`, {
            headers: {
              Authorization: `Bearer ${token}`, 
            },
          });
          if (response.status === 200) {
            const saldo = response.data.saldo;
            setSaldo(saldo); 
            setError("");
          }
        } catch (error) {
          console.error("Erro ao buscar saldo:", error);
          setError("Não foi possível buscar saldo!");
        }
      }
    };

    fetchSaldo();
  }, [clientId]);

  return (
    <div>
      <p className="text-[28px] text-[white] text-urbanist">Seja bem vindo! {name}</p>



      {error && (
        <div className="text-red-500 text-sm text-center mt-4">{error}</div>
      )}

      <h1 className="text-[white]">
        {saldo ? `Saldo atual R$: ${saldo}` : "Carregando saldo..."}
      </h1>


    </div>
  );
}

export default HomeAuthClient;
