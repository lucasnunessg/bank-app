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
    <div className="sm:w-[490px]  h-auto bg-[#14141F] mt-[50px] min-h-screen gap-[10px] flex flex-col items-center justify-center ">
      <form
        className="max-w-[400px] p-[20px] mt-[-200px]"
        onSubmit={(e) => {
          e.preventDefault();
          handleCreateAccount();
        }}
      >
        <div className="flex items-center justify-center w-full mb-2 space-x-2">
          <hr className="border-t border-[rgb(52,52,68)] flex-grow" />
          <p className="font-urbanist text-[white] text-[18px]">
            Crie sua conta
          </p>

          <hr className="border-t border-[rgb(52,52,68)] flex-grow" />
        </div>

        <div className="space-y-[10px] w-full max-w-sm min-w-[200px]">
          <input
            type="email"
            placeholder="E-mail"
            className="w-full bg-transparent placeholder:text-[white] text-[white] text-sm border border-[white] rounded-md  py-[4px] transition duration-300 ease focus:outline-none focus:border-[fuchsia] hover:border-[fuchsia] shadow-sm focus:shadow"
            name="email"
            value={email}
            onChange={handleChange}
          />

          <input
            type="name"
            placeholder="Nome completo"
            className="w-full bg-transparent placeholder:text-[white] text-[white] text-sm border border-[white] rounded-md  py-[4px] transition duration-300 ease focus:outline-none focus:border-[fuchsia] hover:border-[fuchsia] shadow-sm focus:shadow"
            name="name"
            value={name}
            onChange={handleChange}
          />

          <input
            type="cpf"
            placeholder="Seu CPF, sem pontos"
            className="w-full bg-transparent placeholder:text-[white] text-[white] text-sm border border-[white] rounded-md  py-[4px] transition duration-300 ease focus:outline-none focus:border-[fuchsia] hover:border-[fuchsia] shadow-sm focus:shadow"
            name="cpf"
            value={cpf}
            onChange={handleChange}
          />

          <input
            type="contact"
            placeholder="Contato"
            className="w-full bg-transparent placeholder:text-[white] text-[white] text-sm border border-[white] rounded-md  py-[4px] transition duration-300 ease focus:outline-none focus:border-[fuchsia] hover:border-[fuchsia] shadow-sm focus:shadow"
            name="contact"
            value={contact}
            onChange={handleChange}
          />

          <input
            type="address"
            placeholder="Endereço"
            className="w-full bg-transparent placeholder:text-[white] text-[white] text-sm border border-[white] rounded-md  py-[4px] transition duration-300 ease focus:outline-none focus:border-[fuchsia] hover:border-[fuchsia] shadow-sm focus:shadow"
            name="address"
            value={address}
            onChange={handleChange}
          />

          <input
            type="password"
            placeholder="Senha"
            className="w-full bg-transparent placeholder:text-[white] text-[white] text-sm border border-[white] rounded-md  py-[4px] transition duration-300 ease focus:outline-none focus:border-[fuchsia] hover:border-[fuchsia] shadow-sm focus:shadow"
            name="password"
            value={password}
            onChange={handlePassword}
          />
          <div className="flex flex-col items-center ">
          <button
            type="submit"
            className="bg-transparent hover:bg-[fuchsia] text-[white] font-semibold hover:text-[white] py-2 px-4 border border-[white] hover:border-transparent rounded"
          >
            Criar conta
          </button>
          </div>

        </div>

        {errorType && (
          <p className="text-[yellow]">
            Sua senha deve ter um caractere maiúsculo, número e um caractere
            especial
          </p>
        )}
        {error && (
          <p className="text-[red]">Houve um erro ao criar sua conta!</p>
        )}
        {success && <p className="text-[green]">Conta criada com sucesso!</p>}
      </form>
    </div>
  );
}
