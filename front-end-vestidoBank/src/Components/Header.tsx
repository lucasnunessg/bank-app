import { Link } from "react-router-dom";

function Header() {
  return (
    <div className="flex flex-row w-full h-full background-[whitesmoke] items-center justify-between top-0 px-[32px] bg-white shadow-sm border-b-[1px] border-[#FF00FF] border-opacity-10">
      <div className="flex items-center">
      <Link to={"/"} className="no-underline">

        <a className="text-[fuchsia] ml-[2px] font-urbanist text-[37px]">
          Vestido Bank
        </a>
        </Link>

        <Link to={"/"} className="no-underline">
          <a className="text-[black] ml-[32px] font-urbanist text-[37px]">
            Página Inicial
          </a>
        </Link>

        <a className="text-[black] ml-[32px] font-urbanist text-[37px]">
          Para você
        </a>

        <a className="text-[black] ml-[32px] font-urbanist text-[37px]">
          Para seu negócio
        </a>

        <a className="text-[black] ml-[32px] font-urbanist text-[37px]">
          Atendimento
        </a>
      </div>

      <div>
        <button className="flex items-center mr-[20px] mb-[2px] rounded-full justify-center border-transparent border text-[22px] bg-transparent h-[30px] text-black hover:border-[#FF00FF] hover:bg-[#FF00FF] transition-all duration-300">
          Login
        </button>

        <button className="flex items-center mr-[20px] rounded-full justify-center border-transparent border text-[22px] bg-transparent h-[30px] text-black hover:border-[#FF00FF] hover:bg-[#FF00FF] transition-all duration-300">
          Abra sua conta
        </button>
      </div>
    </div>
  );
}

export default Header;
