import { useState } from "react";
import { useAuth } from "../contexts/useAuth";
import api from "../FetchApi";

export function Deposito() {
  const { clientId, token } = useAuth();
  const [valor, setValor] = useState<number | string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [success, setSuccess] = useState<boolean>(false);
  const [error, setError] = useState<boolean>(false);


   
  const contaPoupancaId = clientId;

  

  const handleDeposito = async () => {
    if (!contaPoupancaId || !clientId || !valor) return;
    setIsLoading(true);

    try {

      const response = await api.post(
        `/conta-poupanca/${contaPoupancaId}/client/${clientId}/deposit`,
        { valor }, 
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (response.status === 200) {
        setSuccess(true)
        setError(false);
      }
    } catch (e) {
      console.error(e);
      setError(true);
      setSuccess(false)
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      <h1 className="text-[white]">Depósito</h1>
      <input
        type="number"
        value={valor}
        onChange={(e) => setValor(e.target.value)}
        placeholder="Digite o valor"
      />
      <button onClick={handleDeposito} disabled={isLoading}>
        {isLoading ? "Processando..." : "Depositar"}
      </button>

      {success && (
            <p className="text-[green] text-[20px] font-urbanist">Depósito realizado com sucesso</p>
          )}
      {error && (
        <p className="text-[red]">Ocorreu um erro ao realizar depósito!</p>
      )}
    </div>
  );
}
