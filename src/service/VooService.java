package service;

import dao.*;
import model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class VooService {
    
    private final VooDAO vooDAO;
    private final AssentoDAO assentoDAO;
    private final TicketDAO ticketDAO;
    private final AeroportoDAO aeroportoDAO;
    private final CompanhiaAereaDAO companhiaDAO;

    public VooService() {
        this.vooDAO = new VooDAO();
        this.assentoDAO = new AssentoDAO();
        this.ticketDAO = new TicketDAO();
        this.aeroportoDAO = new AeroportoDAO();
        this.companhiaDAO = new CompanhiaAereaDAO();
    }

    public List<Voo> buscarVoosDisponiveis(int origemId, int destinoId, 
                                           LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        return vooDAO.buscarVoos(origemId, destinoId, dataInicio, dataFim);
    }

    public Ticket comprarTicket(int vooId, int passageiroId, double valor, Integer assentoId) throws SQLException {
        Voo voo = vooDAO.readById(vooId);
        if (voo == null) throw new IllegalArgumentException("Voo não encontrado.");

        List<Assento> assentosLivres = assentoDAO.findAssentosLivres(vooId);
        if (assentosLivres.isEmpty()) throw new IllegalStateException("Voo lotado!");

        String codigo = gerarCodigoTicket();
        Ticket ticket = new Ticket(0, valor, vooId, passageiroId, codigo);
        int ticketId = ticketDAO.create(ticket);
        ticket = ticketDAO.readById(ticketId);

        if (assentoId != null) {
            assentoDAO.atribuirPassageiro(assentoId, passageiroId);
        }

        return ticket;
    }

    public boolean vooEstaLotado(int vooId) throws SQLException {
        return assentoDAO.findAssentosLivres(vooId).isEmpty();
    }

    public String obterDetalhesVoo(int vooId) throws SQLException {
        Voo voo = vooDAO.readById(vooId);
        if (voo == null) return "Voo não encontrado.";

        Aeroporto origem = aeroportoDAO.readById(voo.getOrigemId());
        Aeroporto destino = aeroportoDAO.readById(voo.getDestinoId());
        CompanhiaAerea companhia = companhiaDAO.readById(voo.getCompanhiaAereaId());
        List<Assento> assentosLivres = assentoDAO.findAssentosLivres(vooId);

        return String.format(
            "Voo #%d | %s (%s) → %s (%s) | %s | %s | Assentos: %d/%d",
            voo.getId(), origem.getAbreviacao(), origem.getCidade(),
            destino.getAbreviacao(), destino.getCidade(), companhia.getNome(),
            voo.getDataHora(), assentosLivres.size(), voo.getCapacidade()
        );
    }

    private String gerarCodigoTicket() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            codigo.append(chars.charAt(random.nextInt(chars.length())));
        }
        return codigo.toString();
    }

    public List<Voo> buscarVoosPorOrigem(int origemId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        return vooDAO.buscarPorOrigem(origemId, dataInicio, dataFim);
    }

    public List<Voo> buscarVoosPorDestino(int destinoId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        return vooDAO.buscarPorDestino(destinoId, dataInicio, dataFim);
    }
}
