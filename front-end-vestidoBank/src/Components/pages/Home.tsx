import { useState, useEffect, useCallback } from "react";

function Home() {
  const cards = [
    {
      icon: "🔒",
      text: "Segurança para sua conta",
    },
    {
      icon: "📊",
      text: "Gráfico de análise de sua conta",
    },
    {
      icon: "💸",
      text: "Depósito e saque imediato sem taxas",
    },
    {
      icon: "🏦",
      text: "Conta depósito e corrente",
    },
    {
      icon: "💳",
      text: "Cartão de crédito sem anuidade",
    },
    {
      icon: "📱",
      text: "App moderno e intuitivo",
    },
    {
      icon: "🌍",
      text: "Atendimento global 24/7",
    },
    {
      icon: "💼",
      text: "Investimentos com retorno garantido",
    },
    {
      icon: "🎁",
      text: "Benefícios exclusivos para clientes",
    },
    {
      icon: "📈",
      text: "Consultoria financeira personalizada",
    },
    {
      icon: "💡",
      text: "Soluções inovadoras para seu dinheiro",
    },
    {
      icon: "🛡️",
      text: "Proteção contra fraudes",
    },
  ];

  const [currentCard, setCurrentCard] = useState(0);

  // Wrap nextCard in useCallback to memoize it
  const nextCard = useCallback(() => {
    setCurrentCard((prev) => (prev + 1) % cards.length);
  }, [cards.length]);

  // Wrap prevCard in useCallback to memoize it

  // Automatic slide every 5 seconds
  useEffect(() => {
    const interval = setInterval(nextCard, 5000);
    return () => clearInterval(interval);
  }, [nextCard]);

  return (
    <div className="min-h-screen h-screen bg-[#14141F] text-[white] font-sans flex flex-col items-center justify-center overflow-hidden">
      <header className="text-center mt-[-250px]">
        <h1 className="text-4xl font-bold mb-4 text-[white]">Escolha o banco VestidoBank</h1>
        <p className="text-lg text-gray-400">Sua segurança e confiança são nossa prioridade.</p>
      </header>

      <div className="relative w-full max-w-4xl">
        <div className="p-12 bg-white/10 backdrop-blur-md rounded-lg shadow-lg transition-all duration-500 flex justify-center items-center">
          <div className="flex flex-col items-center space-y-4">
            <span className="text-6xl text-[44px]">{cards[currentCard].icon}</span>
            <span className="text-2xl text-[44px]">{cards[currentCard].text}</span>
          </div>
        </div>


      </div>

      <div className="text-center mt-12">

      </div>
    </div>
  );
}

export default Home;
