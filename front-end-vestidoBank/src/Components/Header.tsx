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
  }, [token]);

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  return (
    <div className="border-b border-[fuchsia] flex flex-row justify-between items-center">
     
        {isAuthenticated ? (
          <>
            <Link to={"/"} className="no-underline">
              <span className="text-[fuchsia]">Vestido Bank</span>
            </Link>

            <div>
              <Link to={"/profile"}>
                <span className="text-[white]">Meu perfil</span>
              </Link>
              <Link to={"/analise"}>
                <span className="text-[white]">Análise da conta</span>
              </Link>
              <Link to={"/transferir"}>
                <span className="text-[white]">Transferir</span>
              </Link>
              <Link to={"/deposito"}>
                <span className="text-[white]">Depósito</span>
              </Link>
            </div>

            <button
              onClick={handleLogout}
              className="bg-transparent text-[white] hover:bg-[#FF00FF]"
            >
              Sair
            </button>
          </>
        ) : (
          <>
          <div className="flex items-center gap-[10px] ">
          <Link to={"/"} className="no-underline">
              <span className="text-[fuchsia]">Vestido Bank</span>
            </Link>

            <span className="text-[white]">Atendimento</span>
          </div>
           
          <div className="flex items-end">
          <Link to={"/login"}>
              <button className="bg-transparent text-[white] hover:bg-[#FF00FF]">
                Login
              </button>
            </Link>
            <Link to={"/create-account"}>
              <button className="bg-transparent text-[white] hover:bg-[#FF00FF]">
                Cadastrar
              </button>
            </Link>
          </div>
            
          </>
        )}
     
    </div>
  );
}

export default Header;
