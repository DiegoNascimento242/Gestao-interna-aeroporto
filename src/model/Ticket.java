package model;

import java.time.LocalDateTime;

public class Ticket extends Entidade {
    private double valor;
    private int vooId;
    private int passageiroId;
    private String codigo;
    private boolean checkinRealizado;

    public Ticket(int id, double valor, int vooId, int passageiroId, String codigo) {
        super(id); // HERANÇA
        this.valor = valor;
        this.vooId = vooId;
        this.passageiroId = passageiroId;
        this.codigo = codigo;
        this.checkinRealizado = false;
    }

    // GETTERS E SETTERS - ENCAPSULAMENTO
    public double getValor() { return this.valor; }
    public int getVooId() { return this.vooId; }
    public int getPassageiroId() { return this.passageiroId; }
    public String getCodigo() { return this.codigo; }
    public boolean isCheckinRealizado() { return this.checkinRealizado; }
    public void setCheckinRealizado(boolean checkinRealizado) {
        this.checkinRealizado = checkinRealizado;
        this.setDataModificacao(); // USO DO THIS
    }

    @Override
    public String toString() {
        return String.format("Ticket %s: Voo %d - Passageiro %d - R$ %.2f - Check-in: %s",
                this.codigo, this.vooId, this.passageiroId, this.valor,
                this.checkinRealizado ? "Sim" : "Não");
    }

    @Override
    public String getDetalhes() {
        return "Ticket: " + this.codigo + " - Voo " + this.vooId + " - R$ " + this.valor;
    }
}