import { Routes, Route } from "react-router-dom";
import "./App.css";
import Header from "./Components/Header";
import Home from "./Components/pages/Home";
import Login from "./Components/pages/Login";
import HomeAuthClient from "./Components/pages/HomeAuthClient";
import Transf from "./Components/pages/Transfer";
import { AuthProvider } from "./Components/provider/AuthProvider";
import { Deposito } from "./Components/pages/Deposito";
import { CreateAccount } from "./Components/pages/CreateAccount";
import { ResetPassword } from "./Components/pages/ResetPassword";
import { AccountAnalysis } from "./Components/pages/AccountAnalysis";
import { Profile } from "./Components/pages/Profile";
import { PageFatura } from "./Components/pages/PageFatura";
import { Saque } from "./Components/pages/Saque";

function App() {
  return (
    <AuthProvider>
<div className="flex flex-col h-full min-h-screen bg-[#14141F]">        <Header />
<div className="flex-grow overflow-y-auto">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/home/auth/client" element={<HomeAuthClient />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/transferir" element={<Transf />} />
            <Route path="/deposito" element={<Deposito />} />
            <Route path="/create-account" element={<CreateAccount />} />
            <Route path="/reset-password" element={<ResetPassword />} />
            <Route path="/analise" element={< AccountAnalysis />} />
            <Route path="/fatura-details" element={<PageFatura />} />
            <Route path="/sake-money" element={<Saque />} />

          </Routes>
        </div>
      </div>
    </AuthProvider>
  );
}

export default App;
