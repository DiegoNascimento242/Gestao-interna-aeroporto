package controller;

import model.*;
import service.*;
import dao.GerenciadorBagagens;
import util.GeradorCodigo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

// CONTROLLER PRINCIPAL - coordena toda a aplicação
public class SistemaController {
    // SERVIÇOS COM DAO
    private PassageiroService passageiroService = new PassageiroService();
    private AeroportoService aeroportoService = new AeroportoService();
    private CompanhiaAereaService companhiaService = new CompanhiaAereaService();
    private VooService vooService = new VooService();
    private AssentoService assentoService = new AssentoService();
    private TicketService ticketService = new TicketService();
    private BagagemService bagagemService = new BagagemService();
    private CheckInService checkInService = new CheckInService();
    private BoardingPassService boardingPassService = new BoardingPassService();
    private RelatorioService relatorioService = new RelatorioService();

    // SERVIÇOS SEM DAO (para demonstração)
    private GerenciadorVoosService gerenciadorVoosService = new GerenciadorVoosService();
    private GerenciadorBagagens gerenciadorBagagens = new GerenciadorBagagens();

    private Scanner scanner = new Scanner(System.in);

    // MÉTODO PARA INICIALIZAR DADOS DE EXEMPLO
    public void inicializarDados() {
        System.out.println("Inicializando sistema aeroportuario...");

        // Criar aeroportos de exemplo
        aeroportoService.criarAeroporto("Aeroporto Internacional de Sao Paulo", "GRU", "Sao Paulo");
        aeroportoService.criarAeroporto("Aeroporto Santos Dumont", "SDU", "Rio de Janeiro");
        aeroportoService.criarAeroporto("Aeroporto de Brasilia", "BSB", "Brasilia");

        // Criar companhias aéreas de exemplo
        companhiaService.criarCompanhia("LATAM Airlines", "LATAM");
        companhiaService.criarCompanhia("Gol Linhas Aereas", "GOL");
        companhiaService.criarCompanhia("Azul Linhas Aereas", "AZUL");

        // Criar voos de exemplo - USANDO CRUD COM DAO
        vooService.criarVoo("GRU", "SDU", LocalDate.now().plusDays(1),
                LocalTime.of(2, 30), "LATAM", 5, 350.00);
        vooService.criarVoo("SDU", "GRU", LocalDate.now().plusDays(2),
                LocalTime.of(2, 30), "GOL", 5, 320.00);

        // Criar voos de exemplo - USANDO CRUD SEM DAO
        gerenciadorVoosService.criarVooDemonstracao("GRU", "BSB", LocalDate.now().plusDays(3),
                LocalTime.of(1, 45), "AZUL", 5, 280.00);

        // Criar assentos para os voos
        inicializarAssentos();

        System.out.println("Sistema inicializado com sucesso!");
    }

    private void inicializarAssentos() {
        // Criar assentos para voos do VooService (CRUD COM DAO)
        Voo[] voosComDAO = vooService.listarVoos();
        for (Voo voo : voosComDAO) {
            for (int fileira = 1; fileira <= 5; fileira++) {
                assentoService.criarAssento(voo.getId(), GeradorCodigo.gerarCodigoAssento(fileira, 'A'));
                assentoService.criarAssento(voo.getId(), GeradorCodigo.gerarCodigoAssento(fileira, 'B'));
            }
        }

        // Criar assentos para voos do GerenciadorVoos (CRUD SEM DAO)
        Voo[] voosSemDAO = gerenciadorVoosService.listarVoos();
        for (Voo voo : voosSemDAO) {
            for (int fileira = 1; fileira <= 5; fileira++) {
                assentoService.criarAssento(voo.getId(), GeradorCodigo.gerarCodigoAssento(fileira, 'C'));
                assentoService.criarAssento(voo.getId(), GeradorCodigo.gerarCodigoAssento(fileira, 'D'));
            }
        }
    }

    // MENU PRINCIPAL
    public void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n==========================================");
            System.out.println("    SISTEMA DE GESTAO AEROPORTO");
            System.out.println("==========================================");
            System.out.println("1. Painel de Voos do Dia");
            System.out.println("2. Compra de Passagem");
            System.out.println("3. Check-in");
            System.out.println("4. Area do Passageiro");
            System.out.println("5. Area Administrativa");
            System.out.println("6. Demonstrar Conceitos POO");
            System.out.println("0. Sair");
            System.out.println("==========================================");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1 -> exibirPainelVoos();
                case 2 -> menuCompraPassagem();
                case 3 -> menuCheckIn();
                case 4 -> menuAreaPassageiro();
                case 5 -> menuAdministrativo();
                //case 6 -> demonstrarConceitosPOO();
                case 0 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private void exibirPainelVoos() {
        System.out.println("\n==========================================");
        System.out.println("          PAINEL DE VOOS - " + LocalDate.now());
        System.out.println("==========================================");

        System.out.println("\n--- VOOS (CRUD COM DAO) ---");
        Voo[] voosComDAO = vooService.listarVoos();
        if (voosComDAO.length == 0) {
            System.out.println("Nenhum voo encontrado.");
        } else {
            for (Voo voo : voosComDAO) {
                System.out.println(voo);
            }
        }

        System.out.println("\n--- VOOS (CRUD SEM DAO) ---");
        Voo[] voosSemDAO = gerenciadorVoosService.listarVoos();
        if (voosSemDAO.length == 0) {
            System.out.println("Nenhum voo encontrado.");
        } else {
            for (Voo voo : voosSemDAO) {
                System.out.println(voo);
            }
        }
    }

    private void menuCompraPassagem() {
        System.out.println("\n==========================================");
        System.out.println("           COMPRA DE PASSAGEM");
        System.out.println("==========================================");

        // Buscar voos
        System.out.print("Origem: ");
        String origem = scanner.nextLine();
        System.out.print("Destino: ");
        String destino = scanner.nextLine();
        System.out.print("Data (AAAA-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());

        Voo[] voosEncontrados = vooService.buscarVoosPorRotaEData(origem, destino, data);

        if (voosEncontrados.length == 0) {
            System.out.println("Nenhum voo encontrado para essa rota e data.");
            return;
        }

        System.out.println("\nVoos encontrados:");
        for (int i = 0; i < voosEncontrados.length; i++) {
            System.out.println((i + 1) + ". " + voosEncontrados[i]);
        }

        System.out.print("Escolha o numero do voo: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > voosEncontrados.length) {
            System.out.println("Escolha invalida!");
            return;
        }

        Voo vooEscolhido = voosEncontrados[escolha - 1];

        // Verificar se ha assentos disponíveis
        if (!vooEscolhido.temAssentosDisponiveis()) {
            System.out.println("Desculpe, este voo esta lotado.");
            return;
        }

        // Cadastrar ou identificar passageiro
        Passageiro passageiro = identificarPassageiro();
        if (passageiro == null) {
            return;
        }

        // Selecionar assento
        Assento assento = selecionarAssento(vooEscolhido.getId());
        if (assento == null) {
            return;
        }

        // Criar ticket
        Ticket ticket = ticketService.criarTicket(vooEscolhido.getPreco(), vooEscolhido.getId(), passageiro.getId());

        // Reservar assento
        assentoService.reservarAssento(assento.getId(), passageiro.getId());

        // Reservar assento no voo
        vooService.reservarAssento(vooEscolhido.getId());

        System.out.println("\nCompra realizada com sucesso!");
        System.out.println("Ticket: " + ticket.getCodigo());
        System.out.println("Assento: " + assento.getCodigoAssento());
        System.out.println("Valor: R$ " + ticket.getValor());
    }

    private Passageiro identificarPassageiro() {
        System.out.print("Documento do passageiro: ");
        String documento = scanner.nextLine();

        Passageiro passageiro = passageiroService.buscarPorDocumento(documento);

        if (passageiro == null) {
            System.out.println("Passageiro nao encontrado. Cadastro necessario:");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Data de Nascimento (AAAA-MM-DD): ");
            LocalDate nascimento = LocalDate.parse(scanner.nextLine());
            System.out.print("Login: ");
            String login = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            passageiroService.criarPassageiro(nome, documento, login, senha, nascimento);
            passageiro = passageiroService.buscarPorDocumento(documento);
            System.out.println("Passageiro cadastrado com sucesso!");
        }

        return passageiro;
    }

    private Assento selecionarAssento(int vooId) {
        Assento[] assentosLivres = assentoService.listarAssentosLivresPorVoo(vooId);

        if (assentosLivres.length == 0) {
            System.out.println("Nao ha assentos disponiveis neste voo.");
            return null;
        }

        System.out.println("\nAssentos disponiveis:");
        for (int i = 0; i < assentosLivres.length; i++) {
            System.out.println((i + 1) + ". " + assentosLivres[i].getCodigoAssento());
        }

        System.out.print("Escolha o numero do assento: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > assentosLivres.length) {
            System.out.println("Escolha invalida!");
            return null;
        }

        return assentosLivres[escolha - 1];
    }

    private void menuCheckIn() {
        System.out.println("\n==========================================");
        System.out.println("                CHECK-IN");
        System.out.println("==========================================");
        System.out.print("Codigo do Ticket: ");
        String codigoTicket = scanner.nextLine();

        Ticket ticket = ticketService.buscarTicketPorCodigo(codigoTicket);
        if (ticket == null) {
            System.out.println("Ticket nao encontrado!");
            return;
        }

        System.out.print("Documento: ");
        String documento = scanner.nextLine();

        // Gerar boarding pass
        Assento assento = obterAssentoDoTicket(ticket);
        if (assento == null) {
            System.out.println("Assento nao encontrado!");
            return;
        }

        String codigoBoardingPass = GeradorCodigo.gerarCodigoBoardingPass();
        boolean sucesso = checkInService.realizarCheckin(ticket.getId(), documento, codigoBoardingPass);

        if (sucesso) {
            BoardingPass boardingPass = boardingPassService.gerarBoardingPass(
                    ticket.getId(), assento.getCodigoAssento(), "A" + (ticket.getId() % 10 + 1));

            System.out.println("\nCheck-in realizado com sucesso!");
            System.out.println(boardingPass.gerarBoardingPassCompleto());
        } else {
            System.out.println("Nao foi possivel realizar o check-in. Verifique se esta dentro do prazo (ate 24h antes do voo).");
        }
    }

    private Assento obterAssentoDoTicket(Ticket ticket) {
        Assento[] assentos = assentoService.listarAssentosPorVoo(ticket.getVooId());
        for (Assento assento : assentos) {
            if (assento.getPassageiroId() != null && assento.getPassageiroId() == ticket.getPassageiroId()) {
                return assento;
            }
        }
        return null;
    }

    private void menuAreaPassageiro() {
        System.out.println("\n==========================================");
        System.out.println("           AREA DO PASSAGEIRO");
        System.out.println("==========================================");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (!passageiroService.fazerLogin(login, senha)) {
            System.out.println("Login ou senha invalidos!");
            return;
        }

        System.out.println("Login realizado com sucesso!");

        // Mostrar tickets do passageiro
        Passageiro passageiro = passageiroService.buscarPorDocumento(login); // simplificado
        if (passageiro != null) {
            Ticket[] tickets = ticketService.buscarTicketsPorPassageiro(passageiro.getId());
            if (tickets.length > 0) {
                System.out.println("\nSeus tickets:");
                for (Ticket ticket : tickets) {
                    System.out.println(" - " + ticket);
                }
            } else {
                System.out.println("Voce nao possui tickets.");
            }
        }
    }

    private void menuAdministrativo() {
        int opcao;
        do {
            System.out.println("\n==========================================");
            System.out.println("           AREA ADMINISTRATIVA");
            System.out.println("==========================================");
            System.out.println("1. CRUD Passageiro");
            System.out.println("2. CRUD Aeroporto");
            System.out.println("3. CRUD Companhia Aerea");
            System.out.println("4. CRUD Voo");
            System.out.println("5. CRUD Assento");
            System.out.println("6. Relatorios");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> menuCRUDPassageiro();
                case 2 -> menuCRUDAeroporto();
                case 3 -> menuCRUDCompanhia();
                case 4 -> menuCRUDVoo();
                case 5 -> menuCRUDAssento();
                case 6 -> menuRelatorios();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    private void menuCRUDPassageiro() {
        System.out.println("\n==========================================");
        System.out.println("             CRUD PASSAGEIRO");
        System.out.println("==========================================");

        Passageiro[] passageiros = passageiroService.listarTodos();
        if (passageiros.length == 0) {
            System.out.println("Nenhum passageiro cadastrado.");
        } else {
            for (Passageiro p : passageiros) {
                System.out.println(p);
            }
        }
    }

    private void menuCRUDAeroporto() {
        System.out.println("\n==========================================");
        System.out.println("              CRUD AEROPORTO");
        System.out.println("==========================================");

        Aeroporto[] aeroportos = aeroportoService.listarAeroportos();
        if (aeroportos.length == 0) {
            System.out.println("Nenhum aeroporto cadastrado.");
        } else {
            for (Aeroporto a : aeroportos) {
                System.out.println(a);
            }
        }
    }

    private void menuCRUDCompanhia() {
        System.out.println("\n==========================================");
        System.out.println("           CRUD COMPANHIA AEREA");
        System.out.println("==========================================");

        CompanhiaAerea[] companhias = companhiaService.listarCompanhias();
        if (companhias.length == 0) {
            System.out.println("Nenhuma companhia aerea cadastrada.");
        } else {
            for (CompanhiaAerea c : companhias) {
                System.out.println(c);
            }
        }
    }

    private void menuCRUDVoo() {
        System.out.println("\n==========================================");
        System.out.println("                CRUD VOO");
        System.out.println("==========================================");

        Voo[] voos = vooService.listarVoos();
        if (voos.length == 0) {
            System.out.println("Nenhum voo cadastrado.");
        } else {
            for (Voo v : voos) {
                System.out.println(v);
            }
        }
    }

    private void menuCRUDAssento() {
        System.out.println("\n==========================================");
        System.out.println("              CRUD ASSENTO");
        System.out.println("==========================================");

        Assento[] assentos = assentoService.listarAssentos();
        if (assentos.length == 0) {
            System.out.println("Nenhum assento cadastrado.");
        } else {
            for (Assento a : assentos) {
                System.out.println(a);
            }
        }
    }

    private void menuRelatorios() {
        int opcao;
        do {
            System.out.println("\n==========================================");
            System.out.println("               RELATORIOS");
            System.out.println("==========================================");
            System.out.println("1. Passageiros que deixaram um aeroporto");
            System.out.println("2. Passageiros que chegaram em um aeroporto");
            System.out.println("3. Valor arrecadado por companhia");
            System.out.println("4. Ocupacao dos voos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opcao: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> {
                    System.out.print("Aeroporto de origem: ");
                    String origem = scanner.nextLine();
                    relatorioService.gerarRelatorioPassageirosQueDeixaramAeroporto(origem);
                }
                case 2 -> {
                    System.out.print("Aeroporto de destino: ");
                    String destino = scanner.nextLine();
                    relatorioService.gerarRelatorioPassageirosQueChegaramAeroporto(destino);
                }
                case 3 -> {
                    System.out.print("Companhia aerea: ");
                    String companhia = scanner.nextLine();
                    System.out.print("Data inicio (AAAA-MM-DD): ");
                    LocalDate inicio = LocalDate.parse(scanner.nextLine());
                    System.out.print("Data fim (AAAA-MM-DD): ");
                    LocalDate fim = LocalDate.parse(scanner.nextLine());
                    relatorioService.gerarRelatorioValorArrecadadoPorCompanhia(companhia, inicio, fim);
                }
                case 4 -> relatorioService.gerarRelatorioOcupacaoVoos();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }
}