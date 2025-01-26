import { Route } from 'react-router-dom';

import "./App.css";
import Header from "./Components/Header";
import Home from "./Components/pages/Home";

function App() {
  return (
    <>
    <div className="flex flex-col min-h-screen min-w-[1820px]">
    <Route path="/" element={<Home />} />
    <Header />
      
    </div>
    </>
  );
}

export default App;
