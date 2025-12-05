package model;

import java.time.LocalDateTime;

/*
  Classe que representa a entrada de um passageiro no aeroporto.
  HERANÇA: herda de Entidade
  ENCAPSULAMENTO: atributos privados com getters/setters
 */
public class EntradaAeroporto extends Entidade {
    private int passageiroId;
    private LocalDateTime dataEntrada;

    public EntradaAeroporto(int id, int passageiroId, LocalDateTime dataEntrada) {
        super(id);
        this.passageiroId = passageiroId;
        this.dataEntrada = dataEntrada;
    }

    // GETTERS - ENCAPSULAMENTO
    public int getPassageiroId() {
        return passageiroId;
    }

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    // SETTER
    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
        this.setDataModificacao();
    }

    // POLIMORFISMO - implementação do método abstrato
    @Override
    public String getDetalhes() {
        return String.format("Entrada no Aeroporto - Passageiro ID: %d, Data: %s", 
                           passageiroId, dataEntrada);
    }

    @Override
    public String toString() {
        return String.format("EntradaAeroporto{id=%d, passageiroId=%d, dataEntrada=%s}",
                           id, passageiroId, dataEntrada);
    }
}
