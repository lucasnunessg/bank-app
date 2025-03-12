import { useCallback, useEffect, useState } from "react";
import { useAuth } from "../contexts/useAuth";
import api from "../FetchApi";

export function PageFatura() {
  interface Transaction {
    id: number;
    valor: number;
    descricao: string;
    nomeDonoDestino: string;
    faturaAtual: number;
    dataHora: string;
  }

  const { clientId, token } = useAuth();
  const [valor, setValor] = useState("");
  const [descricao, setDescricao] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [error, setError] = useState("");
  const [transactions, setTransactions] = useState<Transaction[]>([]);

  const contaPoupancaId = clientId;
  const cartaoDeCreditoId = clientId;

  const handleValor = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValor(event.target.value);
  };

  const formatarData = (dataHora: string) => {
    const [dia, mes, ano] = dataHora.split("/");
  
    const data = new Date(Number(ano), Number(mes) - 1, Number(dia));
  
    if (isNaN(data.getTime())) {
      return "Data inválida";
    }
  
    return data.toLocaleDateString("pt-BR", {
      day: "2-digit",
      month: "2-digit",
      year: "numeric",
    });
  };

  const validarValor = (valor: string): number | null => {
    const valorNumerico = parseFloat(valor);
    if (valorNumerico <= 0 || isNaN(valorNumerico)) {
      setError("O valor deve ser um número positivo.");
      return null;
    }
    return valorNumerico;
  };

  const pagarFaturaComSaldo = async () => {
    const valorNumerico = validarValor(valor);
    if (valorNumerico === null) return;

    try {
      const response = await api.post(
        `/transactions/accounts/${clientId}/${contaPoupancaId}/${cartaoDeCreditoId}/payments-with-creditcard`,
        { valor: valorNumerico, descricao },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (response.status === 200) {
        setSuccessMessage("Pagamento realizado com sucesso.");
        setError("");
        setValor("");
        setDescricao("");
      }
    } catch (e) {
      console.error(e);
      setError("Não foi possível realizar o pagamento, verifique seu saldo.");
    }
  };

  const fetchSaldo = useCallback(async () => {
    if (!clientId || !token) return;

    try {
      const response = await api.get(`/transactions/accounts/${clientId}/credit`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (response.status === 200) {
        setTransactions(response.data);
        setError("");
      }
    } catch (e) {
      console.error(e);
      setError("Erro ao buscar saldo. Tente novamente.");
    }
  }, [clientId, token]);

  useEffect(() => {
    fetchSaldo();
  }, [fetchSaldo]);

  return (
    <div className="flex flex-col items-center gap-[16px] p-[16px] bg-[#1E1E2F] rounded-lg shadow-lg min-h-screen">
      <h1 className="text-[white] text-[32px] sm:text-[48px] font-bold mb-[16px]">
        Pagar Fatura
      </h1>

      <form
        onSubmit={(e) => {
          e.preventDefault();
          pagarFaturaComSaldo();
        }}
        className="w-full max-w-[400px]"
      >
        <div className="mb-4">
          <label htmlFor="valor" className="text-[white] block mt-[16px] mb-[16px]">
            Valor:
          </label>
          <input
            type="number"
            id="valor"
            value={valor}
            onChange={handleValor}
            className="w-full max-w-[330px] p-[7px] rounded-lg bg-[#2D2D42] text-[white] border border-[#3A3A4A] focus:outline-none focus:border-[#00E396] transition-all duration-300"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="descricao" className="text-[white] block mt-[16px] mb-[16px]">
            Descrição:
          </label>
          <input
            type="text"
            id="descricao"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
            className="w-full max-w-[330px] p-[7px] rounded-lg bg-[#2D2D42] text-[white] border border-[#3A3A4A] focus:outline-none focus:border-[#00E396] transition-all duration-300"
            required
          />
              {successMessage && (
        <p className="text-[green] text-[20px] font-urbanist mt-4 text-center">
          {successMessage}
        </p>
      )}
        </div>

        <div className="flex justify-end p-[16px]">
          <button
            type="submit"
            className="p-[9px] rounded-lg bg-[purple] border border-[pink] text-[white] font-semibold hover:bg-[#00C48C] disabled:bg-[#6B6B8A] disabled:cursor-not-allowed transition-all duration-300"
          >
            Pagar Fatura
          </button>
        </div>
      </form>

      <div className="w-full max-w-[400px] mt-8">
        <p className="text-[white] text-[20px] font-bold mb-4">
          Gastos com cartão de crédito:
        </p>
        {transactions.map((transaction) => (
  <div
    key={transaction.id}
    className="p-[16px] mb-[16px] rounded-lg bg-[#2D2D42] text-[white] border border-[#3A3A4A]"
  >
    <p><strong>Descrição:</strong> {transaction.descricao}</p>
    <p><strong>Valor:</strong> R$ {transaction.valor.toFixed(2)}</p>
    <p>
      <strong>Data:</strong> {formatarData(transaction.dataHora)}
    </p>
  </div>
))}
      </div>

  
      {error && (
        <p className="text-[red] text-[14px] sm:text-[16px] mt-4 text-center">
          {error}
        </p>
      )}
    </div>
  );
}