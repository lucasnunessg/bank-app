import { Routes, Route } from "react-router-dom";
import "./App.css";
import Header from "./Components/Header";
import Home from "./Components/pages/Home";
import Login from "./Components/pages/Login";
import HomeAuthClient from "./Components/pages/HomeAuthClient";
import Transf from "./Components/Transfer";
import { AuthProvider } from "./Components/AuthProvider";

function App() {
  return (
    <AuthProvider>
      <div className="flex flex-col min-h-screen bg-[#14141F]">
        <Header />
        <div className="flex-grow">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/home/auth/client" element={<HomeAuthClient />} />
            <Route path="/transferir" element={<Transf />} />
          </Routes>
        </div>
      </div>
    </AuthProvider>
  );
}

export default App;
