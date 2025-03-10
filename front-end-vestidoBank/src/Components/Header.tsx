import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { useAuth } from "../Components/contexts/useAuth";

function Header() {
  const { token, logout } = useAuth();
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(!!token);
  const [isMobile, setIsMobile] = useState<boolean>(window.innerWidth <= 768);
  const [isMenuOpen, setIsMenuOpen] = useState(false); 

  const navigate = useNavigate();

  useEffect(() => {
    setIsAuthenticated(!!token);
  }, [token]);

  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth <= 768);
    };

    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  const handleLogout = () => {
    logout();
    navigate("/");
  };

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen); 
  };

  return (
    <div className="border-b border-[purple] flex flex-row justify-between items-center p-4 relative">
      {isAuthenticated ? (
        <>
          <Link to={"/home/auth/client"} className="no-underline">
            <span className="text-[purple]">Vestido Bank</span>
          </Link>

          <div className="flex items-center gap-4">
            {isMobile ? (
              <>
                <img
                  src="/hambergermenu.png"
                  alt="Menu"
                  className="w-8 h-8 cursor-pointer"
                  onClick={toggleMenu} 
                />
                {isMenuOpen && (
                  <div className="absolute top-full left-0 right-0 bg-black p-4 flex flex-col gap-2 z-10">
                    <Link to={"/profile"} onClick={() => setIsMenuOpen(false)}>
                      <span className="text-[white]">Meu perfil</span>
                    </Link>
                    <Link to={"/analise"} onClick={() => setIsMenuOpen(false)}>
                      <span className="text-[white]">Análise da conta</span>
                    </Link>
                    <Link to={"/transferir"} onClick={() => setIsMenuOpen(false)}>
                      <span className="text-[white]">Transferir</span>
                    </Link>
                    <Link to={"/deposito"} onClick={() => setIsMenuOpen(false)}>
                      <span className="text-[white]">Depósito</span>
                    </Link>
                  </div>
                )}
              </>
            ) : (
              <div className="flex gap-[30px] flex-1 justify-between">
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
            )}

            <button
              onClick={handleLogout}
              className="bg-transparent text-[white] hover:bg-[#FF00FF] p-2"
            >
              Sair
            </button>
          </div>
        </>
      ) : (
        <>
          <div className="flex items-center gap-[10px]">
            <Link to={"/"} className="no-underline">
              <span className="text-[purple]">Vestido Bank</span>
            </Link>

            {!isMobile && <span className="text-[white]">Atendimento</span>}
          </div>

          <div className="flex items-end gap-4">
            <Link to={"/login"}>
              <button className="bg-transparent text-[white] hover:bg-[#FF00FF] p-2">
                Login
              </button>
            </Link>
            <Link to={"/create-account"}>
              <button className="bg-transparent text-[white] hover:bg-[#FF00FF] p-2">
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