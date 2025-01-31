import { useEffect, useState } from "react";
import api from "../FetchApi";
import { jwtDecode } from "jwt-decode";
import { DecodedToken } from "../decoder/DecodedToken";

interface Transaction {
  valor: number;
  descricao: string;
  nomeDonoDestino: string;
  nomeDonoOrigem: string;
}

function HomeAuthClient() {
  const [saldo, setSaldo] = useState("");
  const [error, setError] = useState("");
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [name, setName] = useState<string | null>(null);
  const [clientId, setClientId] = useState<number | null>(null);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = jwtDecode<DecodedToken>(token);
      const extractedClientId = decodedToken.clientId;
      const extractName = decodedToken.name;
      setClientId(extractedClientId);
      setName(extractName);
    }
  }, []);

  useEffect(() => {
    const fetchSaldo = async () => {
      if (clientId !== null) {
        try {
          const token = localStorage.getItem("token");
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

  useEffect(() => {
    const fetchTransactions = async () => {
      if (clientId !== null) {
        try {
          const token = localStorage.getItem("token");
          const response = api.get(
            `/conta-poupanca/{clientId}/transfers/send`,
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
          if ((await response).status === 200) {
            setTransactions((await response).data);
            setError("");
          }
        } catch (e) {
          console.error(e);
          setError("Erro ao buscar transações");
        }
      }
    };

    fetchTransactions();
  }, [clientId]);

  return (
    <div>
      <p className="text-[28px] text-[white] text-urbanist">
        Seja bem vindo! {name}
      </p>

      {error && (
        <div className="text-red-500 text-sm text-center mt-4">{error}</div>
      )}

      <h1 className="text-[white]">
        {saldo ? `Saldo atual R$: ${saldo}` : "Carregando saldo..."}
      </h1>

      <div className="absolute ml-[350px]  max-w-[450px] max-h-[650px]  rounded-[8px] p-[30px] overflow-y-auto custom-scrollbar">
        <h1 className="text-[white]">Histórico de transações:</h1>
        <ul>
          {transactions.map(
            ({ nomeDonoDestino, nomeDonoOrigem, valor, descricao }, index) => (
              <li key={index} className="text-[white] mb-4">
                <p>
                  <strong>Origem:</strong> {nomeDonoOrigem}
                </p>
                <p>
                  <strong>Destino:</strong> {nomeDonoDestino}
                </p>
                <p>
                  <strong>Valor:</strong> R$ {valor.toFixed(2)}
                </p>
                <p>
                  <strong>Descrição:</strong> {descricao}
                </p>
              </li>
            )
          )}
        </ul>
      </div>
    </div>
  );
}

export default HomeAuthClient;
