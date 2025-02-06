import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { DecodedToken } from "./decoder/DecodedToken";

function Header() {
  const [clientName, setClientName] = useState<string | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false);
  console.log(clientName);

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        const decodedToken = jwtDecode<DecodedToken>(token);
        setClientName(decodedToken.name || decodedToken.sub);
        setIsAuthenticated(true);
      } catch (e) {
        console.error("Erro ao decodificar token:", e);
        setIsAuthenticated(false);
      }
    } else {
      setIsAuthenticated(false);
    }
  }, [isAuthenticated]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("name");
    localStorage.removeItem("clientId");
    navigate("/");
    setIsAuthenticated(false);
    setClientName(null);
  };

  return (
    <div className="flex flex-row w-auto h-100vw items-center justify-between top-0 px-[32px] bg-white shadow-sm border-b-[1px] border-[#FF00FF] border-opacity-10">
      <div className="flex items-center">
        {" "}
        {isAuthenticated ? (
          <>
            <Link to={"/"} className="no-underline">
              <span className="text-[fuchsia] ml-[2px] font-urbanist text-[37px] no-underline">
                Vestido Bank
              </span>
            </Link>

            <Link to={"/profile"}>
              <span className="text-[white] ml-[20px] font-urbanist text-[20px] no-underline">
                Meu perfil
              </span>
            </Link>
            <Link to={"/analise"}>
              <span className="text-[white] ml-[20px] font-urbanist text-[20px] no-underline">
                Análise da conta
              </span>
            </Link>
            <Link to={"/transferir"}>
              <span className="text-[white] ml-[20px] font-urbanist text-[20px] no-underline">
                Transferir
              </span>
            </Link>
            <Link to={"/deposito"}>
              <span className="text-[white] ml-[20px] font-urbanist text-[20px] no-underline ">
                Depósito
              </span>
            </Link>

            <button
              onClick={handleLogout}
              className="ml-[900px] flex items-center mr-[20px] rounded-full justify-center border-transparent border text-[22px] bg-transparent h-[30px] text-[white] hover:border-[#FF00FF] hover:bg-[#FF00FF] transition-all duration-300"
            >
              Sair
            </button>
          </>
        ) : (
          <>
            <Link to={"/"} className="no-underline">
              <span className="text-[fuchsia] ml-[2px] font-urbanist text-[37px]">
                Vestido Bank
              </span>
            </Link>

            <Link to={"/"} className="no-underline">
              <span className="text-[white] ml-[32px] font-urbanist text-[24px]">
                Página Inicial
              </span>
            </Link>

            <span className="text-[white] ml-[32px] font-urbanist text-[24px]">
              Para você
            </span>

            <span className="text-[white] ml-[32px] font-urbanist text-[24px]">
              Para seu negócio
            </span>

            <span className="text-[white] ml-[32px] font-urbanist text-[24px]">
              Atendimento
            </span>

            <div className="ml-[auto] flex items-center">
              {" "}
              <Link to={"/login"}>
                <button className="flex items-center mr-[20px] rounded-full justify-center border-transparent border text-[22px] bg-transparent h-[30px] text-[white] hover:border-[#FF00FF] hover:bg-[#FF00FF] transition-all duration-300">
                  Login
                </button>
              </Link>
              <button className="flex items-center mr-[20px] rounded-full justify-center border-transparent border text-[22px] bg-transparent h-[30px] text-[white] hover:border-[#FF00FF] hover:bg-[#FF00FF] transition-all duration-300">
                Abra sua conta
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default Header;
