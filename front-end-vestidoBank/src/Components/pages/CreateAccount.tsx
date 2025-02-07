import React, { useState } from "react";
import api from "../FetchApi";
import { useNavigate } from "react-router-dom";

export function CreateAccount() {
  const navigate = useNavigate();
  const [name, setName] = useState<string>("");
  const [cpf, setCpf] = useState<string>("");
  const [contact, setContact] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<boolean>(false);
  const [success, setSuccess] = useState<boolean>(false);

  const handleName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setName(event.target.value);
  };

  const handleCpf = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCpf(event.target.value);
  };

  const handleContact = (event: React.ChangeEvent<HTMLInputElement>) => {
    setContact(event.target.value);
  };

  const handleAddress = (event: React.ChangeEvent<HTMLInputElement>) => {
    setAddress(event.target.value);
  };

  const handleEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  console.log(email);
  console.log(name);
  console.log(cpf);
  console.log(contact);
  console.log(address);
  console.log(email);
  console.log(password);

  const passwordRegex =
    /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  const handlePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newPassword = event.target.value;

    if (!passwordRegex.test(newPassword)) {
      setError(true);
    } else {
      setError(false);
    }
    setPassword(newPassword);
  };

  const handleCreateAccount = async () => {
    if (!name || !cpf || !contact || !address || !email || !password) {
      setError(true);
      return;
    }
    try {
      const response = await api.post("/clients-bank", {
        name,
        cpf,
        contact,
        address,
        email,
        password,
      });
      if (response.status === 201) {
        navigate("/home/auth/client");
        setSuccess(true);
        setError(false);
      }
    } catch (e) {
      console.error(e);
      setError(true);
      setSuccess(false);
    }
  };

  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleCreateAccount();
        }}
      >
        <input
          type="email"
          placeholder="Email"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="email"
          value={email}
          onChange={handleEmail}
        />

        <input
          type="name"
          placeholder="nome completo"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="name"
          value={name}
          onChange={handleName}
        />

        <input
          type="cpf"
          placeholder="insira seu CPF, sem pontos"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="cpf"
          value={cpf}
          onChange={handleCpf}
        />

        <input
          type="contact"
          placeholder="número para contato"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="contact"
          value={contact}
          onChange={handleContact}
        />

        <input
          type="address"
          placeholder="endereço completo"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="address"
          value={address}
          onChange={handleAddress}
        />

        <input
          type="password"
          placeholder="sua senha. Ela deve conter caractéres maiúsculo e 1 caractere especial"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="password"
          value={password}
          onChange={handlePassword}
        />
        <button
          type="submit"
          className="bg-primary border border-[fuchsia] text-white font-urbanist rounded-full px-4 py-3 h-[54px] w-full hover:bg-[fuchsia] font-light transition-colors"
        >
          Entrar
        </button>
        {error && (
          <p className="text-[red]">Houve um erro ao criar sua conta!</p>
        )}
        {success && <p className="text-[green]">Conta criada com sucesso!</p>}
      </form>
    </div>
  );
}
