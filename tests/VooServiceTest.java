import dao.*;
import model.*;
import service.VooService;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Testes para regras de negócio de voos.
 * REGRA PRINCIPAL: não permitir venda quando voo estiver lotado.
 */
public class VooServiceTest {
    
    private VooService vooService;
    private VooDAO vooDAO;
    private AssentoDAO assentoDAO;

    @Before
    public void setUp() {
        vooService = new VooService();
        vooDAO = new VooDAO();
        assentoDAO = new AssentoDAO();
    }

    @Test
    public void testVooLotado() throws SQLException {
        // Criar voo com capacidade pequena (2 assentos)
        LocalDateTime dataVoo = LocalDateTime.now().plusDays(2);
        Voo voo = new Voo(0, 1, 3, dataVoo, 60, 1, 2, "PROGRAMADO");
        int vooId = vooDAO.create(voo);

        // Criar 2 assentos
        Assento assento1 = new Assento(0, vooId, "1A");
        int assentoId1 = assentoDAO.create(assento1);
        
        Assento assento2 = new Assento(0, vooId, "1B");
        int assentoId2 = assentoDAO.create(assento2);

        // Ocupar os 2 assentos
        assentoDAO.atribuirPassageiro(assentoId1, 1);
        assentoDAO.atribuirPassageiro(assentoId2, 2);

        // Verificar se voo está lotado
        boolean lotado = vooService.vooEstaLotado(vooId);
        assertTrue("Voo deve estar lotado", lotado);

        // Tentar comprar ticket (deve falhar)
        try {
            vooService.comprarTicket(vooId, 3, 400.0, null);
            fail("Deveria lançar exceção: voo lotado");
        } catch (IllegalStateException e) {
            assertTrue("Mensagem deve mencionar voo lotado", 
                      e.getMessage().contains("lotado"));
        }

        // Limpar
        assentoDAO.delete(assentoId1);
        assentoDAO.delete(assentoId2);
        vooDAO.delete(vooId);
    }

    @Test
    public void testCompraComSucesso() throws SQLException {
        // Criar voo com assentos disponíveis
        LocalDateTime dataVoo = LocalDateTime.now().plusDays(3);
        Voo voo = new Voo(0, 1, 3, dataVoo, 60, 1, 5, "PROGRAMADO");
        int vooId = vooDAO.create(voo);

        // Criar assentos
        for (int i = 1; i <= 5; i++) {
            Assento assento = new Assento(0, vooId, i + "A");
            assentoDAO.create(assento);
        }

        // Comprar ticket (deve funcionar)
        Ticket ticket = vooService.comprarTicket(vooId, 1, 350.0, null);
        assertNotNull("Ticket deve ser criado", ticket);
        assertNotNull("Código do ticket deve existir", ticket.getCodigo());
        assertEquals("Valor deve ser 350.0", 350.0, ticket.getValor(), 0.01);

        // Limpar
        TicketDAO ticketDAO = new TicketDAO();
        ticketDAO.delete(ticket.getId());
        
        // Limpar assentos
        for (Assento a : assentoDAO.findByVoo(vooId)) {
            assentoDAO.delete(a.getId());
        }
        
        vooDAO.delete(vooId);
    }

    @Test
    public void testBuscarVoosDisponiveis() throws SQLException {
        // Buscar voos existentes do seed
        LocalDateTime dataInicio = LocalDateTime.of(2025, 12, 1, 0, 0);
        LocalDateTime dataFim = LocalDateTime.of(2025, 12, 31, 23, 59);

        var voos = vooService.buscarVoosDisponiveis(1, 3, dataInicio, dataFim);
        assertNotNull("Lista de voos não deve ser nula", voos);
        // Deve haver pelo menos 1 voo GRU->GIG no seed
    }
}
