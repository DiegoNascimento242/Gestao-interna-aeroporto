package service;

import dao.AeroportoDAO;
import model.Aeroporto;

// CRUD COM DAO
public class AeroportoService {
    private AeroportoDAO aeroportoDAO;

    public AeroportoService() {
        this.aeroportoDAO = new AeroportoDAO();
    }

    public void criarAeroporto(String nome, String abreviacao, String cidade) {
        int novoId = this.gerarNovoId();
        Aeroporto aeroporto = new Aeroporto(novoId, nome, abreviacao, cidade);
        this.aeroportoDAO.criar(aeroporto);
    }

    public Aeroporto[] listarAeroportos() {
        return this.aeroportoDAO.listarTodos();
    }

    public Aeroporto buscarAeroportoPorId(int id) {
        return this.aeroportoDAO.buscarPorId(id);
    }

    public Aeroporto buscarAeroportoPorAbreviacao(String abreviacao) {
        return this.aeroportoDAO.buscarPorAbreviacao(abreviacao);
    }

    public void atualizarAeroporto(int id, String nome, String abreviacao, String cidade) {
        Aeroporto aeroporto = this.aeroportoDAO.buscarPorId(id);
        if (aeroporto != null) {
            aeroporto.setNome(nome);
            aeroporto.setAbreviacao(abreviacao);
            aeroporto.setCidade(cidade);
            this.aeroportoDAO.atualizar(id, aeroporto);
        }
    }

    public void deletarAeroporto(int id) {
        this.aeroportoDAO.deletar(id);
    }

    private int gerarNovoId() {
        Aeroporto[] todos = this.listarAeroportos();
        int maiorId = 0;
        for (Aeroporto a : todos) {
            if (a.getId() > maiorId) {
                maiorId = a.getId();
            }
        }
        return maiorId + 1;
    }
}