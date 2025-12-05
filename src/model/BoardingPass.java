package model;

import java.time.LocalDateTime;

public class BoardingPass extends Entidade {
    private int ticketId;
    private String passageiroNome;
    private String vooInfo;
    private String assento;

    public BoardingPass(int id, int ticketId, String passageiroNome, String vooInfo, String assento) {
        super(id); // HERANÇA
        this.ticketId = ticketId;
        this.passageiroNome = passageiroNome;
        this.vooInfo = vooInfo;
        this.assento = assento;
    }

    // GETTERS - ENCAPSULAMENTO
    public int getTicketId() { return this.ticketId; }
    public String getPassageiroNome() { return this.passageiroNome; }
    public String getVooInfo() { return this.vooInfo; }
    public String getAssento() { return this.assento; }

    @Override
    public String toString() {
        return String.format("BoardingPass %d: %s - Voo: %s - Assento %s", this.id, this.passageiroNome, this.vooInfo, this.assento);
    }

    @Override
    public String getDetalhes() {
        return "Boarding Pass: " + this.passageiroNome + " - Voo: " + this.vooInfo + " - Assento " + this.assento;
    }

    public String gerarBoardingPassCompleto() {
        return String.format(
                "=== BOARDING PASS ===\n" +
                        "Passageiro: %s\n" +
                        "Voo: %s\n" +
                        "Assento: %s\n" +
                        "Ticket: %d\n" +
                        "Data: %02d/%02d/%04d\n" +
                        "=====================",
                this.passageiroNome, this.vooInfo, this.assento, this.ticketId,
                this.dataCriacao.getDayOfMonth(), this.dataCriacao.getMonthValue(), this.dataCriacao.getYear()
        );
    }
}