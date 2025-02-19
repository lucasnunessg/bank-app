import { useState } from "react";
import api from "../FetchApi";
import { useAuth } from "../contexts/useAuth";

export function ExchangeInformation() {
  const { token, clientId } = useAuth();

  const [name, setName] = useState("");
  const [contact, setContact] = useState("");
  const [address, setAddress] = useState("");
  const [email, setEmail] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setName(event.target.value);
  };

  const handleEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handleContact = (event: React.ChangeEvent<HTMLInputElement>) => {
    setContact(event.target.value);
  };

  const handleAddress = (event: React.ChangeEvent<HTMLInputElement>) => {
    setAddress(event.target.value);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    try {
      const fetchData = await api.put(
        `/clients-bank/${clientId}`,
        {
          name,
          contact,
          address,
          email,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (fetchData.status === 200) {
        setSuccess("Os dados foram alterados com sucesso!");
        setError("");
      }
    } catch (e) {
      console.error(e);
      setSuccess("");
      setError("Não foi possível alterar seus dados");
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit} className="flex flex-col gap-4">
        <label className="text-[white]">
          Nome:
          <input type="text" value={name} onChange={handleName} />
        </label>

        <label className="text-[white]">
          E-mail:
          <input type="email" value={email} onChange={handleEmail} />
        </label>

        <label className="text-[white]">
          Contato:
          <input type="text" value={contact} onChange={handleContact} />
        </label>

        <label className="text-[white]">
          Endereço:
          <input type="text" value={address} onChange={handleAddress} />
        </label>

        <button type="submit">Atualizar</button>

        {success && <p className="text-[green]-500">{success}</p>}
        {error && <p className="text-[red]-500">{error}</p>}
      </form>
    </div>
  );
}
