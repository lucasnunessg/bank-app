import { useEffect, useState } from "react";
import api from "../FetchApi";
import { useAuth } from "../contexts/useAuth";

export function Saque() {
  const [valor, setValor] = useState("0,00"); 
  const [saldo, setSaldo] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const { clientId, token } = useAuth();

  const contaPoupancaId = clientId;

  const handleValorChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value.replace(/\D/g, "");
    const paddedValue = inputValue.padStart(3, "0"); 
    const parteInteira = paddedValue.slice(0, -2); 
    const parteDecimal = paddedValue.slice(-2); 
    const valorFormatado = `${parteInteira},${parteDecimal}`; 
    setValor(valorFormatado);
  };

  useEffect(() => {
    const saldoAtual = async () => {
      if(!clientId || !token) return;
      try{
        const apiResponse = await api.get(`/conta-poupanca/${clientId}/saldo`, {
          headers: { Authorization: `Bearer ${token}` },

        })
        if(apiResponse.status === 200) {
          setSaldo(apiResponse.data.saldo)
        }
      }catch(e) {
        console.error(e);
      }
    }
    saldoAtual()
  }, [clientId, token])

  const sakeMoney = async () => {
    if (!clientId || !token) return;
    setIsLoading(true);
    setError("");
    setSuccess("");

    try {
      const valorNumerico = parseFloat(valor.replace(",", ".")); 
      const response = await api.put(
        `/conta-poupanca/${contaPoupancaId}/client/${clientId}/sake`,
        {
          valor: valorNumerico,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      if (response.status === 200) {
        setSuccess("Saque efetuado com sucesso!");
        setTimeout(() => {
          window.location.reload();
        }, 3000); 
      
      }
    } catch (e) {
      console.error(e);
      setError("Não foi possível sacar seu dinheiro, verifique seu saldo e tente novamente mais tarde.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex flex-col items-center  gap-[20px] p-[20px]  rounded-lg shadow-lg">
      <div className="flex justify-between items-center gap-[36px]">
      <p className="text-[white]">Saldo atual: </p>
      <p className="text-[white]">R$ {saldo}</p>  
      </div>
      

      <div className="flex items-center gap-2">
        <div className="text-[white] text-[22px] sm:text-[28px] font-bold flex items-center">
          R${" "}
          <input
            type="text"
            value={valor}
            onChange={handleValorChange}
            className="bg-transparent border-none outline-none w-[100px] text-[white] text-[32px] sm:text-[48px] font-bold"
          />
        </div>
        <img
          src="/lapiis.png"
          alt="Ícone"
          className="w-[18px] h-[20px] sm:w-[30px] sm:h-[50px] flex flex-col"
        />
      </div>


     {error && (
        <p className="text-[red] text-[14px] sm:text-[16px] text-center">
          {error}
        </p>
      )}
      {success && (
        <p className="text-[green] text-[14px] sm:text-[16px] text-center">
          {success}
        </p>
      )}



     <button
        onClick={sakeMoney}
        disabled={isLoading}
        className="w-[80px] p-3 rounded-lg bg-[purple] border solid border-bg-[pink] text-[white] font-semibold hover:bg-[pink] disabled:bg-[#6B6B8A] disabled:cursor-not-allowed transition-all duration-300"
      >
        {isLoading ? "Processando..." : "Sacar dinheiro"}
      </button>
    </div>
  );
}