package model;

import java.time.LocalDate;

// HERANÇA - Passageiro herda de Entidade
public class Passageiro extends Entidade {
    private String nome;
    private LocalDate nascimento;
    private String documento;
    private String login;
    private String senha;

    public Passageiro(int id, String nome, LocalDate nascimento, String documento, String login, String senha) {
        super(id); // USO DO SUPER - HERANÇA
        this.nome = nome;
        this.nascimento = nascimento;
        this.documento = documento;
        this.login = login;
        this.senha = senha;
    }

    // GETTERS E SETTERS - ENCAPSULAMENTO
    public String getNome() { return this.nome; }
    public void setNome(String nome) {
        this.nome = nome;
        this.setDataModificacao(); // USO DO THIS
    }

    public LocalDate getNascimento() { return this.nascimento; }
    public String getDocumento() { return this.documento; }
    public String getLogin() { return this.login; }
    public void setLogin(String login) {
        this.login = login;
        this.setDataModificacao(); // USO DO THIS
    }
    public String getSenha() { return this.senha; }
    public void setSenha(String senha) {
        this.senha = senha;
        this.setDataModificacao(); // USO DO THIS
    }

    // REESCRITA - @OVERRIDE
    @Override
    public String toString() {
        return "Passageiro{id=" + this.id + ", nome='" + this.nome + "', documento='" + this.documento + "'}";
    }

    // POLIMORFISMO - implementação do método abstrato
    @Override
    public String getDetalhes() {
        return "Passageiro: " + this.nome + " (Doc: " + this.documento + ")";
    }
}