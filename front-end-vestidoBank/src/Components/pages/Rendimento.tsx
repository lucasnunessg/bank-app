import { useEffect, useState } from "react";
import { useAuth } from "../contexts/useAuth";
import api from "../FetchApi";


export function Rendimento(){

  const [valor, setValor] = useState("");
  const [error, setError] = useState("");
  const { clientId, token } = useAuth();

const contaPoupancaId = clientId;





    useEffect(() => {

    const fetchData = async () => {
try{
  const response = await api.get(`/conta-poupanca/${contaPoupancaId}/rendimento`, {
    headers: { Authorization: `Bearer ${token}` },

  })
  if(response.status === 200) {
    setValor(response.data);
    setError("")
  }
}catch(e) {
  console.error(e)
  setError("Não foi possível encontrar seu saldo.")
}
    }  
      
    fetchData()

  },[contaPoupancaId, token])

  return (
    <div className="bg-[#1E1E2F]">
      <h2 className="text-[white] ml-[20px]">Total de investimentos:</h2>
        <p className="text-[white] ml-[20px]">
          R$ {valor}
        </p>
      {error && <p className="text-[red]">{error}</p>}
    </div>
  )
}