import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import api from "../FetchApi";

export function ResetPassword() {
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");
  const [showPassword, setShowPassword] = useState(false);
  const [password, setPassword] = useState("");
  const [messageSuccess, setMessageSuccess] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    if (!token) {
      navigate("/");
    }
  }, [token, navigate]);

  useEffect(() => {
    if (password && confirmPassword) {
      if (password !== confirmPassword) {
        setError("As senhas não coincidem");
      } else {
        setError("");
      }
    }
  }, [password, confirmPassword]);

  const handleNewPassword = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (error) return;

    try {
      const response = await api.post("/reset-password", {
        token,
        password,
        confirmPassword,
      });

      if (response.status === 200) {
        setMessageSuccess("Você redefiniu sua senha.");
        setError("");
        setTimeout(() => {
          navigate("/login");
        }, 2000);
      }
    } catch (e) {
      console.error(e);
      setError("Não foi possível redefinir a senha, tente novamente.");
      setMessageSuccess("");
    }
  };

  if (!token) {
    return <p className="text-[white]">Carregando...</p>;
  }

  return (
    <div className="flex flex-col items-center gap-[16px] p-[16px] bg-[#1E1E2F] rounded-lg shadow-lg min-h-screen">
      <h2 className="text-[white] text-[32px] sm:text-[48px] font-bold mb-[16px]">
        Redefinir senha
      </h2>

      <form
        onSubmit={handleNewPassword}
        className="w-full max-w-[400px]"
      >
        <div className="mb-4">
          <p className="text-[white] block mb-2">Digite a sua nova senha:</p>
          <div className="flex items-center gap-2">
            <input
              type={showPassword ? "text" : "password"}
              placeholder="Deve ter uma letra maiúscula, números e 1 caractere especial"
              onChange={(e) => setPassword(e.target.value)}
              value={password}
              className="w-full p-[7px] rounded-lg bg-[#2D2D42] text-[white] border border-[#3A3A4A] focus:outline-none focus:border-[#00E396] transition-all duration-300"
            />
            <button
              type="button"
              onClick={() => setShowPassword(!showPassword)}
              className="p-[7px] bg-[#4A4A68] text-[white] font-semibold hover:bg-[#00C48C] transition-all duration-300"
            >
              {showPassword ? "Esconder" : "Mostrar"}
            </button>
          </div>
        </div>

        <div className="mb-4">
          <p className="text-[white] block mb-2">Confirme sua senha:</p>
          <input
            type={showPassword ? "text" : "password"}
            placeholder="Digite novamente a senha"
            onChange={(e) => setConfirmPassword(e.target.value)}
            value={confirmPassword}
            className="w-full p-[7px] rounded-lg bg-[#2D2D42] text-[white] border border-[#3A3A4A] focus:outline-none focus:border-[#00E396] transition-all duration-300"
          />
        </div>

        {messageSuccess && (
          <p className="text-[green] text-[20px] font-urbanist mt-4 text-center">
            {messageSuccess}
          </p>
        )}
        {error && (
          <p className="text-[red] text-[14px] sm:text-[16px] mt-4 text-center">
            {error}
          </p>
        )}

        <div className="flex justify-end">
          <button
            type="submit"
            className="p-[9px] bg-[#4A4A68] text-[white] font-semibold hover:bg-[#00C48C] disabled:bg-[#6B6B8A] disabled:cursor-not-allowed transition-all duration-300"
            disabled={!!error}
          >
            Redefinir Senha
          </button>
        </div>
      </form>
    </div>
  );
}