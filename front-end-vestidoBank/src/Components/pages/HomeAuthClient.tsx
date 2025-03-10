import { useEffect, useState } from "react";
import api from "../FetchApi";
import { Link } from "react-router-dom";
import { useAuth } from "../contexts/useAuth";
import { Eye, EyeOff } from "lucide-react";
import { Rendimento } from "./Rendimento";
import { Fatura } from "./FaturaAtual";
import { AccountAnalysis } from "./AccountAnalysis";

function HomeAuthClient() {
  const [saldo, setSaldo] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [showSaldo, setShowSaldo] = useState(false);
  const { clientId, name, token } = useAuth();

  useEffect(() => {
    const fetchSaldo = async () => {
      if (clientId !== null) {
        setLoading(true);
        try {
          const response = await api.get(`/conta-poupanca/${clientId}/saldo`, {
            headers: { Authorization: `Bearer ${token}` },
          });

          if (response.status === 200) {
            setSaldo(response.data.saldo);
            setError("");
          }
        } catch (error) {
          console.error("Erro ao buscar saldo:", error);
          setError("Não foi possível buscar saldo!");
        } finally {
          setLoading(false);
        }
      }
    };

    fetchSaldo();
  }, [clientId, token]);

  const handleShowSaldo = () => {
    setShowSaldo((prev) => !prev);
  };

  return (
    <div className="flex flex-col items-center p-4">
      {/* Mensagem de boas-vindas */}
      <p className="text-[20px] sm:text-[32px] text-[white] font-urbanist text-center mb-4">
        Seja bem-vindo! {name}
      </p>

      {/* Carregamento ou exibição do saldo */}
      {loading ? (
        <h1 className="text-[white] text-[18px] sm:text-[24px] text-center">
          Carregando...
        </h1>
      ) : (
        showSaldo && (
          <p className="text-[white] text-[18px] sm:text-[24px] font-urbanist text-center mb-4">
            Saldo atual R$: {saldo}
          </p>
        )
      )}

      {/* Botão "Mostrar saldo" */}
      <div className="flex justify-center w-full mb-6">
        <a
          onClick={handleShowSaldo}
          className="bg-blue-500 text-[white] text-[14px] sm:text-[16px] p-2 rounded cursor-pointer flex items-center"
        >
          {showSaldo ? "Esconder saldo" : "Mostrar saldo"}
          {showSaldo ? <EyeOff className="ml-2 h-[16px] w-[18px]" /> : <Eye className="ml-2 h-[16px] w-[18px]" />}
        </a>
      </div>

      {/* Links de Transferência e Extrato */}
      <div className="flex justify-around w-full max-w-[400px] mb-6">
        <Link
          className="no-underline text-[white] flex flex-col items-center"
          to={"/transferir"}
        >
          <img
            src="/transmitsqaure2.png"
            alt="Menu"
            className="w-[36px] h-[36px] cursor-pointer mb-2"
          />
          <span className="text-[14px] sm:text-[16px]">Transf.</span>
        </Link>

        <Link
          className="no-underline text-[white] flex flex-col items-center"
          to={"/deposito"}
        >
          <img
            src="/cardadd.png"
            alt="Menu"
            className="w-[36px] h-[36px] cursor-pointer mb-2"
          />
          <span className="text-[14px] sm:text-[16px]">Depositar.</span>
        </Link>

        <Link
          className="no-underline text-[white] flex flex-col items-center"
          to={"/fatura-details"}
        >
          <img
            src="/remove-money.png"
            alt="Menu"
            className="w-[36px] h-[36px] cursor-pointer mb-2"
          />
          <span className="text-[14px] sm:text-[16px]">Sacar</span>
        </Link>

        <Link
          className="no-underline text-[white] flex flex-col items-center"
          to={"/fatura-details"}
        >
          <img
            src="/documenttext1.png"
            alt="Menu"
            className="w-[36px] h-[36px] cursor-pointer mb-2"
          />
          <span className="text-[14px] sm:text-[16px]">Extrato</span>
        </Link>
      </div>

      {/* Mensagem de erro */}
      {error && (
        <div className="text-red-500 text-[14px] sm:text-[16px] text-center mb-4">
          {error}
        </div>
      )}

      {/* Componentes Rendimento e Fatura */}
      <div className="w-full max-w-[600px] mb-6">
        <Rendimento />
        <Fatura />
      </div>

      {/* Link para detalhes da fatura */}
      <div className="text-center mb-6">
        <Link to={"/fatura-details"} className="no-underline">
          <span className="text-[white] text-[14px] sm:text-[16px] hover:text-[#FF00FF] hover:underline transition-all duration-300">
            Ver detalhes da fatura
          </span>
        </Link>
      </div>

      {/* Análise da conta */}
      <div className="w-full max-w-[600px] mb-6">
        <p className="text-[white] text-[18px] sm:text-[24px] text-center mb-4">
          Análise de sua conta:
        </p>
        <div className="w-full h-[200px] sm:h-[300px]">
          <AccountAnalysis />
        </div>
      </div>
    </div>
  );
}

export default HomeAuthClient;
