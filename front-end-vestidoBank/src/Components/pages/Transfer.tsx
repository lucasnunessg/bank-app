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
    if (!valor || !contaDestinoId || !contaOrigemId || !token)  {
      alert("Por favor, selecione um destinatário e informe o valor.");
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
    <div>
      <h1 className="text-[white]">Transferir para outra conta</h1>

      {loading ? (
        <p>Carregando contas disponíveis...</p>
      ) : (
        <>
          <label className="text-[white]">
            Lista de contatos:
          </label>
          <select
            className="text-[white]"
            value={contaDestinoId || ""}
            onChange={(e) => setContaDestinoId(e.target.value)}
          >
            <option value="" className="text-[white]">
              Selecione uma conta
            </option>
            {contasDestino.map((conta) => (
              <option className="text-[black]" key={conta.id} value={conta.id}>
                {conta.name}
              </option>
            ))}
          </select>

          <br />

          <label className="text-[white]">Valor da transferência:</label>
          <input
            type="number"
            className="text-[black]"
            value={valor}
            onChange={(e) =>
              setValor(e.target.value ? Number(e.target.value) : "")
            }
            placeholder="Digite o valor"
          />

          <br />

          <button
            className="text-[black]"
            onClick={handleTransfer}
            disabled={transferindo}
          >
            {transferindo ? "Transferindo..." : "Confirmar Transferência"}
          </button>

          {successMessage && (
            <p className="text-[green] text-[20px] font-urbanist">Transferênca realizada com sucesso</p>
          )}
        </>
      )}
    </div>
  );
}

export default Transf;
