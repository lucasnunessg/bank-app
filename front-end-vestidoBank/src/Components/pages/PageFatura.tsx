import { useCallback, useEffect, useState } from "react";
import { useAuth } from "../contexts/useAuth";
import api from "../FetchApi";



export function PageFatura(){

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
  }

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
        setError("")
      }
    } catch (e) {
      console.error(e);
      setError("Erro ao buscar saldo. Tente novamente.");
    }
  }, [clientId, token]);
  
  useEffect(() => {
    fetchSaldo();
  }, [fetchSaldo]);
  
 

  return(
    <div>
      <form
      onSubmit={(e) => {
        e.preventDefault();
        pagarFaturaComSaldo();
      }}>
                <div>
          <label htmlFor="valor" className="text-[white]">Valor:</label>
          <input
            type="number"
            id="valor"
            className="text-[white]"
            value={valor}
            onChange={handleValor}
            required
          />
        </div>
        <div>
          <label htmlFor="descricao" className="text-[white]">Descrição:</label>
          <input
            type="text"
            id="descricao"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="text-[white]">Pagar Fatura</button>

        <p className="text-[white]">Resumo da fatura:</p>
        {transactions.map((transaction) => (
          <div key={transaction.id}>
            <p className="text-[white]">Descrição: {transaction.descricao} </p>
            <p className="text-[white]">Valor R$: {transaction.valor.toFixed(2)}</p>
            <p className="text-[white]">{transaction.dataHora}</p>
            

          </div>
        ))}
      {successMessage && <p className="text-[green]">{successMessage}</p>}
      {error && <p className="text-[red]">{error}</p>}

      </form>

    </div>
  )
}