package model;

import java.time.LocalDateTime;

public class BoardingPass extends Entidade {
    private int ticketId;
    private String codigo;
    private String assento;
    private String portaoEmbarque;

    public BoardingPass(int id, int ticketId, String codigo, String assento, String portaoEmbarque) {
        super(id); // HERANÇA
        this.ticketId = ticketId;
        this.codigo = codigo;
        this.assento = assento;
        this.portaoEmbarque = portaoEmbarque;
    }

    // GETTERS - ENCAPSULAMENTO
    public int getTicketId() { return this.ticketId; }
    public String getCodigo() { return this.codigo; }
    public String getAssento() { return this.assento; }
    public String getPortaoEmbarque() { return this.portaoEmbarque; }

    @Override
    public String toString() {
        return String.format("BoardingPass %s: Assento %s - Portão %s", this.codigo, this.assento, this.portaoEmbarque);
    }

    @Override
    public String getDetalhes() {
        return "Boarding Pass: " + this.codigo + " - Assento " + this.assento + " - Portão " + this.portaoEmbarque;
    }

    public String gerarBoardingPassCompleto() {
        return String.format(
                "=== BOARDING PASS ===\n" +
                        "Código: %s\n" +
                        "Assento: %s\n" +
                        "Portão: %s\n" +
                        "Ticket: %d\n" +
                        "=====================",
                this.codigo, this.assento, this.portaoEmbarque, this.ticketId
        );
    }
}