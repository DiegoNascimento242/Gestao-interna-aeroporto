package service;

import dao.*;
import model.*;
import util.DateUtil;

import java.sql.SQLException;
import java.time.LocalDateTime;

/*
 REGRA DE NEGÓCIO PRINCIPAL: check-in só pode ser feito até 24h antes do voo.
 */
public class CheckInService {
    
    private final CheckInDAO checkInDAO;
    private final TicketDAO ticketDAO;
    private final VooDAO vooDAO;
    private final BoardingPassDAO boardingPassDAO;
    private final PassageiroDAO passageiroDAO;
    private final AeroportoDAO aeroportoDAO;
    private final AssentoDAO assentoDAO;

    public CheckInService() {
        this.checkInDAO = new CheckInDAO();
        this.ticketDAO = new TicketDAO();
        this.vooDAO = new VooDAO();
        this.boardingPassDAO = new BoardingPassDAO();
        this.passageiroDAO = new PassageiroDAO();
        this.aeroportoDAO = new AeroportoDAO();
        this.assentoDAO = new AssentoDAO();
    }


    public BoardingPass realizarCheckIn(int ticketId, String documento) throws SQLException {
        // Verifica se ticket existe
        Ticket ticket = ticketDAO.readById(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket não encontrado.");
        }

        // Verifica se já existe check-in
        if (checkInDAO.existeCheckIn(ticketId)) {
            throw new IllegalStateException("Check-in já foi realizado para este ticket.");
        }

        // Busca o voo
        Voo voo = vooDAO.readById(ticket.getVooId());
        if (voo == null) {
            throw new IllegalArgumentException("Voo não encontrado.");
        }

        // REGRA DE NEGÓCIO: verifica se está dentro de 24 horas antes do voo
        LocalDateTime agora = LocalDateTime.now();
        long horasAteVoo = DateUtil.hoursBetween(agora, voo.getDataHora());
        
        if (horasAteVoo > 24) {
            throw new IllegalStateException(
                String.format("Check-in só pode ser feito até 24 horas antes do voo. Faltam %d horas.", horasAteVoo)
            );
        }

        if (horasAteVoo < 0) {
            throw new IllegalStateException("O voo já partiu. Check-in não é mais possível.");
        }

        // Cria o check-in
        CheckIn checkIn = new CheckIn(0, ticketId, documento, "");
        checkInDAO.create(checkIn);

        // Gera boarding pass
        Passageiro passageiro = passageiroDAO.readById(ticket.getPassageiroId());
        Aeroporto origem = aeroportoDAO.readById(voo.getOrigemId());
        Aeroporto destino = aeroportoDAO.readById(voo.getDestinoId());

        // Busca assento do passageiro
        String codigoAssento = "N/A";
        for (Assento assento : assentoDAO.findByVoo(voo.getId())) {
            if (assento.getPassageiroId() != null && assento.getPassageiroId() == passageiro.getId()) {
                codigoAssento = assento.getCodigoAssento();
                break;
            }
        }

        String vooInfo = String.format("%s → %s (%s)", 
            origem.getAbreviacao(), destino.getAbreviacao(), voo.getDataHora());

        BoardingPass boardingPass = new BoardingPass(
            0, ticketId, passageiro.getNome(), vooInfo, codigoAssento
        );
        
        int bpId = boardingPassDAO.create(boardingPass);
        return boardingPassDAO.readById(bpId);
    }

    /*
     Verifica se check-in pode ser realizado para um ticket.
     */
    public boolean podeRealizarCheckIn(int ticketId) throws SQLException {
        Ticket ticket = ticketDAO.readById(ticketId);
        if (ticket == null) return false;

        if (checkInDAO.existeCheckIn(ticketId)) return false;

        Voo voo = vooDAO.readById(ticket.getVooId());
        if (voo == null) return false;

        return DateUtil.isDentro24Horas(LocalDateTime.now(), voo.getDataHora());
    }
}
