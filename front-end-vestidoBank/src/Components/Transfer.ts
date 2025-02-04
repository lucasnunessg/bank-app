import { useEffect, useState } from "react";
import api from "./FetchApi";

function Transf() {

  const [valor, setValor] = useState<Number>();
  const [clientId, setClientId] = useState<string | number | null>(null);



  useEffect(() => {
    const storedClientId = localStorage.getItem("clientId")
    if(storedClientId) {
      setClientId(JSON.parse(storedClientId));
    }
  }, [])

}

export default Transf;