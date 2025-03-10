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
    <div>
      <p className="text-[17px] sm:text-[32px] text-[white] font-urbanist">
        Seja bem-vindo! {name}
      </p>

      {loading ? (
          <h1 className="text-[white]">Carregando...</h1>
        ) : (
          showSaldo && (
            <p className="text-[white] font-urbanist">
              Saldo atual R$: {saldo}
            </p>
          )
        )}
      <div className="flex justify-between p-[30px]">
        <Link
          className="no-underline text-[white] flex flex-col"
          to={"/transferir"}
        >
          <img
            src="/transmitsqaure2.png"
            alt="Menu"
            className="w-[36px] h-[36px] cursor-pointer"
          />
          Transf.
        </Link>
        <Link
          className="no-underline text-[white] flex flex-col "
          to={"/fatura-details"}
        >
          <img
            src="/documenttext1.png"
            alt="Menu"
            className="w-[36px] h-[36px] cursor-pointer"
          />
          Extrato
        </Link>
      </div>

      {error && (
        <div className="text-red-500 text-sm text-center mt-4">{error}</div>
      )}

      <div className="flex flex-col items-end mr-[20px]">
        <a
          onClick={handleShowSaldo}
          className="bg-blue-500 text-[white] p-2 rounded cursor-pointer"
        >
          {" "}
          {showSaldo ? "Esconder saldo" : "Mostrar saldo"}
          <br></br>
          {showSaldo ? <EyeOff className="ml-2" /> : <Eye className="ml-2" />}
        </a>

             </div>

      <div>
        <Rendimento />
        <Fatura />

        <Link to={"/fatura-details"} className="no-underline">
          <span className="text-[white]">ver detalhes da fatura</span>
        </Link>
      </div>
      <div className="absolute  overflow-y-auto custom-scrollbar">
       
      </div>
      <p className="text-[white]">análise de sua conta:</p>
      <div className="w-[333px] h-[100px] ">
        <AccountAnalysis />
      </div>
    </div>
  );
}

export default HomeAuthClient;
