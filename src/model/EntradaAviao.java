package model;

import java.time.LocalDateTime;

/*
  Classe que representa a entrada de um passageiro no avião.
 HERANÇA: herda de Entidade
  ENCAPSULAMENTO: atributos privados com getters/setters
 */
public class EntradaAviao extends Entidade {
    private int passageiroId;
    private int vooId;
    private LocalDateTime dataEntrada;

    public EntradaAviao(int id, int passageiroId, int vooId, LocalDateTime dataEntrada) {
        super(id);
        this.passageiroId = passageiroId;
        this.vooId = vooId;
        this.dataEntrada = dataEntrada;
    }

    // GETTERS - ENCAPSULAMENTO
    public int getPassageiroId() {
        return passageiroId;
    }

    public int getVooId() {
        return vooId;
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
        return String.format("Entrada no Avião - Passageiro ID: %d, Voo ID: %d, Data: %s", 
                           passageiroId, vooId, dataEntrada);
    }

    @Override
    public String toString() {
        return String.format("EntradaAviao{id=%d, passageiroId=%d, vooId=%d, dataEntrada=%s}",
                           id, passageiroId, vooId, dataEntrada);
    }
}
