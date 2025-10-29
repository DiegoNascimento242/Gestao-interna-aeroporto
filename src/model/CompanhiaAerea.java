package model;

import java.time.LocalDateTime;

// HERANÇA - CompanhiaAerea herda de Entidade
public class CompanhiaAerea extends Entidade {
    private String nome;
    private String abreviacao;

    public CompanhiaAerea(int id, String nome, String abreviacao) {
        super(id); // HERANÇA
        this.nome = nome;
        this.abreviacao = abreviacao;
    }

    // GETTERS E SETTERS - ENCAPSULAMENTO
    public String getNome() { return this.nome; }
    public void setNome(String nome) {
        this.nome = nome;
        this.setDataModificacao(); // USO DO THIS
    }

    public String getAbreviacao() { return this.abreviacao; }
    public void setAbreviacao(String abreviacao) {
        this.abreviacao = abreviacao;
        this.setDataModificacao(); // USO DO THIS
    }

    @Override
    public String toString() {
        return "CompanhiaAerea{id=" + this.id + ", nome='" + this.nome + "', abreviacao='" + this.abreviacao + "'}";
    }

    @Override
    public String getDetalhes() {
        return "Companhia Aérea: " + this.nome + " (" + this.abreviacao + ")";
    }
}