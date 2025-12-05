package main;

import dao.*;
import model.*;
import service.*;
import util.DatabaseConnection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


import javax.swing.JOptionPane;


public class Main {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        String mensagemInicial =
                "TRABALHO  POO - DIEGO CARDOSO DO NASCIMENTO\n\n" +
                        "Bem-vindo ao sistema completo de Gestao-interna-aeroporto!\n\n" +
                        "Este sistema inclui:\n" +
                        "- CRUDs completos\n" +
                        "- Compra de passagens\n" +
                        "- Check-in online\n" +
                        "- Relatorios gerenciais\n" +
                        "Clique em OK para continuar...";

        JOptionPane.showMessageDialog(null, mensagemInicial);






        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GESTÃO INTERNA DE AEROPORTO   ║");
        System.out.println("║          Diego Cardoso do Nascimento       ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        if (!DatabaseConnection.testConnection()) {
            System.err.println("ERRO: Não foi possível conectar ao banco!");
            System.err.println("Execute: mysql -u root -p < aeroporto_db.sql");
            return;
        }

        System.out.println("✓ Conexão estabelecida: " + DatabaseConnection.getConfigInfo() + "\n");
        menuPrincipal();
    }

    private static void menuPrincipal() {
        while (true) {
            System.out.println("\n═══ MENU PRINCIPAL ═══");
            System.out.println("1. Buscar e Comprar Passagens");
            System.out.println("2. Realizar Check-in");
            System.out.println("3. Consultar Reserva");
            System.out.println("4. Administração (CRUDs)");
            System.out.println("5. Relatórios Gerenciais");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");

            int opcao = lerInt();

            switch (opcao) {
                case 1: menuBuscarComprar(); break;
                case 2: menuCheckIn(); break;
                case 3: menuConsultarReserva(); break;
                case 4: menuAdministracao(); break;
                case 5: menuRelatorios(); break;
                case 0:
                    System.out.println("\nEncerrando. Até logo!");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuBuscarComprar() {
        try {
            AeroportoDAO aeroportoDAO = new AeroportoDAO();
            VooService vooService = new VooService();

            System.out.println("\n═══ BUSCAR PASSAGENS ═══");
            
            List<Aeroporto> aeroportos = aeroportoDAO.readAll();
            System.out.println("\nAeroportos:");
            for (Aeroporto a : aeroportos) {
                System.out.printf("%d. %s (%s) - %s\n", a.getId(), a.getNome(), a.getAbreviacao(), a.getCidade());
            }

            System.out.print("\nOrigem (ID): ");
            int origemId = lerInt();
            System.out.print("Destino (ID): ");
            int destinoId = lerInt();
            System.out.print("Data inicial (dd/MM/yyyy): ");
            LocalDateTime dataInicio = LocalDate.parse(scanner.nextLine(), dateFormatter).atStartOfDay();
            System.out.print("Data final (dd/MM/yyyy): ");
            LocalDateTime dataFim = LocalDate.parse(scanner.nextLine(), dateFormatter).atTime(23, 59);

            List<Voo> voos = vooService.buscarVoosDisponiveis(origemId, destinoId, dataInicio, dataFim);

            if (voos.isEmpty()) {
                System.out.println("\nNenhum voo encontrado.");
                return;
            }

            System.out.println("\n═══ VOOS DISPONÍVEIS ═══");
            for (Voo voo : voos) {
                System.out.println(vooService.obterDetalhesVoo(voo.getId()));
            }

            System.out.print("\nComprar passagem? (s/n): ");
            if (scanner.nextLine().equalsIgnoreCase("s")) {
                System.out.print("ID do voo: ");
                int vooId = lerInt();
                System.out.print("ID do passageiro: ");
                int passageiroId = lerInt();
                System.out.print("Valor: R$ ");
                double valor = lerDouble();

                Ticket ticket = vooService.comprarTicket(vooId, passageiroId, valor, null);
                System.out.println("\n✓ Passagem comprada! Código: " + ticket.getCodigo());
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void menuCheckIn() {
        try {
            CheckInService checkInService = new CheckInService();

            System.out.println("\n═══ CHECK-IN ═══");
            System.out.print("ID do ticket: ");
            int ticketId = lerInt();
            System.out.print("Documento: ");
            String documento = scanner.nextLine();

            BoardingPass bp = checkInService.realizarCheckIn(ticketId, documento);
            System.out.println("\n✓ Check-in realizado!");
            System.out.println(bp.gerarBoardingPassCompleto());

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void menuConsultarReserva() {
        try {
            TicketDAO ticketDAO = new TicketDAO();

            System.out.println("\n═══ CONSULTAR RESERVA ═══");
            System.out.print("Código do ticket: ");
            String codigo = scanner.nextLine();

            Ticket ticket = ticketDAO.findByCodigo(codigo);
            if (ticket == null) {
                System.out.println("Reserva não encontrada.");
            } else {
                System.out.println("\n" + ticket);
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void menuAdministracao() {
        System.out.println("\n═══ ADMINISTRAÇÃO ═══");
        System.out.println("1. Passageiros");
        System.out.println("2. Aeroportos");
        System.out.println("3. Companhias");
        System.out.println("4. Voos");
        System.out.print("Escolha: ");

        int opcao = lerInt();
        switch (opcao) {
            case 1: listarPassageiros(); break;
            case 2: listarAeroportos(); break;
            case 3: listarCompanhias(); break;
            case 4: listarVoos(); break;
        }
    }

    private static void listarPassageiros() {
        try {
            PassageiroDAO dao = new PassageiroDAO();
            List<Passageiro> lista = dao.readAll();
            System.out.println("\n═══ PASSAGEIROS ═══");
            lista.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void listarAeroportos() {
        try {
            AeroportoDAO dao = new AeroportoDAO();
            List<Aeroporto> lista = dao.readAll();
            System.out.println("\n═══ AEROPORTOS ═══");
            lista.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void listarCompanhias() {
        try {
            CompanhiaAereaDAO dao = new CompanhiaAereaDAO();
            List<CompanhiaAerea> lista = dao.readAll();
            System.out.println("\n═══ COMPANHIAS ═══");
            lista.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void listarVoos() {
        try {
            VooDAO dao = new VooDAO();
            List<Voo> lista = dao.readAll();
            System.out.println("\n═══ VOOS ═══");
            lista.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void menuRelatorios() {
        try {
            RelatorioService relatorioService = new RelatorioService();

            System.out.println("\n═══ RELATÓRIOS ═══");
            System.out.println("1. Passageiros saída");
            System.out.println("2. Passageiros chegada");
            System.out.println("3. Arrecadação companhia");
            System.out.println("4. Lista passageiros voo");
            System.out.print("Escolha: ");

            int opcao = lerInt();

            if (opcao == 1 || opcao == 2) {
                System.out.print("ID aeroporto: ");
                int aeroportoId = lerInt();
                System.out.print("Data inicial (dd/MM/yyyy): ");
                LocalDateTime dataInicio = LocalDate.parse(scanner.nextLine(), dateFormatter).atStartOfDay();
                System.out.print("Data final (dd/MM/yyyy): ");
                LocalDateTime dataFim = LocalDate.parse(scanner.nextLine(), dateFormatter).atTime(23, 59);

                String relatorio = opcao == 1 
                    ? relatorioService.relatorioPassageirosSaida(aeroportoId, dataInicio, dataFim)
                    : relatorioService.relatorioPassageirosChegada(aeroportoId, dataInicio, dataFim);

                System.out.println("\n" + relatorio);

            } else if (opcao == 3) {
                System.out.print("ID companhia: ");
                int companhiaId = lerInt();
                System.out.print("Data inicial (dd/MM/yyyy): ");
                LocalDateTime dataInicio = LocalDate.parse(scanner.nextLine(), dateFormatter).atStartOfDay();
                System.out.print("Data final (dd/MM/yyyy): ");
                LocalDateTime dataFim = LocalDate.parse(scanner.nextLine(), dateFormatter).atTime(23, 59);

                String relatorio = relatorioService.relatorioArrecadacaoCompanhia(companhiaId, dataInicio, dataFim);
                System.out.println("\n" + relatorio);

            } else if (opcao == 4) {
                System.out.print("ID voo: ");
                int vooId = lerInt();
                String relatorio = relatorioService.relatorioPassageirosVoo(vooId);
                System.out.println("\n" + relatorio);
            }

        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static int lerInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
