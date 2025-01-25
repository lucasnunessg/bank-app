function Header() {
  return (
    <div className="flex flex-row w-full h-full items-center justify-between top-0 px-[32px] bg-white shadow-sm ">
      <div className="flex items-center">
        <a className="text-[fuchsia] ml-[2px] font-urbanist text-[37px]">
          Vestido Bank
        </a>

        <a className="text-[black] ml-[32px] font-urbanist text-[37px]">
          Página Inicial
        </a>

        <a className="text-[black] ml-[32px] font-urbanist text-[37px]">
          Para você
        </a>

        <a className="text-[black] ml-[32px] font-urbanist text-[37px]">
          Para seu negócio
        </a>

        <a className="text-[black] ml-[32px] font-urbanist text-[37px]">
          Contato
        </a>
      </div>

      <div>
        <button className="flex items-center mr-[16px] justify-center border-transparent border text-[22px] bg-transparent h-[30px] text-black hover:border-[#FF00FF] hover:bg-[#FF00FF] transition-all duration-300">
          Login
        </button>
      </div>
    </div>
  );
}

export default Header;