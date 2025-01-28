import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import api from "../FetchApi";

interface DecodedToken {
  sub: string;
  name?: string;
}

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [welcomeMessage, setWelcomeMessage] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const storedClientName = localStorage.getItem("name");
    if (storedClientName) {
      setWelcomeMessage(storedClientName);
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("name");
    setWelcomeMessage(null);
  };

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
        localStorage.setItem("token", token);

        const decodedToken = jwtDecode<DecodedToken>(token);
        const clientName = decodedToken.name || decodedToken.sub;
        setWelcomeMessage(clientName);
        localStorage.setItem("name", clientName);

        navigate("/home/auth/client");
      }
    } catch (error) {
      console.error("Erro no login: ", error);
      setError("E-mail ou senha inválidos");
    }
  };

  return (
    <div className="login-container min-h-screen bg-[#14141F]">
      {welcomeMessage ? (
        <div className="welcome-message">
          <p className="text-white font-urbanist text-lg">
            Bem-vindo, {welcomeMessage}!
          </p>
          <button
            onClick={handleLogout}
            className="bg-red-500 text-white font-urbanist rounded-full px-4 py-2 mt-4 hover:bg-red-600 transition-colors"
          >
            Logout
          </button>
        </div>
      ) : (
        <form
          className="login-input-form w-full max-w-[690px] h-auto top-[315px] left-[615px] opacity-100 p-6 bg-[#14141F] rounded-lg mx-auto sm:top-[150px] sm:left-0"
          onSubmit={(e) => {
            e.preventDefault();
            handleLogin();
          }}
        >
          <h2 className="text-urbanist text-[fuchsia] text-[32px] text-center w-full absolute top-[200px] right-[0px] ">
            Vestido Bank
          </h2>
          <div className="mt-[220px] border-[1px] border-solid border-[fuchsia] rounded-[8px] p-[30px]">
            <div className="text-center mb-4 w-full">
              <div className="flex items-center justify-center w-full mb-2 space-x-2">
                <hr className="border-t border-[rgb(52,52,68)] flex-grow" />
                <p className="font-urbanist text-[white] text-[18px]">
                  Insira email e senha para continuar
                </p>
                <hr className="border-t border-[rgb(52,52,68)] flex-grow" />
              </div>
            </div>

            <div className="input-container mb-4 flex flex-col gap-y-[32px] sm:gap-y-4">
              <input
                type="email"
                placeholder="Email"
                className="email-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
                name="email"
                value={email}
                onChange={handleEmail}
              />

              <input
                type="password"
                placeholder="Senha"
                className="password-input-form w-full h-[48px] px-4 rounded-[8px] border-[1px] border-solid border-[rgb(52,52,68)] bg-[#F5F5DC] placeholder:bg-[#F5F5DC] placeholder:text-black focus:font-normal font-urbanist text-[black] placeholder:font-normal placeholder:text-[14px] font-normal"
                name="password"
                value={password}
                onChange={handlePassword}
              />
            </div>

            {error && (
              <div className="text-red-500 text-sm text-center mt-4">
                {error}
              </div>
            )}

            <div className="flex justify-between items-center mb-4">
              <div className="flex items-center">
                <input type="checkbox" className="mr-2" />
                <label className="text-sm font-urbanist text-[rgb(235,235,235)]">
                  Lembrar-me
                </label>
              </div>

              <a
                type="button"
                className="text-sm font-urbanist text-[white] hover:underline point"
                onClick={() => navigate("/recovery")}
              >
                Esqueci a senha
              </a>
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
      )}
    </div>
  );
}

export default Login;
