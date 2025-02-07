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
  const [errorType, setErrorType] = useState<boolean>(false);
  const [error, setError] = useState<boolean>(false);

  const [success, setSuccess] = useState<boolean>(false);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    if (name === "password") {
      setErrorType(!passwordRegex.test(value));
    }
    switch (name) {
      case "name":
        setName(value);
        break;
      case "cpf":
        setCpf(value);
        break;
      case "contact":
        setContact(value);
        break;
      case "address":
        setAddress(value);
        break;
      case "email":
        setEmail(value);
        break;
      case "password":
        setPassword(value);
        break;
    }
  };


  const passwordRegex =
    /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

  const handlePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newPassword = event.target.value;

    if (!passwordRegex.test(newPassword)) {
      setErrorType(true);
    } else {
      setErrorType(false);
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
        setSuccess(true);
        setError(false);
        setTimeout(() => {
          navigate("/login");
        }, 3000);
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
          onChange={handleChange}
        />

        <input
          type="name"
          placeholder="nome completo"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="name"
          value={name}
          onChange={handleChange}
        />

        <input
          type="cpf"
          placeholder="insira seu CPF, sem pontos"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="cpf"
          value={cpf}
          onChange={handleChange}
        />

        <input
          type="contact"
          placeholder="número para contato"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="contact"
          value={contact}
          onChange={handleChange}
        />

        <input
          type="address"
          placeholder="endereço completo"
          className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
          name="address"
          value={address}
          onChange={handleChange}
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
          Criar conta
        </button>
        {errorType && (
          <p className="text-[red]">Sua senha deve ter um caractere maiúsculo, número e um caractere especial</p>
        )}
        {error && (
          <p className="text-[red]">Houve um erro ao criar sua conta!</p>
        )}
        {success && <p className="text-[green]">Conta criada com sucesso!</p>}
      </form>
    </div>
  );
}
