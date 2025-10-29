package service;

import dao.PassageiroDAO;
import model.Passageiro;
import java.time.LocalDate;

// CRUD COM DAO - implementa INTERFACE
public class PassageiroService implements IService<Passageiro> {
    private PassageiroDAO passageiroDAO;

    public PassageiroService() {
        this.passageiroDAO = new PassageiroDAO();
    }

    // POLIMORFISMO através da interface
    public void cadastrar(Passageiro passageiro) {
        this.passageiroDAO.criar(passageiro);
    }

    public Passageiro[] listarTodos() {
        return this.passageiroDAO.listarTodos();
    }

    public Passageiro buscarPorId(int id) {
        return this.passageiroDAO.buscarPorId(id);
    }

    public void atualizar(int id, Passageiro passageiro) {
        this.passageiroDAO.atualizar(id, passageiro);
    }

    public void excluir(int id) {
        this.passageiroDAO.deletar(id);
    }

    // Métodos específicos do Passageiro
    public Passageiro buscarPorDocumento(String documento) {
        return this.passageiroDAO.buscarPorDocumento(documento);
    }

    public void criarPassageiro(String nome, String documento, String login, String senha, LocalDate nascimento) {
        int novoId = this.gerarNovoId();
        Passageiro passageiro = new Passageiro(novoId, nome, nascimento, documento, login, senha);
        this.cadastrar(passageiro);
    }

    private int gerarNovoId() {
        Passageiro[] todos = this.listarTodos();
        int maiorId = 0;
        for (Passageiro p : todos) {
            if (p.getId() > maiorId) {
                maiorId = p.getId();
            }
        }
        return maiorId + 1;
    }

    public boolean fazerLogin(String login, String senha) {
        Passageiro[] passageiros = this.listarTodos();
        for (Passageiro passageiro : passageiros) {
            if (passageiro.getLogin().equals(login) && passageiro.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }
}