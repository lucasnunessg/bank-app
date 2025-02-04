import { useEffect, useState } from "react";
import api from "./FetchApi";


function Transf() {
  const [valor, setValor] = useState<number | "">("");
  const [clientId, setClientId] = useState<string | number | null>(null);
  const [contaOrigemId, setContaOrigemId] = useState<string | number | null>(
    null
  );
  const [contasDestino, setContasDestino] = useState<
    { id: number; name: string }[]
  >([]);
  const [contaDestinoId, setContaDestinoId] = useState<string | number | null>(
    null
  );
  const [loading, setLoading] = useState<boolean>(false);
  const [transferindo, setTransferindo] = useState<boolean>(false);

  useEffect(() => {
    const storedClientId = localStorage.getItem("clientId");
    if (storedClientId !== null) {
      try {
        setClientId(JSON.parse(storedClientId));
        setContaOrigemId(JSON.parse(storedClientId));
      } catch (error) {
        console.error("Erro ao parsear clientId:", error);
      }
    }
  }, [setContaOrigemId]);



  useEffect(() => {
    async function fetchContasDestino() {
      if (!clientId) return;
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
  }, [clientId]);


  async function handleTransfer() {
    if (!valor || !contaDestinoId || !clientId) {
      alert("Por favor, selecione um destinatário e informe o valor.");
      return;
    }

    setTransferindo(true);

    try {
      const token = localStorage.getItem("token");
      console.log("token: " , token);
      console.log("valor: ", valor);
      console.log("conta destiino: " , contaDestinoId);
      console.log("conta origem: ", contaOrigemId);
      
        
 
      const response = await api.post(
        `/transactions/accounts/${contaOrigemId}/transfer/${contaDestinoId}`,
        { valor: Number(valor) }, 
        { headers: { Authorization: `Bearer ${token}` } } 
      );

      if (response.status === 200) {
        alert("Transferência realizada com sucesso!");
        setValor("");
        setContaDestinoId(null);
        setContaOrigemId(null);
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
            Selecione para quem você quer enviar:
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
        </>
      )}
    </div>
  );
}

export default Transf;
