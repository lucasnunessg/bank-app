import { Routes, Route } from 'react-router-dom'; 
import "./App.css";
import Header from "./Components/Header";
import Home from "./Components/pages/Home";

function App() {
  return (
    <div className="flex flex-col min-h-screen">
      <Header /> 
      <Routes>
        <Route path="/" element={<Home />} /> 
       
      </Routes>
    </div>
  );
}

export default App;