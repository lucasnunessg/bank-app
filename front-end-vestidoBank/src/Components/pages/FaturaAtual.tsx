import { useEffect, useState } from "react";
import api from "../FetchApi";
import { useAuth } from "../contexts/useAuth";

export function Fatura(){

const { clientId, token } = useAuth();

  const [fatura, setFatura] = useState([]);
  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(true);


  useEffect(() => {
    const fetchResponse = async () => {
      setIsLoading(true)
      try{
        const response = await api.get(`/credit-card/${clientId}/fatura-atual`, {
          headers: { Authorization: `Bearer ${token}` },

        })
        if(response.status === 200) {
          setFatura(response.data)
          setError("")
        }
      }catch(e){
        console.error(e)
        setError("Não foi possível encontrar sua fatura")
        
      }finally{
        setIsLoading(false);
      }
    }
    fetchResponse()
  },[clientId, token])

  return(
    <div>
    {isLoading ? (
      <p className="text-[white]">Carregando...</p>
    ) : (
      <>
        {fatura.length > 0 && (
          <div className="">
            <p className="text-[white] flex flex-col items-center">Fatura atual:</p>
            <p className="text-[white] flex flex-col items-center">{fatura[0]}</p>
          </div>
        )}
        {error && <p className="text-[red]">{error}</p>}
      </>
    )}
  </div>
  )
}