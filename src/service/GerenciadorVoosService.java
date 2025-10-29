package service;

import dao.GerenciadorVoos;
import model.Voo;
import java.time.LocalDate;
import java.time.LocalTime;

// CRUD SEM DAO - demonstra abordagem alternativa
public class GerenciadorVoosService {
    private GerenciadorVoos gerenciadorVoos;

    public GerenciadorVoosService() {
        this.gerenciadorVoos = new GerenciadorVoos();
    }

    // CREATE - CRUD SEM DAO
    public void adicionarVoo(Voo voo) {
        this.gerenciadorVoos.adicionarVoo(voo);
    }

    // READ - CRUD SEM DAO
    public Voo[] listarVoos() {
        return this.gerenciadorVoos.listarVoos();
    }

    public Voo buscarVooPorId(int id) {
        return this.gerenciadorVoos.buscarVooPorId(id);
    }

    // UPDATE - CRUD SEM DAO
    public void atualizarVoo(int id, Voo vooAtualizado) {
        this.gerenciadorVoos.atualizarVoo(id, vooAtualizado);
    }

    // DELETE - CRUD SEM DAO
    public void removerVoo(int id) {
        this.gerenciadorVoos.removerVoo(id);
    }

    // Métodos específicos do Gerenciador
    public Voo[] buscarVoosPorOrigem(String origem) {
        return this.gerenciadorVoos.buscarVoosPorOrigem(origem);
    }

    public Voo[] buscarVoosPorData(LocalDate data) {
        return this.gerenciadorVoos.buscarVoosPorData(data);
    }

    public void criarVooDemonstracao(String origem, String destino, LocalDate data,
                                     LocalTime duracao, String companhia, int capacidade, double preco) {
        int novoId = this.gerarNovoId();
        Voo voo = new Voo(novoId, origem, destino, data, duracao, companhia, capacidade, preco);
        this.adicionarVoo(voo);
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