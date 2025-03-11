import { useEffect, useState } from "react";
import api from "../FetchApi";
import { useAuth } from "../contexts/useAuth";

function Transf() {
  const [valor, setValor] = useState("0,00"); 
  const [contasDestino, setContasDestino] = useState<{ id: number; name: string }[]>([]);
  const [contaDestinoId, setContaDestinoId] = useState<string | number | null>(null);
  const [loading, setLoading] = useState(false);
  const [transferindo, setTransferindo] = useState(false);
  const [successMessage, setSuccessMessage] = useState(false);
  const [error, setError] = useState("");

  const { clientId, token } = useAuth();
  const contaOrigemId = clientId;

  const handleValorChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const inputValue = e.target.value.replace(/\D/g, ""); 
    const paddedValue = inputValue.padStart(3, "0"); 
    const parteInteira = paddedValue.slice(0, -2); 
    const parteDecimal = paddedValue.slice(-2); 
    const valorFormatado = `${parteInteira},${parteDecimal}`; 
    setValor(valorFormatado);
  };

  useEffect(() => {
    async function fetchContasDestino() {
      if (!clientId || !token) return;
      setLoading(true);

      try {
        const response = await api.get(`/clients-bank/${clientId}/list-clients`, {
          headers: { Authorization: `Bearer ${token}` },
        });
        if (response.status === 200) {
          setContasDestino(response.data);
        }
      } catch (e) {
        console.error("Erro ao buscar contas destino:", e);
      } finally {
        setLoading(false);
      }
    }
    fetchContasDestino();
  }, [clientId, token]);

  async function handleTransfer() {
    if (!valor || !contaDestinoId || !contaOrigemId || !token) {
      setError("Por favor, selecione um destinatário e informe o valor.");
      return;
    }

    setTransferindo(true);
    setError("");
    setSuccessMessage(false);

    try {
      const valorNumerico = parseFloat(valor.replace(",", ".")); 
      const response = await api.post(
        `/transactions/accounts/${contaOrigemId}/transfer/${contaDestinoId}`,
        { valor: valorNumerico },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (response.status === 200) {
        setSuccessMessage(true);
        setTimeout(() => {
          setSuccessMessage(false);
          window.location.reload(); 
        }, 3000);
      }
    } catch (e) {
      console.error("Erro ao realizar a transferência:", e);
      setError("Erro ao realizar a transferência. Tente novamente mais tarde.");
    } finally {
      setTransferindo(false);
    }
  }

  return (
    <div className="flex flex-col items-center gap-[16px] p-[16px] bg-[#1E1E2F] rounded-lg shadow-lg min-h-screen">
      <h1 className="text-[white] text-[32px] sm:text-[48px] font-bold mb-[16px]">
        Transferir para outra conta
      </h1>
  
      {loading ? (
        <p className="text-[white]">Carregando contas disponíveis...</p>
      ) : (
        <div className="w-full max-w-[400px]">
          <label className="text-[white] block mb-2">Lista de contatos:</label>
          <select
            className="w-full p-[7px] rounded-lg bg-[#2D2D42] text-[white] border border-[#3A3A4A] focus:outline-none focus:border-[#00E396] transition-all duration-300"
            value={contaDestinoId || ""}
            onChange={(e) => setContaDestinoId(e.target.value)}
          >
            <option value="">Selecione uma conta</option>
            {contasDestino.map((conta) => (
              <option key={conta.id} value={conta.id}>
                {conta.name}
              </option>
            ))}
          </select>
  
          <label className="text-[white] block mt-[16px] mb-[4px]">Valor da transferência:</label>
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
              className="p-[9px] rounded-lg bg-[purple] border border-[pink] text-[white] font-semibold hover:bg-[#00C48C] disabled:bg-[#6B6B8A] disabled:cursor-not-allowed transition-all duration-300"
              onClick={handleTransfer}
              disabled={transferindo}
            >
              {transferindo ? "Transferindo..." : "Confirmar Transferência"}
            </button>
          </div>
  
         {successMessage && (
            <p className="text-[green] text-[20px] font-urbanist mt-4 text-center">
              Transferência realizada com sucesso!
            </p>
          )}
          {error && (
            <p className="text-[red] text-[14px] sm:text-[16px] mt-4 text-center">
              {error}
            </p>
          )}
        </div>
      )}
    </div>
  );
}

export default Transf;