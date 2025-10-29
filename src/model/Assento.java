package model;

import java.time.LocalDateTime;

public class Assento extends Entidade {
    private int vooId;
    private String codigoAssento;
    private Integer passageiroId; // null se estiver livre

    public Assento(int id, int vooId, String codigoAssento) {
        super(id); // HERANÇA
        this.vooId = vooId;
        this.codigoAssento = codigoAssento;
        this.passageiroId = null;
    }

    // GETTERS E SETTERS - ENCAPSULAMENTO
    public int getVooId() { return this.vooId; }
    public String getCodigoAssento() { return this.codigoAssento; }
    public Integer getPassageiroId() { return this.passageiroId; }
    public void setPassageiroId(Integer passageiroId) {
        this.passageiroId = passageiroId;
        this.setDataModificacao(); // USO DO THIS
    }

    public boolean estaOcupado() {
        return this.passageiroId != null;
    }

    @Override
    public String toString() {
        return String.format("Assento %s (Voo %d) - %s",
                this.codigoAssento, this.vooId, this.estaOcupado() ? "Ocupado" : "Livre");
    }

    @Override
    public String getDetalhes() {
        return "Assento: " + this.codigoAssento + " - Voo " + this.vooId + " - " +
                (this.estaOcupado() ? "Ocupado" : "Livre");
    }
}