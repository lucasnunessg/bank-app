import { useEffect, useState } from "react";
import Chart from "react-apexcharts";
import api from "../FetchApi";
import { useAuth } from "../contexts/useAuth";
import { ApexOptions } from "apexcharts";

export function AccountAnalysis() {
  const { clientId, token } = useAuth();
  const contaPoupancaId = clientId;
  const [depositData, setDepositData] = useState<number[]>([]);
  const [transferData, setTransferData] = useState<number[]>([]);
  const [messageError, setMessageError] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {

        const headers = {
          Authorization: `Bearer ${token}`
        };


        const [deposit, transfers] = await Promise.all([
          api.get(`/conta-poupanca/${clientId}/saldo`, {headers}),
          api.get(`/conta-poupanca/${contaPoupancaId}/client/${clientId}/deposit`, {headers})
        ]);
        setDepositData(deposit.data);
        setTransferData(transfers.data);
        setIsLoading(false);

      } catch (e) {
        console.error(e);
        setMessageError("Não foi possível acessar os dados");
        setIsLoading(false);
      }
    };
    fetchData();
  }, [clientId, contaPoupancaId, token]);

  const series = [
    {
      name: "Depósitos",
      data: depositData,
      color: "#00E396" // Verde para saldos positivos
    },
    {
      name: "Transferências",
      data: transferData,
      color: "#FF4560" // Vermelho para saldos negativos
    }
  ];

  const options: ApexOptions = {
    chart: {
      type: "bar", // Tipo de gráfico explícito
      height: 350
    },
    plotOptions: {
      bar: {
        horizontal: false,
        columnWidth: "55%"
      }
    },
    dataLabels: {
      enabled: false
    },
    stroke: {
      show: true,
      width: 2,
      colors: ["transparent"]
    },
    xaxis: {
      categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep"]
    },
    yaxis: {
      title: {
        text: "Valores"
      }
    },
    fill: {
      opacity: 1
    },
    tooltip: {
      y: {
        formatter: (val) => `R$ ${val}`
      }
    }
  };

  return (
    <div>
      {isLoading && <p>Carregando dados...</p>}
      <Chart options={options} series={series} type="bar" height={350} />
      {messageError && <p>{messageError}</p>}
    </div>
  );
}
