import { useState } from "react";
import api from "../FetchApi";

function ForgotPassword() {
  const [email, setEmail] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [showForm, setShowForm] = useState<boolean>(false);

  const handleForgotPassword = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    try {
      const response = await api.post("/forgot-password", { email });
      if (response.status === 200) {
        setMessage("Um link de redefinição foi enviado ao seu e-mail");
        setError("");
      }
      if(!response) {
        return <p>aguarde um momento...</p>
      }
    } catch (e) {
      console.error(e);
      setError("Não foi possível enviar o e-mail, tente novamente");
      setMessage("");
    }
  };

  

  return (
    <div>
      {!showForm && (
        <a className="text-[white] w-full font-urbanist xl:right-[0px] text-sm font-light" onClick={() => setShowForm(true)}>
  Esqueceu sua senha?
</a>      )}
      {showForm && (
        <div>
          <h1 className="text-[fuchsia]">Redefinir senha:</h1>
          <form onSubmit={handleForgotPassword}>
            <label className="text-[white]">E-mail:</label>

            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="Digite seu e-mail"
            ></input>

            <button type="submit">enviar</button>
          </form>
          {message && <p className="text-[green]">{message}</p>}
          {error && <p className="text-[red]">{error}</p>}
        </div>
      )}
    </div>
  );
}

export default ForgotPassword;
