import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../FetchApi";
import { useAuth } from "../contexts/useAuth";
import ForgotPassword from "./ForgotPassword";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [rememberMe, setRememberMe] = useState<boolean>(false);
  const [showForgotPassword, setShowForgotPassword] = useState<boolean>(false); 
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  const handlePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const handleLogin = async () => {
    if (!email || !password) {
      setError("Por favor, preencha todos os campos.");
      return;
    }

    try {
      const response = await api.post("/auth/login", {
        email: email,
        password: password,
      });

      if (response.status === 200) {
        const token = response.data.token;
        login(token);
        navigate("/home/auth/client");
        window.location.reload();

        if (rememberMe) {
          localStorage.setItem("token", token);
        } else {
          sessionStorage.setItem("token", token);
        }
      }
    } catch (error) {
      console.error("Erro no login:", error);
      setError("E-mail ou senha inválidos");
    }
  };

  return (
    <div className="login-container min-h-screen flex items-center justify-center bg-[#14141F]">
      {!showForgotPassword ? (
        <form
          className="login-input-form w-full max-w-[350px] h-auto bg-[#14141F] border-[fuchsia] p-[10px]"
          onSubmit={(e) => {
            e.preventDefault();
            handleLogin();
          }}
        >
          <h2 className="text-urbanist text-[fuchsia] text-[32px] flex flex-col items-center w-full">
            Vestido Bank
          </h2>

          <div className="mt-6">
            <div className="text-center mb-4 w-full">
              <div className="flex items-center justify-center w-full mb-2 space-x-2">
                <hr className="border-t border-[rgb(52,52,68)] flex-grow" />
                <p className="font-urbanist text-[white] text-[18px]">
                  Insira suas credenciais
                </p>
                <hr className="border-t border-[rgb(52,52,68)] flex-grow" />
              </div>
            </div>

            <div className="input-container mb-4 flex flex-col gap-y-[32px] sm:gap-y-4">
              <input
                type="email"
                placeholder="Email"
                className="email-input-form w-full h-[38px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
                name="email"
                value={email}
                onChange={handleEmail}
              />

              <input
                type="password"
                placeholder="Senha"
                className="password-input-form w-full h-[38px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
                name="password"
                value={password}
                onChange={handlePassword}
              />
            </div>

            {error && (
              <div className="text-[red] text-sm text-center mt-4">{error}</div>
            )}

            <div className="flex justify-between items-center mb-4">
              <div className="flex items-center">
                <input
                  type="checkbox"
                  className="mr-2"
                  checked={rememberMe}
                  onChange={() => setRememberMe(!rememberMe)}
                />
                <label className="text-sm font-urbanist text-[rgb(235,235,235)]">
                  Lembrar-me
                </label>
              </div>
              <div className="ml-[20px]">
                <a
                  className="text-[white] w-full font-urbanist xl:right-[0px] text-sm font-light cursor-pointer"
                  onClick={() => setShowForgotPassword(true)} 
                >
                  Esqueceu sua senha?
                </a>
              </div>
            </div>

            <div className="flex flex-col gap-y-[32px] sm:gap-y-4">
              <button
                type="submit"
                className="bg-primary border border-[fuchsia] text-white font-urbanist rounded-full px-4 py-3 h-[54px] w-full hover:bg-[fuchsia] font-light transition-colors"
              >
                Entrar
              </button>
            </div>
          </div>
        </form>
      ) : (
        <ForgotPassword onBackToLogin={() => setShowForgotPassword(false)} /> 
      )}
    </div>
  );
}

export default Login;