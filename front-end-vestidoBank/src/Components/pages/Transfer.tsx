import { useEffect, useState } from "react";
import api from "../FetchApi";
import { useAuth } from "../contexts/useAuth";

function Transf() {
  const [valor, setValor] = useState<number | "">("");
  const [contasDestino, setContasDestino] = useState<
    { id: number; name: string }[]
  >([]);
  const [contaDestinoId, setContaDestinoId] = useState<string | number | null>(
    null
  );
  const [loading, setLoading] = useState<boolean>(false);
  const [transferindo, setTransferindo] = useState<boolean>(false);
  const [successMessage, setSuccessMessage] = useState<boolean>(false);

  const { clientId, token } = useAuth();

  const contaOrigemId = clientId;

  useEffect(() => {
    async function fetchContasDestino() {
      if (!clientId || !token) return;
      setLoading(true);


      try {
        const token = localStorage.getItem("token");
        const response = await api.get(
          `/clients-bank/${clientId}/list-clients`,
          {
            headers: { Authorization: `Bearer ${token}` },
          }
        );
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
      alert("Por favor, selecione um destinatário e informe o valor.");
      console.log(handleTransfer);
      
      return;
    }

    setTransferindo(true);

    try {
      const token = localStorage.getItem("token");

      const response = await api.post(
        `/transactions/accounts/${contaOrigemId}/transfer/${contaDestinoId}`,
        { valor: Number(valor) },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (response.status === 200) {
        setValor("");
        setContaDestinoId(null);
        setSuccessMessage(true);
        setTimeout(() => {
          setSuccessMessage(false);
        }, 3000);
      }
    } catch (e) {
      console.error("Erro ao realizar a transferência:", e);
      alert("Erro ao realizar a transferência.");
    } finally {
      setTransferindo(false);
    }
  }

  return (
    <div className="flex flex-col items-center justify-center mt-[-120px] min-h-screen p-[10px]">
      <h1 className="text-[fuchsia] text-3xl mb-4 font-bold">Transferir para outra conta</h1>

      {loading ? (
        <p className="text-[fuchsia]">Carregando contas disponíveis...</p>
      ) : (
        <div className="w-full bg-[#111] rounded-2xl shadow-lg gap-[20px]">
          <label className="text-[white] flex flex-col items-center block mb-2">Lista de contatos:</label>
          <select
            className="w-full p-2 mb-4 rounded-md bg-[#222] text-[fuchsia] border border-[fuchsia]"
            value={contaDestinoId || ""}
            onChange={(e) => setContaDestinoId(e.target.value)}
          >
            <option value="">Selecione uma conta</option>
            {contasDestino.map((conta) => (
              <option className="text-[black]" key={conta.id} value={conta.id}>
                {conta.name}
              </option>
            ))}
          </select>

          <label className="text-[fuchsia] block mb-2">Valor da transferência:</label>
          <input
            type="number"
            className="w-full p-2 mb-4 rounded-md bg-[#222] h-[22px] text-[fuchsia] border border-[fuchsia]"
            value={valor}
            onChange={(e) =>
              setValor(e.target.value ? Number(e.target.value) : "")
            }
            placeholder="Digite o valor"
          />

          <button
            className="w-full p-2 rounded-md bg-[fuchsia] text-[black] font-bold hover:bg-[#ff00ff] transition-all duration-300"
            onClick={handleTransfer}
            disabled={transferindo}
          >
            {transferindo ? "Transferindo..." : "Confirmar Transferência"}
          </button>

          {successMessage && (
            <p className="text-[green] text-[20px] font-urbanist mt-4">Transferênca realizada com sucesso</p>
          )}
        </div>
      )}
    </div>
  );
}

export default Transf;
