import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import api from "../FetchApi";

interface DecodedToken {
  sub: string;
  name?: string; // Adicione isso se o token contiver o nome do usuário
}

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [welcomeMessage, setWelcomeMessage] = useState<string | null>(null);
  const navigate = useNavigate();

  // Carrega o nome do cliente do localStorage quando o componente é montado
  useEffect(() => {
    const storedClientName = localStorage.getItem("name");
    if (storedClientName) {
      setWelcomeMessage(storedClientName);
    }
  }, []);

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
    <div>
      <h1>Login</h1>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <div>
        <label htmlFor="email">Email:</label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={handleEmail}
        />
      </div>
      <div>
        <label htmlFor="password">Senha:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={handlePassword}
        />
      </div>
      {welcomeMessage && (
        <h3 className="welcome-message-text">
          Seja bem-vindo(a), {welcomeMessage}!
        </h3>
      )}
      <button onClick={handleLogin}>Entrar</button>
    </div>
  );
}

export default Login;