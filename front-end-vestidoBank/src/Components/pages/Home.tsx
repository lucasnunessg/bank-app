function Home() {
  return (
    <div className="min-h-screen h-screen background-[whitesmoke] from-blue-900 to-blue-700 text-white font-sans">
      <div>
        <header className="text-center pt-10">
          <h1 className="text-4xl font-bold intense-glow">
            Escolha o banco VestidoBank
          </h1>
          <p className="mt-2 text-lg text-blue-200 text-[22px]">
            Sua segurança e confiança são nossa prioridade.
          </p>
        </header>

        <div className="mt-12 p-6 bg-white/10 backdrop-blur-md rounded-lg shadow-lg text-[22px]">
          <ul className="space-y-4">
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">🔒</span>
              <span className="text-lg">Segurança para sua conta</span>
            </li>
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">📊</span>
              <span className="text-lg">Gráfico de análise de sua conta</span>
            </li>
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">💸</span>
              <span className="text-lg">
                Depósito e saque imediato sem taxas
              </span>
            </li>
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">🏦</span>
              <span className="text-lg">Conta depósito e corrente</span>
            </li>
          </ul>
        </div>

        <div className="mt-8 p-6 bg-white/10 backdrop-blur-md rounded-lg shadow-lg text-[22px]">
          <ul className="space-y-4">
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">💳</span>
              <span className="text-lg">Cartão de crédito sem anuidade</span>
            </li>
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">📱</span>
              <span className="text-lg">App moderno e intuitivo</span>
            </li>
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">🌍</span>
              <span className="text-lg">Atendimento global 24/7</span>
            </li>
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">💼</span>
              <span className="text-lg">
                Investimentos com retorno garantido
              </span>
            </li>
          </ul>
        </div>

        <div className="mt-8 p-6 bg-white/10 backdrop-blur-md rounded-lg shadow-lg text-[22px]">
          <ul className="space-y-4">
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">🎁</span>
              <span className="text-lg">
                Benefícios exclusivos para clientes
              </span>
            </li>
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">📈</span>
              <span className="text-lg">
                Consultoria financeira personalizada
              </span>
            </li>
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">💡</span>
              <span className="text-lg">
                Soluções inovadoras para seu dinheiro
              </span>
            </li>
            <li className="flex items-center space-x-4">
              <span className="text-2xl text-blue-300">🛡️</span>
              <span className="text-lg">Proteção contra fraudes</span>
            </li>
          </ul>
        </div>

        <div className="mt-8 p-6 bg-white/10 backdrop-blur-md rounded-lg shadow-lg text-[22px]">
          <h2 className="text-2xl font-bold mb-4">
            Por que escolher o VestidoBank?
          </h2>
          <p className="text-lg">
            No VestidoBank, estamos comprometidos em oferecer a melhor
            experiência bancária para você. Com tecnologia de ponta, segurança
            avançada e uma equipe dedicada, garantimos que seu dinheiro esteja
            sempre seguro e rendendo. Nossas soluções são projetadas para
            atender às suas necessidades, seja para investimentos, transações
            diárias ou planejamento financeiro. Junte-se a nós e descubra um
            novo padrão de excelência em serviços bancários.
          </p>
        </div>

        <div className="text-center mt-12">
          <a className="bg-blue-500 hover:bg-blue-600 text-white font-semibold py-3 px-8 rounded-full shadow-lg transition-all transform hover:scale-105 cursor-pointer">
            Abra sua conta com a Vestido Bank
          </a>
        </div>
      </div>


    </div>
  );
}

export default Home;
