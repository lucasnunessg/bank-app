import { useEffect, useState } from "react";
import api from "../FetchApi";
import { jwtDecode } from "jwt-decode";
import { DecodedToken } from "../decoder/DecodedToken";
import { Eye, EyeOff } from "lucide-react";

interface Transaction {
  valor: number;
  descricao: string;
  nomeDonoDestino: string;
  nomeDonoOrigem: string;
  date: string;
}

function HomeAuthClient() {
  const [saldo, setSaldo] = useState("");
  const [error, setError] = useState("");
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [name, setName] = useState<string | null>(null);
  const [clientId, setClientId] = useState<number | null>(null);
  const [loading, setLoading] = useState(false);
  const [showSaldo, setShowSaldo] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const decodedToken = jwtDecode<DecodedToken>(token);
      setClientId(decodedToken.clientId);
      setName(decodedToken.name);
    }
  }, []);

  useEffect(() => {
    const fetchSaldo = async () => {
      if (clientId !== null) {
        setLoading(true);
        try {
          const token = localStorage.getItem("token");
          const response = await api.get(`/conta-poupanca/${clientId}/saldo`, {
            headers: { Authorization: `Bearer ${token}` },
          });

          if (response.status === 200) {
            setSaldo(response.data.saldo);
            setError("");
          }
        } catch (error) {
          console.error("Erro ao buscar saldo:", error);
          setError("Não foi possível buscar saldo!");
        } finally {
          setLoading(false);
        }
      }
    };

    fetchSaldo();
  }, [clientId]);

  useEffect(() => {
    const fetchTransactions = async () => {
      if (clientId !== null) {
        setLoading(true);
        try {
          const token = localStorage.getItem("token");
          const response = await api.get(
            `/conta-poupanca/${clientId}/transfers/send`,
            {
              headers: { Authorization: `Bearer ${token}` },
            }
          );

          if (response.status === 200) {
            setTransactions(response.data);
            setError("");
          }
        } catch (error) {
          console.error("Erro ao buscar transações:", error);
          setError("Erro ao buscar transações");
        } finally {
          setLoading(false);
        }
      }
    };

    fetchTransactions();

  }, [clientId]);

  const handleShowSaldo = () => {
    setShowSaldo((prev) => !prev);
  };

  return (
    <div>
      <p className="text-[28px] text-[white] font-urbanist">
        Seja bem-vindo! {name}
      </p>

      {error && (
        <div className="text-red-500 text-sm text-center mt-4">{error}</div>
      )}

      <div className="flex flex-col items-end">
        <a
          onClick={handleShowSaldo}
          className="bg-blue-500 text-[white] p-2 rounded cursor-pointer"
        >
          {" "}
          {showSaldo ? "Esconder saldo" : "Mostrar saldo"}<br></br>
          {showSaldo ? <EyeOff className="ml-2" /> : <Eye className="ml-2" />}
        </a>

        {loading ? (
          <h1 className="text-[white]">Carregando...</h1>
        ) : (
          showSaldo && (
            <h1 className="text-[white] font-urbanist">
              Saldo atual R$: {saldo}
            </h1>
          )
        )}
      </div>

      <div className="absolute  max-w-[450px] max-h-[650px]  p-[30px] overflow-y-auto custom-scrollbar">
        <h1 className="text-[white] font-[urbanist]">
          Histórico de transações:
        </h1>
        <ul>
          {transactions.map(
            (
              { nomeDonoDestino, nomeDonoOrigem, valor, descricao, date },
              index
            ) => (
              <li key={index} className="text-[white] font-[urbanist] mb-4">
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
                <p>
                  <strong>Data:</strong> {date}
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
