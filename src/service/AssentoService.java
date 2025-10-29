package service;

import dao.AssentoDAO;
import model.Assento;

// CRUD COM DAO
public class AssentoService {
    private AssentoDAO assentoDAO;

    public AssentoService() {
        this.assentoDAO = new AssentoDAO();
    }

    public void criarAssento(int vooId, String codigoAssento) {
        int novoId = this.gerarNovoId();
        Assento assento = new Assento(novoId, vooId, codigoAssento);
        this.assentoDAO.criar(assento);
    }

    public Assento[] listarAssentos() {
        return this.assentoDAO.listarTodos();
    }

    public Assento[] listarAssentosPorVoo(int vooId) {
        return this.assentoDAO.buscarPorVooId(vooId);
    }

    public Assento[] listarAssentosLivresPorVoo(int vooId) {
        return this.assentoDAO.buscarAssentosLivresPorVooId(vooId);
    }

    public Assento buscarAssentoPorId(int id) {
        return this.assentoDAO.buscarPorId(id);
    }

    public Assento buscarAssentoPorVooECodigo(int vooId, String codigoAssento) {
        return this.assentoDAO.buscarPorVooIdECodigo(vooId, codigoAssento);
    }

    public boolean reservarAssento(int assentoId, int passageiroId) {
        Assento assento = this.assentoDAO.buscarPorId(assentoId);
        if (assento != null && !assento.estaOcupado()) {
            assento.setPassageiroId(passageiroId);
            this.assentoDAO.atualizar(assentoId, assento);
            return true;
        }
        return false;
    }

    public void liberarAssento(int assentoId) {
        Assento assento = this.assentoDAO.buscarPorId(assentoId);
        if (assento != null) {
            assento.setPassageiroId(null);
            this.assentoDAO.atualizar(assentoId, assento);
        }
    }

    public void deletarAssento(int id) {
        this.assentoDAO.deletar(id);
    }

    private int gerarNovoId() {
        Assento[] todos = this.listarAssentos();
        int maiorId = 0;
        for (Assento a : todos) {
            if (a.getId() > maiorId) {
                maiorId = a.getId();
            }
        }
        return maiorId + 1;
    }
}