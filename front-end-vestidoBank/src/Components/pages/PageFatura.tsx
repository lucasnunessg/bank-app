import { useState } from "react";
import { useAuth } from "../contexts/useAuth";
import api from "../FetchApi";

export function PageFatura(){
  
  const { clientId, token } = useAuth();
  const [valor, setValor] = useState("");
  const [descricao, setDescricao] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [error, setError] = useState("");

  const contaPoupancaId = clientId;
  const cartaoDeCreditoId = clientId;

  const handleValor = (event: React.ChangeEvent<HTMLInputElement>) => {
    setValor(event.target.value);
  }

  const pagarFaturaComSaldo = async () => {
    const valorNumerico = parseFloat(valor);
    if (valorNumerico <= 0 || isNaN(valorNumerico)) {
      setError("O valor deve ser um número positivo.");
      return;
    }
  
    try {
      const response = await api.post(
        `/${clientId}/${contaPoupancaId}/${cartaoDeCreditoId}/payments-with-creditcard`,
        {
          valor: valorNumerico, 
          descricao,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
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
  return(
    <div>
      <form
      onSubmit={(e) => {
        e.preventDefault();
        pagarFaturaComSaldo();
      }}>
                <div>
          <label htmlFor="valor">Valor:</label>
          <input
            type="number"
            id="valor"
            value={valor}
            onChange={handleValor}
            required
          />
        </div>
        <div>
          <label htmlFor="descricao">Descrição:</label>
          <input
            type="text"
            id="descricao"
            value={descricao}
            onChange={(e) => setDescricao(e.target.value)}
            required
          />
        </div>
        <button type="submit">Pagar Fatura</button>
      {successMessage && <p>{successMessage}</p>}
      {error && <p>{error}</p>}

      </form>

    </div>
  )
}