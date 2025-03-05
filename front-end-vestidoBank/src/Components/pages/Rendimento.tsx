import { useEffect, useState } from "react";
import { useAuth } from "../contexts/useAuth";
import api from "../FetchApi";


export function Rendimento(){

  const [valor, setValor] = useState("");
  const [error, setError] = useState("");
  const { clientId, token } = useAuth();

const contaPoupancaId = clientId;
console.log(token);
console.log(clientId);
console.log(contaPoupancaId);




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
    <div>
      <h2 className="text-[white]">Seu total de rendimento até a presente data é:</h2>
        <p className="text-[white]">
          R$ {valor}
        </p>
      {error && <p className="text-[red]">{error}</p>}
    </div>
  )
}