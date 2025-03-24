
import React, { useEffect, useState } from "react";
import Highcharts from "highcharts";
import HighchartsReact from "highcharts-react-official";
import axios from "axios";
import AdminHeaders from "../headers/AdminHeaders";
import { useNavigate } from "react-router-dom"; // Importe o useNavigate

const RelatorioStatusChamados = () => {

    const navigate = useNavigate(); // Hook para redirecionamento

  const [chartData, setChartData] = useState({
    emAndamento: 0,
    emFaseDeTeste: 0,
    resolvido: 0,
    analise: 0
  });

{/*
React.useEffect(() => {
  axios
    .get("http://localhost:8080/chamados/relatorio/statusDosChamados") // URL correta da API
    .then((response) => {
      const data = response.data; // Obtenha os dados diretamente
      setChartData({
        emAndamento: data.EmAndamento || 0,
        emFaseDeTeste: data.EmFaseDeTeste || 0,
        resolvido: data.Resolvido || 0,
        EmAnalise: data.EmAnalise || 0,
      });
    })
    .catch((error) => console.error("Erro ao carregar os dados:", error));
}, []);
 */}

   // Verifica se o usuário está autenticado ao carregar o componente
     useEffect(() => {
         const token = localStorage.getItem("token"); // Recupera o token do localStorage
         if (!token) {
             navigate("/nao-autorizado"); // Redireciona para a página de não autorizado
         } else {
             // Faz a requisição para carregar os dados do relatório
             axios
                 .get("http://localhost:8080/chamados/relatorio/statusDosChamados", {
                     headers: {
                         Authorization: `Bearer ${token}`, // Envia o token no cabeçalho
                     },
                 })
                 .then((response) => {
                     const data = response.data; // Obtenha os dados diretamente
                     setChartData({
                         emAndamento: data.EmAndamento || 0,
                         emFaseDeTeste: data.EmFaseDeTeste || 0,
                         resolvido: data.Resolvido || 0,
                         EmAnalise: data.EmAnalise || 0,
                     });
                 })
                 .catch((error) => {
                     if (error.response && (error.response.status === 403 || error.response.status === 401)) {
                         navigate("/nao-autorizado"); // Redireciona para a página de não autorizado
                     } else {
                         console.error("Erro ao carregar os dados:", error);
                     }
                 });
         }
     }, [navigate]);



  const options = {
    chart: {
      type: "pie",
    },
    title: {
      text: "Relatório dos Status de todos os Chamados",
    },
    tooltip: {
      valueSuffix: "%",
    },
    plotOptions: {
      pie: { // Adicione "pie" aqui para configurar gráficos do tipo pizza
        allowPointSelect: true,
        cursor: "pointer",
        dataLabels: {
          enabled: true,
          format: "{point.name}", // Exibe apenas o nome da categoria
          style: {
            fontSize: "1.2em",
            textOutline: "none",
          },
        },
      },
    },



    series: [
      {
        name: "Porcentagem",
        colorByPoint: true,
        data: [
          {
            name: "Chamados em Andamento",
            y: chartData.emAndamento,
          },
          {
            name: "Chamados em Fase de Teste",
            sliced: true,
            selected: true,
            y: chartData.emFaseDeTeste,
          },
          {
            name: "Chamados Resolvidos",
            y: chartData.resolvido,
          },

           {
              name: "Chamados em Análise",
              y: chartData.EmAnalise,
           },

        ],
      },
    ],
  };

  return (
    <div>
        <AdminHeaders />
        <h2 className="alert alert-info text-center" role="alert">Relatório dos Status de todos os chamados</h2>



      {/* Highcharts */}
      <div style={{ height: "400px" }}>
        <HighchartsReact highcharts={Highcharts} options={options} />
      </div>
    </div>
  );
};

export default RelatorioStatusChamados;

