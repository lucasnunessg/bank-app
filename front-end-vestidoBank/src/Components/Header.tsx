import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { useAuth } from "../Components/contexts/useAuth";



function Header() {
  const { token, logout } = useAuth();
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(!!token);
  
  const navigate = useNavigate();

  useEffect(() => {  
    setIsAuthenticated(!!token);
  }, [token])

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <div className="flex flex-row w-full items-center justify-between top-0 bg-white shadow-sm border-b-[1px] border-[#FF00FF] border-opacity-10">
      <div className="flex items-center justify-between w-full max-w-[1200px] mx-auto px-4">
        {isAuthenticated ? (
          <>
            {/* Parte do header quando o usuário está logado */}
            <Link to={"/"} className="no-underline">
              <span className="text-[fuchsia] ml-[2px] font-urbanist text-[37px] no-underline">
                Vestido Bank
              </span>
            </Link>

            <div className="flex items-center space-x-8">
              <Link to={"/profile"}>
                <span className="text-[white] font-urbanist text-[20px] no-underline">
                  Meu perfil
                </span>
              </Link>
              <Link to={"/analise"}>
                <span className="text-[white] font-urbanist text-[20px] no-underline">
                  Análise da conta
                </span>
              </Link>
              <Link to={"/transferir"}>
                <span className="text-[white] font-urbanist text-[20px] no-underline">
                  Transferir
                </span>
              </Link>
              <Link to={"/deposito"}>
                <span className="text-[white] font-urbanist text-[20px] no-underline">
                  Depósito
                </span>
              </Link>
            </div>

            <button
              onClick={handleLogout}
              className="flex items-center rounded-full justify-center border-transparent border text-[22px] bg-transparent h-[30px] text-[white] hover:border-[#FF00FF] hover:bg-[#FF00FF] transition-all duration-300"
            >
              Sair
            </button>
          </>
        ) : (
          <>
            <div className="w-full h-[50px] flex flex-row sm:flex-row justify-between items-center gap-4 sm:gap-8">
              <Link to={"/"} className="no-underline">
                <span className="text-[fuchsia] font-urbanist text-2xl sm:text-[40px]">
                  Vestido Bank
                </span>
              </Link>

          
            
                <span className="text-[white] font-urbanist text-sm sm:text-base">
                  Atendimento
                </span>
              </div>

              <div className="flex flex-row sm:flex-row items-center gap-4 sm:gap-8">
                <Link to={"/login"}>
                  <button className="border-transparent border bg-transparent h-[30px] text-[white] hover:border-[#FF00FF] hover:bg-[#FF00FF] transition-all duration-300 text-sm sm:text-base">
                    Login
                  </button>
                </Link>
                <Link to={"/create-account"}>
                  <button className="border-transparent border bg-transparent h-[30px] text-[white] hover:border-[#FF00FF] hover:bg-[#FF00FF] transition-all duration-300 text-sm sm:text-base">
                    Cadastrar
                  </button>
                </Link>
              </div>
            
          </>
        )}
      </div>
    </div>
  );
}

export default Header;