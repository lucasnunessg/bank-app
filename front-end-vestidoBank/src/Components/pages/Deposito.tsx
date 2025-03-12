import { useState } from "react";
import { useAuth } from "../contexts/useAuth";
import api from "../FetchApi";

export function Deposito() {
  const { clientId, token } = useAuth();
  const [valor, setValor] = useState("0,00"); 
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [success, setSuccess] = useState<boolean>(false);
  const [error, setError] = useState<boolean>(false);

  const contaPoupancaId = clientId;

  const handleValorChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value.replace(/\D/g, ""); 
    const paddedValue = inputValue.padStart(3, "0"); 
    const parteInteira = paddedValue.slice(0, -2); 
    const parteDecimal = paddedValue.slice(-2);
    const valorFormatado = `${parteInteira},${parteDecimal}`; 
    setValor(valorFormatado);
  };



  const handleDeposito = async () => {
    if (!contaPoupancaId || !clientId || !valor) {
      setError(true);
      return;
    }

    setIsLoading(true);
    setError(false);
    setSuccess(false);

    try {
      const valorNumerico = parseFloat(valor.replace(",", ".")); 

      const response = await api.post(
        `/conta-poupanca/${contaPoupancaId}/client/${clientId}/deposit`,
        { valor: valorNumerico }, 
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (response.status === 200) {
        setSuccess(true);
      }
    } catch (e) {
      console.error(e);
      setError(true);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center gap-[16px] p-[16px] bg-[#1E1E2F] rounded-lg shadow-lg min-h-screen">
      <h1 className="text-[white] text-[32px] sm:text-[48px] font-bold mb-[16px]">
        Depósito
      </h1>

      <div className="w-full max-w-[400px]">
        <label className="text-[white] block mb-[8px] p-[16px]">Valor do depósito:</label>
        <div className="flex justify-center">
          <div className="text-[white] text-[32px] sm:text-[48px] font-bold flex items-center">
            R${" "}
            <input
              type="text"
              value={valor}
              onChange={handleValorChange}
              className="bg-transparent border-none outline-none w-[200px] text-[white] text-[32px] sm:text-[48px] font-bold text-center"
            />
          </div>
        </div>

        <div className="flex justify-end mt-[36px]">
          <button
            className="p-[16px] rounded-lg bg-[purple] border border-[pink] text-[white] font-semibold hover:bg-[#00C48C] disabled:bg-[#6B6B8A] disabled:cursor-not-allowed transition-all duration-300"
            onClick={handleDeposito}
            disabled={isLoading}
          >
            {isLoading ? "Processando..." : "Depositar"}
          </button>
        </div>

        {success &&  (

            
          <p className="text-[green] text-[20px] font-urbanist mt-4 text-center">
            Depósito realizado com sucesso!
          </p>
          

        )}


        {error && (
          <p className="text-[red] text-[14px] sm:text-[16px] mt-4 text-center">
            Ocorreu um erro ao realizar o depósito!
          </p>
        )}
      </div>
    </div>
  );
}