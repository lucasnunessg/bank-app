import { useState } from "react";
import api from "../FetchApi";

function ForgotPassword({ onBackToLogin }: { onBackToLogin: () => void }) {
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  const handleForgotPassword = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await api.post("/forgot-password", { email });
      if (response.status === 200) {
        setMessage("Um link de redefinição foi enviado ao seu e-mail");
        setError("");
      }
    } catch (e) {
      console.error(e);
      setError("Não foi possível enviar o e-mail, tente novamente");
      setMessage("");
    }
  };

  return (
    <div className="forgot-password-form w-full max-w-[350px] h-auto bg-[#14141F] border-[fuchsia] p-[10px]">
      <h2 className="text-urbanist text-[fuchsia] text-[32px] flex flex-col items-center w-full">
        Redefinir Senha
      </h2>

      <form onSubmit={handleForgotPassword}>
        <div className="input-container mb-4 flex flex-col gap-y-[32px] sm:gap-y-4">
          <input
            type="email"
            placeholder="Digite seu e-mail"
            className="email-input-form w-full h-[38px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        {message && <p className="text-[green] text-sm text-center mt-4">{message}</p>}
        {error && <p className="text-[red] text-sm text-center mt-4">{error}</p>}

        <div className="flex flex-col gap-y-[32px] sm:gap-y-4">
          <button
            type="submit"
            className="bg-primary border border-[fuchsia] text-white font-urbanist rounded-full px-4 py-3 h-[54px] w-full hover:bg-[fuchsia] font-light transition-colors"
          >
            Enviar
          </button>
          <button
            type="button"
            className="bg-transparent border border-[fuchsia] text-[white] font-urbanist rounded-full px-4 py-3 h-[54px] w-full hover:bg-[fuchsia] font-light transition-colors"
            onClick={onBackToLogin} 
          >
            Voltar ao Login
          </button>
        </div>
      </form>
    </div>
  );
}

export default ForgotPassword;