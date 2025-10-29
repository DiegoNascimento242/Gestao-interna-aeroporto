package model;

import java.time.LocalDateTime;

public class Bagagem extends Entidade {
    private int ticketId;
    private String documento;
    private double peso;

    public Bagagem(int id, int ticketId, String documento, double peso) {
        super(id); // HERANÇA
        this.ticketId = ticketId;
        this.documento = documento;
        this.peso = peso;
    }

    // GETTERS - ENCAPSULAMENTO
    public int getTicketId() { return this.ticketId; }
    public String getDocumento() { return this.documento; }
    public double getPeso() { return this.peso; }

    @Override
    public String toString() {
        return String.format("Bagagem %d: Ticket %d - %.1f kg", this.id, this.ticketId, this.peso);
    }

    @Override
    public String getDetalhes() {
        return "Bagagem: Ticket " + this.ticketId + " - " + this.peso + " kg";
    }
}