package service;

import dao.CheckInDAO;
import model.CheckIn;
import model.Ticket;
import model.Voo;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

// CRUD COM DAO
public class CheckInService {
    private CheckInDAO checkInDAO;
    private TicketService ticketService;
    private VooService vooService;

    public CheckInService() {
        this.checkInDAO = new CheckInDAO();
        this.ticketService = new TicketService();
        this.vooService = new VooService();
    }

    public boolean realizarCheckin(int ticketId, String documento, String codigoBoardingPass) {
        Ticket ticket = ticketService.buscarTicketPorId(ticketId);
        if (ticket == null) {
            return false;
        }

        // Verificar se o check-in está sendo feito até 24 horas antes do voo
        Voo voo = vooService.buscarVooPorId(ticket.getVooId());
        if (voo == null) {
            return false;
        }

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime dataVoo = voo.getData().atStartOfDay();
        long horasAteVoo = ChronoUnit.HOURS.between(agora, dataVoo);

        if (horasAteVoo < 24) {
            return false; // Check-in só pode ser feito até 24h antes
        }

        // Criar check-in
        int novoId = this.gerarNovoId();
        CheckIn checkin = new CheckIn(novoId, ticketId, documento, codigoBoardingPass);
        this.checkInDAO.criar(checkin);

        // Marcar ticket como com check-in realizado
        ticketService.realizarCheckin(ticketId);

        return true;
    }

    public CheckIn buscarCheckinPorTicketId(int ticketId) {
        return this.checkInDAO.buscarPorTicketId(ticketId);
    }

    public CheckIn buscarCheckinPorId(int id) {
        return this.checkInDAO.buscarPorId(id);
    }

    public CheckIn[] listarCheckins() {
        return this.checkInDAO.listarTodos();
    }

    private int gerarNovoId() {
        CheckIn[] todos = this.listarCheckins();
        int maiorId = 0;
        for (CheckIn c : todos) {
            if (c.getId() > maiorId) {
                maiorId = c.getId();
            }
        }
        return maiorId + 1;
    }
}