package service;

import dao.BagagemDAO;
import model.Bagagem;

// CRUD COM DAO
public class BagagemService {
    private BagagemDAO bagagemDAO;

    public BagagemService() {
        this.bagagemDAO = new BagagemDAO();
    }

    public void despacharBagagem(int ticketId, String documento, double peso) {
        int novoId = this.gerarNovoId();
        Bagagem bagagem = new Bagagem(novoId, ticketId, documento, peso);
        this.bagagemDAO.criar(bagagem);
    }

    public Bagagem[] listarBagagens() {
        return this.bagagemDAO.listarTodos();
    }

    public Bagagem[] buscarBagagensPorTicket(int ticketId) {
        return this.bagagemDAO.buscarPorTicketId(ticketId);
    }

    public Bagagem buscarBagagemPorId(int id) {
        return this.bagagemDAO.buscarPorId(id);
    }

    public void deletarBagagem(int id) {
        this.bagagemDAO.deletar(id);
    }

    private int gerarNovoId() {
        Bagagem[] todos = this.listarBagagens();
        int maiorId = 0;
        for (Bagagem b : todos) {
            if (b.getId() > maiorId) {
                maiorId = b.getId();
            }
        }
        return maiorId + 1;
    }
}