import { ReactEventHandler, useState } from "react";
import api from "../FetchApi";
import { useAuth } from "../contexts/useAuth";

export function ExchangeInformation(){

  const { token, clientId} = useAuth();

  const [name, setName] = useState("");
  const [cpf, setCpf] = useState("");
  const [contact, setContact] = useState("");
  const [address, setAddress] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");


  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try{
      const fetchData = await api.put(`/clients-bank/${clientId}`, {
        name,
        cpf,
        contact,
        address,
        email,
        password
      }, {
        headers: {
          Authorization: `Bearer ${token}` 
    }});
    if(fetchData.status === 200) {
      setSuccess("Os dados foram alterados com sucesso!");
      setError("");
    }
    }catch(e){
      console.error(e)
      setSuccess("");
      setError("Não foi possível alterar seus dados");
    }
  }


  return (
    <div>
      <form onSubmit={handleSubmit}>
        
      </form>
    </div>
  )
}