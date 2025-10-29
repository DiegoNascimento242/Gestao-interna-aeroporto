package service;

import dao.VooDAO;
import model.Voo;
import java.time.LocalDate;
import java.time.LocalTime;

// CRUD COM DAO
public class VooService {
    private VooDAO vooDAO;

    public VooService() {
        this.vooDAO = new VooDAO();
    }

    public void criarVoo(String origem, String destino, LocalDate data, LocalTime duracao,
                         String companhia, int capacidade, double preco) {
        int novoId = this.gerarNovoId();
        Voo voo = new Voo(novoId, origem, destino, data, duracao, companhia, capacidade, preco);
        this.vooDAO.criar(voo);
    }

    public Voo[] listarVoos() {
        return this.vooDAO.listarTodos();
    }

    public Voo buscarVooPorId(int id) {
        return this.vooDAO.buscarPorId(id);
    }

    public Voo[] buscarVoosPorRotaEData(String origem, String destino, LocalDate data) {
        return this.vooDAO.buscarPorRotaEData(origem, destino, data);
    }

    public Voo[] buscarVoosPorOrigem(String origem) {
        return this.vooDAO.buscarPorOrigem(origem);
    }

    public Voo[] buscarVoosPorDestino(String destino) {
        return this.vooDAO.buscarPorDestino(destino);
    }

    public void atualizarEstadoVoo(int id, String estado) {
        Voo voo = this.vooDAO.buscarPorId(id);
        if (voo != null) {
            voo.setEstado(estado);
            this.vooDAO.atualizar(id, voo);
        }
    }

    public boolean reservarAssento(int vooId) {
        Voo voo = this.vooDAO.buscarPorId(vooId);
        if (voo != null && voo.temAssentosDisponiveis()) {
            boolean sucesso = voo.reservarAssento();
            if (sucesso) {
                this.vooDAO.atualizar(vooId, voo);
            }
            return sucesso;
        }
        return false;
    }

    public void deletarVoo(int id) {
        this.vooDAO.deletar(id);
    }

    private int gerarNovoId() {
        Voo[] todos = this.listarVoos();
        int maiorId = 0;
        for (Voo v : todos) {
            if (v.getId() > maiorId) {
                maiorId = v.getId();
            }
        }
        return maiorId + 1;
    }
}