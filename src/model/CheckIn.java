package model;

import java.time.LocalDateTime;

public class CheckIn extends Entidade {
    private int ticketId;
    private String documento;
    private String codigoBoardingPass;

    public CheckIn(int id, int ticketId, String documento, String codigoBoardingPass) {
        super(id); // HERANÇA
        this.ticketId = ticketId;
        this.documento = documento;
        this.codigoBoardingPass = codigoBoardingPass;
    }

    // GETTERS - ENCAPSULAMENTO
    public int getTicketId() { return this.ticketId; }
    public String getDocumento() { return this.documento; }
    public String getCodigoBoardingPass() { return this.codigoBoardingPass; }

    @Override
    public String toString() {
        return String.format("CheckIn %d: Ticket %d - Documento %s", this.id, this.ticketId, this.documento);
    }

    @Override
    public String getDetalhes() {
        return "Check-in: Ticket " + this.ticketId + " - " + this.documento;
    }
}