package service;

import dao.*;
import model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
  Serviço para geração de relatórios gerenciais.
 */
public class RelatorioService {
    
    private final VooDAO vooDAO;
    private final TicketDAO ticketDAO;
    private final PassageiroDAO passageiroDAO;
    private final AeroportoDAO aeroportoDAO;
    private final CompanhiaAereaDAO companhiaDAO;
    private final AssentoDAO assentoDAO;

    public RelatorioService() {
        this.vooDAO = new VooDAO();
        this.ticketDAO = new TicketDAO();
        this.passageiroDAO = new PassageiroDAO();
        this.aeroportoDAO = new AeroportoDAO();
        this.companhiaDAO = new CompanhiaAereaDAO();
        this.assentoDAO = new AssentoDAO();
    }

    /*
      Relatório: Passageiros que saíram de um aeroporto em um período.
     */
    public String relatorioPassageirosSaida(int aeroportoId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        Aeroporto aeroporto = aeroportoDAO.readById(aeroportoId);
        if (aeroporto == null) return "Aeroporto não encontrado.";

        List<Voo> voos = vooDAO.buscarPorOrigem(aeroportoId, dataInicio, dataFim);
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO: PASSAGEIROS QUE SAÍRAM ===\n");
        relatorio.append(String.format("Aeroporto: %s (%s)\n", aeroporto.getNome(), aeroporto.getAbreviacao()));
        relatorio.append(String.format("Período: %s a %s\n\n", dataInicio, dataFim));

        int totalPassageiros = 0;
        for (Voo voo : voos) {
            List<Assento> assentosOcupados = new ArrayList<>();
            for (Assento assento : assentoDAO.findByVoo(voo.getId())) {
                if (assento.getPassageiroId() != null) {
                    assentosOcupados.add(assento);
                }
            }

            if (!assentosOcupados.isEmpty()) {
                Aeroporto destino = aeroportoDAO.readById(voo.getDestinoId());
                relatorio.append(String.format("Voo #%d para %s (%s) - %d passageiros\n",
                    voo.getId(), destino.getAbreviacao(), voo.getDataHora(), assentosOcupados.size()));
                
                for (Assento assento : assentosOcupados) {
                    Passageiro p = passageiroDAO.readById(assento.getPassageiroId());
                    relatorio.append(String.format("  - %s (Doc: %s) - Assento %s\n",
                        p.getNome(), p.getDocumento(), assento.getCodigoAssento()));
                }
                totalPassageiros += assentosOcupados.size();
            }
        }

        relatorio.append(String.format("\nTotal de passageiros: %d\n", totalPassageiros));
        return relatorio.toString();
    }

    /*
      Relatório: Passageiros que chegaram em um aeroporto em um período.
     */
    public String relatorioPassageirosChegada(int aeroportoId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        Aeroporto aeroporto = aeroportoDAO.readById(aeroportoId);
        if (aeroporto == null) return "Aeroporto não encontrado.";

        List<Voo> voos = vooDAO.buscarPorDestino(aeroportoId, dataInicio, dataFim);
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO: PASSAGEIROS QUE CHEGARAM ===\n");
        relatorio.append(String.format("Aeroporto: %s (%s)\n", aeroporto.getNome(), aeroporto.getAbreviacao()));
        relatorio.append(String.format("Período: %s a %s\n\n", dataInicio, dataFim));

        int totalPassageiros = 0;
        for (Voo voo : voos) {
            List<Assento> assentosOcupados = new ArrayList<>();
            for (Assento assento : assentoDAO.findByVoo(voo.getId())) {
                if (assento.getPassageiroId() != null) {
                    assentosOcupados.add(assento);
                }
            }

            if (!assentosOcupados.isEmpty()) {
                Aeroporto origem = aeroportoDAO.readById(voo.getOrigemId());
                relatorio.append(String.format("Voo #%d de %s (%s) - %d passageiros\n",
                    voo.getId(), origem.getAbreviacao(), voo.getDataHora(), assentosOcupados.size()));
                
                for (Assento assento : assentosOcupados) {
                    Passageiro p = passageiroDAO.readById(assento.getPassageiroId());
                    relatorio.append(String.format("  - %s (Doc: %s) - Assento %s\n",
                        p.getNome(), p.getDocumento(), assento.getCodigoAssento()));
                }
                totalPassageiros += assentosOcupados.size();
            }
        }

        relatorio.append(String.format("\nTotal de passageiros: %d\n", totalPassageiros));
        return relatorio.toString();
    }

    /*
      Relatório: Valor arrecadado por companhia aérea em um período.
     */
    public String relatorioArrecadacaoCompanhia(int companhiaId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        CompanhiaAerea companhia = companhiaDAO.readById(companhiaId);
        if (companhia == null) return "Companhia aérea não encontrada.";

        double totalArrecadado = ticketDAO.calcularArrecadacaoPorCompanhia(companhiaId, dataInicio, dataFim);

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== RELATÓRIO: ARRECADAÇÃO POR COMPANHIA ===\n");
        relatorio.append(String.format("Companhia: %s (%s)\n", companhia.getNome(), companhia.getAbreviacao()));
        relatorio.append(String.format("Período: %s a %s\n\n", dataInicio, dataFim));
        relatorio.append(String.format("Total arrecadado: R$ %.2f\n", totalArrecadado));

        return relatorio.toString();
    }

    /*
      Relatório: Lista de passageiros de um voo.
     */
    public String relatorioPassageirosVoo(int vooId) throws SQLException {
        Voo voo = vooDAO.readById(vooId);
        if (voo == null) return "Voo não encontrado.";

        Aeroporto origem = aeroportoDAO.readById(voo.getOrigemId());
        Aeroporto destino = aeroportoDAO.readById(voo.getDestinoId());
        CompanhiaAerea companhia = companhiaDAO.readById(voo.getCompanhiaAereaId());

        List<Assento> assentosOcupados = new ArrayList<>();
        for (Assento assento : assentoDAO.findByVoo(vooId)) {
            if (assento.getPassageiroId() != null) {
                assentosOcupados.add(assento);
            }
        }

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=== LISTA DE PASSAGEIROS DO VOO ===\n");
        relatorio.append(String.format("Voo #%d\n", voo.getId()));
        relatorio.append(String.format("Rota: %s (%s) → %s (%s)\n",
            origem.getNome(), origem.getAbreviacao(), destino.getNome(), destino.getAbreviacao()));
        relatorio.append(String.format("Companhia: %s\n", companhia.getNome()));
        relatorio.append(String.format("Data/Hora: %s\n", voo.getDataHora()));
        relatorio.append(String.format("Passageiros: %d/%d\n\n", assentosOcupados.size(), voo.getCapacidade()));

        if (assentosOcupados.isEmpty()) {
            relatorio.append("Nenhum passageiro embarcado.\n");
        } else {
            for (Assento assento : assentosOcupados) {
                Passageiro p = passageiroDAO.readById(assento.getPassageiroId());
                relatorio.append(String.format("Assento %s: %s (Doc: %s)\n",
                    assento.getCodigoAssento(), p.getNome(), p.getDocumento()));
            }
        }

        return relatorio.toString();
    }
}
