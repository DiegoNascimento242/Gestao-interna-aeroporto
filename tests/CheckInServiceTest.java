import dao.*;
import model.*;
import service.CheckInService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Testes para regras de negócio do check-in.
 * REGRA PRINCIPAL: check-in só pode ser feito até 24h antes do voo.
 */
public class CheckInServiceTest {
    
    private CheckInService checkInService;
    private VooDAO vooDAO;
    private TicketDAO ticketDAO;
    private PassageiroDAO passageiroDAO;
    private AeroportoDAO aeroportoDAO;
    private CompanhiaAereaDAO companhiaDAO;
    private AssentoDAO assentoDAO;

    @Before
    public void setUp() {
        checkInService = new CheckInService();
        vooDAO = new VooDAO();
        ticketDAO = new TicketDAO();
        passageiroDAO = new PassageiroDAO();
        aeroportoDAO = new AeroportoDAO();
        companhiaDAO = new CompanhiaAereaDAO();
        assentoDAO = new AssentoDAO();
    }

    @Test
    public void testCheckInForaDoPrazo() throws SQLException {
        // Criar voo com data muito futura (mais de 24h)
        LocalDateTime dataVooFuturo = LocalDateTime.now().plusDays(5);
        
        Voo voo = new Voo(0, 1, 3, dataVooFuturo, 60, 1, 10, "PROGRAMADO");
        int vooId = vooDAO.create(voo);

        // Criar ticket
        Ticket ticket = new Ticket(0, 500.0, vooId, 1, "TEST1");
        int ticketId = ticketDAO.create(ticket);

        // Tentar check-in (deve falhar)
        try {
            checkInService.realizarCheckIn(ticketId, "12345678901");
            fail("Deveria lançar exceção: check-in fora do prazo");
        } catch (IllegalStateException e) {
            assertTrue("Mensagem deve mencionar 24 horas", e.getMessage().contains("24 horas"));
        }

        // Limpar
        ticketDAO.delete(ticketId);
        vooDAO.delete(vooId);
    }

    @Test
    public void testCheckInDentroDoPrazo() throws SQLException {
        // Criar voo com data próxima (dentro de 24h)
        LocalDateTime dataVooProximo = LocalDateTime.now().plusHours(12);
        
        Voo voo = new Voo(0, 1, 3, dataVooProximo, 60, 1, 10, "PROGRAMADO");
        int vooId = vooDAO.create(voo);

        // Criar assentos
        Assento assento = new Assento(0, vooId, "1A");
        int assentoId = assentoDAO.create(assento);
        assentoDAO.atribuirPassageiro(assentoId, 1);

        // Criar ticket
        Ticket ticket = new Ticket(0, 500.0, vooId, 1, "TEST2");
        int ticketId = ticketDAO.create(ticket);

        // Realizar check-in (deve funcionar)
        BoardingPass bp = checkInService.realizarCheckIn(ticketId, "12345678901");
        assertNotNull("Boarding pass deve ser criado", bp);
        assertEquals("Passageiro deve ser João Silva", "João Silva", bp.getPassageiroNome());

        // Limpar
        ticketDAO.delete(ticketId);
        assentoDAO.delete(assentoId);
        vooDAO.delete(vooId);
    }

    @Test
    public void testCheckInDuplicado() throws SQLException {
        // Usar ticket existente que já tem check-in
        // Neste caso, vamos criar um novo cenário controlado
        
        LocalDateTime dataVooProximo = LocalDateTime.now().plusHours(10);
        Voo voo = new Voo(0, 1, 3, dataVooProximo, 60, 1, 10, "PROGRAMADO");
        int vooId = vooDAO.create(voo);

        Assento assento = new Assento(0, vooId, "2B");
        int assentoId = assentoDAO.create(assento);
        assentoDAO.atribuirPassageiro(assentoId, 2);

        Ticket ticket = new Ticket(0, 600.0, vooId, 2, "TEST3");
        int ticketId = ticketDAO.create(ticket);

        // Primeiro check-in (deve funcionar)
        BoardingPass bp1 = checkInService.realizarCheckIn(ticketId, "98765432109");
        assertNotNull("Primeiro check-in deve funcionar", bp1);

        // Segundo check-in (deve falhar)
        try {
            checkInService.realizarCheckIn(ticketId, "98765432109");
            fail("Deveria lançar exceção: check-in duplicado");
        } catch (IllegalStateException e) {
            assertTrue("Mensagem deve mencionar check-in já realizado", 
                      e.getMessage().contains("já foi realizado"));
        }

        // Limpar
        ticketDAO.delete(ticketId);
        assentoDAO.delete(assentoId);
        vooDAO.delete(vooId);
    }
}
