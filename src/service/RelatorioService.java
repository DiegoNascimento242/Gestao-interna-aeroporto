package service;

import model.Passageiro;
import model.Ticket;
import model.Voo;
import java.time.LocalDate;

// SERVIÇO ESPECIALIZADO EM RELATÓRIOS
public class RelatorioService {
    private PassageiroService passageiroService;
    private VooService vooService;
    private TicketService ticketService;

    public RelatorioService() {
        this.passageiroService = new PassageiroService();
        this.vooService = new VooService();
        this.ticketService = new TicketService();
    }

    // Relatório 1: Passageiros que deixaram um determinado aeroporto
    public void gerarRelatorioPassageirosQueDeixaramAeroporto(String aeroportoOrigem) {
        System.out.println("\n=== RELATÓRIO: Passageiros que deixaram " + aeroportoOrigem + " ===");

        Voo[] voos = vooService.buscarVoosPorOrigem(aeroportoOrigem);
        int totalPassageiros = 0;

        for (Voo voo : voos) {
            Ticket[] tickets = ticketService.buscarTicketsPorVoo(voo.getId());
            for (Ticket ticket : tickets) {
                Passageiro passageiro = passageiroService.buscarPorId(ticket.getPassageiroId());
                if (passageiro != null) {
                    System.out.println(" Voo " + voo.getId() + ": " + passageiro.getNome() +
                            " (Doc: " + passageiro.getDocumento() + ")");
                    totalPassageiros++;
                }
            }
        }

        System.out.println(" Total de passageiros: " + totalPassageiros);
    }

    // Relatório 2: Passageiros que chegaram em um determinado aeroporto
    public void gerarRelatorioPassageirosQueChegaramAeroporto(String aeroportoDestino) {
        System.out.println("\n=== RELATÓRIO: Passageiros que chegaram em " + aeroportoDestino + " ===");

        Voo[] voos = vooService.buscarVoosPorDestino(aeroportoDestino);
        int totalPassageiros = 0;

        for (Voo voo : voos) {
            Ticket[] tickets = ticketService.buscarTicketsPorVoo(voo.getId());
            for (Ticket ticket : tickets) {
                Passageiro passageiro = passageiroService.buscarPorId(ticket.getPassageiroId());
                if (passageiro != null) {
                    System.out.println(" Voo " + voo.getId() + ": " + passageiro.getNome() +
                            " (Doc: " + passageiro.getDocumento() + ")");
                    totalPassageiros++;
                }
            }
        }

        System.out.println(" Total de passageiros: " + totalPassageiros);
    }

    // Relatório 3: Valor arrecadado por companhia em um período
    public void gerarRelatorioValorArrecadadoPorCompanhia(String companhia, LocalDate dataInicio, LocalDate dataFim) {
        System.out.println("\n=== RELATÓRIO: Valor arrecadado por " + companhia + " ===");
        System.out.println("Período: " + dataInicio + " a " + dataFim);

        double total = 0.0;
        int totalTickets = 0;

        Voo[] todosVoos = vooService.listarVoos();
        for (Voo voo : todosVoos) {
            if (voo.getCompanhia().equalsIgnoreCase(companhia) &&
                    !voo.getData().isBefore(dataInicio) &&
                    !voo.getData().isAfter(dataFim)) {

                Ticket[] tickets = ticketService.buscarTicketsPorVoo(voo.getId());
                for (Ticket ticket : tickets) {
                    total += ticket.getValor();
                    totalTickets++;
                }
            }
        }

        System.out.println(" Valor total arrecadado: R$ " + String.format("%.2f", total));
        System.out.println(" Total de tickets vendidos: " + totalTickets);
        System.out.println(" Média por ticket: R$ " + String.format("%.2f", totalTickets > 0 ? total / totalTickets : 0));
    }

    // Relatório 4: Ocupação dos voos
    public void gerarRelatorioOcupacaoVoos() {
        System.out.println("\n=== RELATÓRIO: Ocupação dos Voos ===");

        Voo[] voos = vooService.listarVoos();
        for (Voo voo : voos) {
            double ocupacao = (double) voo.getAssentosOcupados() / voo.getCapacidade() * 100;
            System.out.println("  Voo " + voo.getId() + " (" + voo.getOrigem() + "->" + voo.getDestino() +
                    "): " + voo.getAssentosOcupados() + "/" + voo.getCapacidade() +
                    " assentos (" + String.format("%.1f", ocupacao) + "%)");
        }
    }
}