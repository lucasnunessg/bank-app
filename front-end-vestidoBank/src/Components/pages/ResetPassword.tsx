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

  console.log(token);

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
        confirmPassword
      });

      if (response.status === 200) {
        setMessageSuccess("Você redefiniu sua senha.");
        setError("");
        setTimeout(() => {
        navigate("/login");
        }, 2000)
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
    <div>
      <h2 className="text-[white]">Redefinir senha:</h2>
      <form onSubmit={handleNewPassword}>
        <p className="text-[white]">Digite a sua nova senha:</p>
        <input
          type={showPassword ? "text" : "password"} 
          placeholder="deve ter uma letra maiúscula, números e 1 caractere especial"
          onChange={(e) => setPassword(e.target.value)}
          value={password}
        />
        <button
          type="button"
          onClick={() => setShowPassword(!showPassword)}
          className="text-[blue] ml-10 border-[white]"

        >
          {showPassword ? "Esconder" : "Mostrar"}
        </button>

        <p className="text-[white]">Confirme sua senha:</p>
        <input
          type={showPassword ? "text" : "password"} 
          placeholder="digite novamente a senha"
          onChange={(e) => setConfirmPassword(e.target.value)}
          value={confirmPassword}
        />

        {messageSuccess && <p className="text-[green]">{messageSuccess}</p>}
        {error && <p className="text-[red]">{error}</p>}

        <button type="submit" className="text-[white]" disabled={!!error}>
          Redefinir Senha
        </button>
      </form>
    </div>
  );
}
