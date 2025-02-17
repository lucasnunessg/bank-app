import { useEffect, useState, useMemo } from "react";
import Chart from "react-apexcharts";
import api from "../FetchApi";
import { useAuth } from "../contexts/useAuth";
import { ApexOptions } from "apexcharts";

export function AccountAnalysis() {
  const { clientId, token } = useAuth();
  const [saldoData, setSaldoData] = useState<number[]>([]);
  const [transferData, setTransferData] = useState<{ valor: number; date: string }[]>([]);
  const [messageError, setMessageError] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  interface Transfer {
    contaDestinoId: number;
    nomeDonoDestino: string;
    contaOrigemId: number;
    nomeDonoOrigem: string;
    valor: number;
    descricao: string;
    date: string;
  }

  useEffect(() => {
    if (!token || !clientId) {
      console.error("erro ao encontrar");
      return;
    }

    const fetchData = async () => {
      try {
        const [saldo, transfers] = await Promise.all([
          api.get(`/conta-poupanca/${clientId}/saldo`, {
            headers: { Authorization: `Bearer ${token}` },
          }),
          api.get(`/conta-poupanca/${clientId}/transfers/send`, {
            headers: { Authorization: `Bearer ${token}` },
          }),
        ]);

        const saldoData = Object.values(saldo.data) as number[];
        const transferDataFormatted = transfers.data.map((item: Transfer) => ({
          valor: item.valor,
          date: item.date.split('/').reverse().join('-'), // Formato yyyy-mm-dd
        }));

        setSaldoData(saldoData);
        setTransferData(transferDataFormatted);
        setIsLoading(false);
      } catch (e) {
        console.error(e);
        setMessageError("Não foi possível acessar os dados");
        setIsLoading(false);
      }
    };

    fetchData();
  }, [clientId, token]);

  const series = useMemo(() => {
    if (saldoData.length === 0 || transferData.length === 0) return [];

    const saldoAcumulado: number[] = [];
    let saldoAtual = saldoData[0]; 

    transferData.forEach((transfer) => {
      saldoAcumulado.push(saldoAtual); 
      saldoAtual -= transfer.valor; 
    });

    saldoAcumulado.push(saldoAtual);

    return [
      {
        name: "Saldo atual",
        data: saldoAcumulado,
        color: "#00E396",
      },
      {
        name: "Transferências",
        data: transferData.map((transfer) => transfer.valor),
        color: "#FF4560",
      },
    ];
  }, [saldoData, transferData]);

  const options: ApexOptions = useMemo(() => ({
    chart: {
      type: "bar",
      height: 350,
    },
    plotOptions: {
      bar: {
        horizontal: false,
        columnWidth: "55%",
      },
    },
    dataLabels: {
      enabled: false,
    },
    stroke: {
      show: true,
      width: 2,
      colors: ["transparent"],
    },
    xaxis: {
      categories: transferData.map((item) => item.date), 
    },
    yaxis: {
      title: {
        text: "Valores",
      },
    },
    fill: {
      opacity: 1,
    },
    tooltip: {
      y: {
        formatter: (val) => `R$ ${val}`,
      },
    },
  }), [transferData]);

  return (
    <div>
      {isLoading && <p>Carregando dados...</p>}
      <Chart options={options} series={series} type="bar" height={350} />
      {messageError && <p className="text-[red]">{messageError}</p>}
    </div>
  );
}