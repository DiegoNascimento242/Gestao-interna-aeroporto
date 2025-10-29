package service;

import dao.TicketDAO;
import model.Ticket;
import util.GeradorCodigo;

// CRUD COM DAO
public class TicketService {
    private TicketDAO ticketDAO;

    public TicketService() {
        this.ticketDAO = new TicketDAO();
    }

    public Ticket criarTicket(double valor, int vooId, int passageiroId) {
        int novoId = this.gerarNovoId();
        String codigo = GeradorCodigo.gerarCodigoTicket(5);
        Ticket ticket = new Ticket(novoId, valor, vooId, passageiroId, codigo);
        this.ticketDAO.criar(ticket);
        return ticket;
    }

    public Ticket[] listarTickets() {
        return this.ticketDAO.listarTodos();
    }

    public Ticket buscarTicketPorId(int id) {
        return this.ticketDAO.buscarPorId(id);
    }

    public Ticket buscarTicketPorCodigo(String codigo) {
        return this.ticketDAO.buscarPorCodigo(codigo);
    }

    public Ticket[] buscarTicketsPorPassageiro(int passageiroId) {
        return this.ticketDAO.buscarPorPassageiroId(passageiroId);
    }

    public Ticket[] buscarTicketsPorVoo(int vooId) {
        return this.ticketDAO.buscarPorVooId(vooId);
    }

    public void realizarCheckin(int ticketId) {
        Ticket ticket = this.ticketDAO.buscarPorId(ticketId);
        if (ticket != null) {
            ticket.setCheckinRealizado(true);
            this.ticketDAO.atualizar(ticketId, ticket);
        }
    }

    public void deletarTicket(int id) {
        this.ticketDAO.deletar(id);
    }

    private int gerarNovoId() {
        Ticket[] todos = this.listarTickets();
        int maiorId = 0;
        for (Ticket t : todos) {
            if (t.getId() > maiorId) {
                maiorId = t.getId();
            }
        }
        return maiorId + 1;
    }
}