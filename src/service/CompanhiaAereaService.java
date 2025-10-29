package service;

import dao.CompanhiaAereaDAO;
import model.CompanhiaAerea;

// CRUD COM DAO
public class CompanhiaAereaService {
    private CompanhiaAereaDAO companhiaDAO;

    public CompanhiaAereaService() {
        this.companhiaDAO = new CompanhiaAereaDAO();
    }

    public void criarCompanhia(String nome, String abreviacao) {
        int novoId = this.gerarNovoId();
        CompanhiaAerea companhia = new CompanhiaAerea(novoId, nome, abreviacao);
        this.companhiaDAO.criar(companhia);
    }

    public CompanhiaAerea[] listarCompanhias() {
        return this.companhiaDAO.listarTodos();
    }

    public CompanhiaAerea buscarCompanhiaPorId(int id) {
        return this.companhiaDAO.buscarPorId(id);
    }

    public CompanhiaAerea buscarCompanhiaPorNome(String nome) {
        return this.companhiaDAO.buscarPorNome(nome);
    }

    public void atualizarCompanhia(int id, String nome, String abreviacao) {
        CompanhiaAerea companhia = this.companhiaDAO.buscarPorId(id);
        if (companhia != null) {
            companhia.setNome(nome);
            companhia.setAbreviacao(abreviacao);
            this.companhiaDAO.atualizar(id, companhia);
        }
    }

    public void deletarCompanhia(int id) {
        this.companhiaDAO.deletar(id);
    }

    private int gerarNovoId() {
        CompanhiaAerea[] todos = this.listarCompanhias();
        int maiorId = 0;
        for (CompanhiaAerea c : todos) {
            if (c.getId() > maiorId) {
                maiorId = c.getId();
            }
        }
        return maiorId + 1;
    }
}