package model;

import java.time.LocalDateTime;

// CLASSE PAI - demonstra HERANÇA
public abstract class Entidade {
    protected int id; // ATRIBUTO PROTECTED
    protected LocalDateTime dataCriacao;
    protected LocalDateTime dataModificacao;

    public Entidade(int id) {
        this.id = id;
        this.dataCriacao = LocalDateTime.now();
        this.dataModificacao = LocalDateTime.now();
    }

    // GETTERS - ENCAPSULAMENTO
    public int getId() { return this.id; }
    public LocalDateTime getDataCriacao() { return this.dataCriacao; }
    public LocalDateTime getDataModificacao() { return this.dataModificacao; }

    // SETTER - ENCAPSULAMENTO
    protected void setDataModificacao() {
        this.dataModificacao = LocalDateTime.now();
    }

    // MÉTODO ABSTRATO - POLIMORFISMO
    public abstract String getDetalhes();
}
