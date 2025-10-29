package model;

import java.time.LocalDate;
import java.time.LocalTime;

// HERANÇA - Voo herda de Entidade
public class Voo extends Entidade {
    private String origem;
    private String destino;
    private LocalDate data;
    private LocalTime duracao;
    private String companhia;
    private int capacidade;
    private String estado;
    private int assentosOcupados;
    private double preco; // DOUBLE para dinheiro

    public Voo(int id, String origem, String destino, LocalDate data, LocalTime duracao,
               String companhia, int capacidade, double preco) {
        super(id); // HERANÇA
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.duracao = duracao;
        this.companhia = companhia;
        this.capacidade = capacidade;
        this.preco = preco;
        this.estado = "Programado";
        this.assentosOcupados = 0;
    }

    // GETTERS E SETTERS - ENCAPSULAMENTO
    public String getOrigem() { return this.origem; }
    public String getDestino() { return this.destino; }
    public LocalDate getData() { return this.data; }
    public LocalTime getDuracao() { return this.duracao; }
    public String getCompanhia() { return this.companhia; }
    public int getCapacidade() { return this.capacidade; }
    public String getEstado() { return this.estado; }
    public void setEstado(String estado) {
        this.estado = estado;
        this.setDataModificacao(); // USO DO THIS
    }
    public int getAssentosOcupados() { return this.assentosOcupados; }
    public double getPreco() { return this.preco; } // DOUBLE para dinheiro
    public void setPreco(double preco) {
        this.preco = preco;
        this.setDataModificacao(); // USO DO THIS
    }

    public boolean reservarAssento() {
        if (this.assentosOcupados < this.capacidade) {
            this.assentosOcupados++;
            this.setDataModificacao(); // USO DO THIS
            return true;
        }
        return false;
    }

    public boolean temAssentosDisponiveis() {
        return this.assentosOcupados < this.capacidade;
    }

    public int getAssentosDisponiveis() {
        return this.capacidade - this.assentosOcupados;
    }

    // REESCRITA - @OVERRIDE
    @Override
    public String toString() {
        return String.format("Voo %d: %s -> %s (%s) - %s - R$ %.2f - %d/%d assentos",
                this.id, this.origem, this.destino, this.data, this.companhia, this.preco,
                this.assentosOcupados, this.capacidade);
    }

    // POLIMORFISMO - implementação do método abstrato
    @Override
    public String getDetalhes() {
        return "Voo: " + this.origem + " para " + this.destino + " - " + this.companhia + " - " + this.estado;
    }
}