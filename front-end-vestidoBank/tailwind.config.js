/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './index.html',
    './src/**/**/*.{js,ts,jsx,tsx}', 
  ],
  theme: {
    extend: {
      colors: {
        // Cores principais do código
        primary: "#14141F", // Cor de fundo principal
        secondary: "#8A8AA0", // Cor secundária (textos e bordas)
        sidebar: "rgba(255, 255, 255, 1)",
        contentDashboard: "rgba(245, 247, 250, 1)",

        // Cores de texto
        textPrimary: "#FFFFFF", // Cor de texto principal (branco)
        textSecondary: "#8A8AA0", // Cor de texto secundário (cinza)
        textSideBar: "rgba(137, 137, 137, 1)",
        textSidebarOnClick: "rgba(100, 0, 170, 1)", // Cor de texto da sidebar
        textNameSide: "rgba(0, 0, 0, 1)",
        textDivSidebar: "rgba(35, 35, 35, 1)",
        textDashboardActive: "rgba(150, 0, 255, 1)" ,


        // Cores de fundo
        background: "#14141F", // Cor de fundo geral
        foreground: "#8A8AA0", // Cor de texto e detalhes

        // Cores de interação
        accent: "#C133FF", // Cor de destaque para botões ou links
      },
      fontFamily: {
        urbanist: ['Urbanist', 'sans-serif', 'Inter'], 
        inter: ['Inter', 'sans-serif'],
      },
    },
  },
  plugins: [],
} 