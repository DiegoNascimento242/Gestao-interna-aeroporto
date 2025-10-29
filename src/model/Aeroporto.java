package model;

// HERANÇA - Aeroporto herda de Entidade
public class Aeroporto extends Entidade {
    private String nome;
    private String abreviacao;
    private String cidade;

    public Aeroporto(int id, String nome, String abreviacao, String cidade) {
        super(id); // HERANÇA
        this.nome = nome;
        this.abreviacao = abreviacao;
        this.cidade = cidade;
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

    public String getCidade() { return this.cidade; }
    public void setCidade(String cidade) {
        this.cidade = cidade;
        this.setDataModificacao(); // USO DO THIS
    }

    @Override
    public String toString() {
        return "Aeroporto{id=" + this.id + ", nome='" + this.nome + "', cidade='" + this.cidade + "'}";
    }

    @Override
    public String getDetalhes() {
        return "Aeroporto: " + this.nome + " (" + this.abreviacao + ") - " + this.cidade;
    }
}