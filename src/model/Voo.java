package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

// HERANÇA - Voo herda de Entidade
public class Voo extends Entidade {
    private int origemId; // ID do aeroporto de origem
    private int destinoId; // ID do aeroporto de destino
    private LocalDateTime dataHora; // Data e hora do voo
    private int duracaoMinutos; // Duração em minutos
    private int companhiaAereaId; // ID da companhia aérea
    private int capacidade;
    private String estado;
    private int assentosOcupados;
    private double preco; // DOUBLE para dinheiro


    public Voo(int id, int origemId, int destinoId, LocalDateTime dataHora, int duracaoMinutos,
               int companhiaAereaId, int capacidade, String estado) {
        super(id); // HERANÇA
        this.origemId = origemId;
        this.destinoId = destinoId;
        this.dataHora = dataHora;
        this.duracaoMinutos = duracaoMinutos;
        this.companhiaAereaId = companhiaAereaId;
        this.capacidade = capacidade;
        this.estado = estado;
        this.assentosOcupados = 0;
        this.preco = 0.0; // Será definido pelo ticket
    }

    // GETTERS E SETTERS - ENCAPSULAMENTO
    public int getOrigemId() { return this.origemId; }
    public int getDestinoId() { return this.destinoId; }
    public LocalDateTime getDataHora() { return this.dataHora; }
    public int getDuracaoMinutos() { return this.duracaoMinutos; }
    public int getCompanhiaAereaId() { return this.companhiaAereaId; }
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
        return String.format("Voo %d: Origem=%d Destino=%d Data=%s Estado=%s Assentos=%d/%d",
                this.id, this.origemId, this.destinoId, this.dataHora,
                this.estado, this.assentosOcupados, this.capacidade);
    }

    // POLIMORFISMO - implementação do método abstrato
    @Override
    public String getDetalhes() {
        return String.format("Voo ID=%d: Origem=%d Destino=%d Data=%s Estado=%s",
                this.id, this.origemId, this.destinoId, this.dataHora, this.estado);
    }

    // Método para obter horas até o voo
    public long getHorasAteVoo() {
        return java.time.temporal.ChronoUnit.HOURS.between(
                LocalDateTime.now(), this.dataHora
        );
    }
}