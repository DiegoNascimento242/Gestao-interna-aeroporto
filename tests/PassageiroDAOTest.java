import dao.PassageiroDAO;
import model.Passageiro;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testes de integração para PassageiroDAO.
 * Requer banco de dados local configurado.
 */
public class PassageiroDAOTest {
    
    private PassageiroDAO passageiroDAO;

    @Before
    public void setUp() {
        passageiroDAO = new PassageiroDAO();
    }

    @Test
    public void testCreatePassageiro() throws SQLException {
        Passageiro passageiro = new Passageiro(
            0,
            "Teste Usuario",
            LocalDate.of(1990, 1, 1),
            "99999999999",
            "teste.usuario",
            "senha123"
        );

        int id = passageiroDAO.create(passageiro);
        assertTrue("ID deve ser maior que 0", id > 0);

        // Limpar
        passageiroDAO.delete(id);
    }

    @Test
    public void testReadPassageiro() throws SQLException {
        // Criar passageiro de teste
        Passageiro passageiro = new Passageiro(
            0,
            "Teste Leitura",
            LocalDate.of(1995, 5, 15),
            "88888888888",
            "teste.leitura",
            "senha456"
        );

        int id = passageiroDAO.create(passageiro);

        // Ler passageiro
        Passageiro lido = passageiroDAO.readById(id);
        assertNotNull("Passageiro deve existir", lido);
        assertEquals("Nome deve ser igual", "Teste Leitura", lido.getNome());
        assertEquals("Documento deve ser igual", "88888888888", lido.getDocumento());

        // Limpar
        passageiroDAO.delete(id);
    }

    @Test
    public void testReadAll() throws SQLException {
        List<Passageiro> passageiros = passageiroDAO.readAll();
        assertNotNull("Lista não deve ser nula", passageiros);
        assertTrue("Deve haver pelo menos 1 passageiro", passageiros.size() >= 1);
    }

    @Test
    public void testUpdatePassageiro() throws SQLException {
        // Criar passageiro
        Passageiro passageiro = new Passageiro(
            0,
            "Teste Update",
            LocalDate.of(1992, 3, 10),
            "77777777777",
            "teste.update",
            "senha789"
        );

        int id = passageiroDAO.create(passageiro);
        Passageiro lido = passageiroDAO.readById(id);

        // Atualizar
        lido.setNome("Teste Update Modificado");
        boolean atualizado = passageiroDAO.update(lido);

        assertTrue("Atualização deve ser bem-sucedida", atualizado);

        // Verificar
        Passageiro verificado = passageiroDAO.readById(id);
        assertEquals("Nome deve estar atualizado", "Teste Update Modificado", verificado.getNome());

        // Limpar
        passageiroDAO.delete(id);
    }

    @Test
    public void testDeletePassageiro() throws SQLException {
        // Criar passageiro
        Passageiro passageiro = new Passageiro(
            0,
            "Teste Delete",
            LocalDate.of(1988, 12, 25),
            "66666666666",
            "teste.delete",
            "senha321"
        );

        int id = passageiroDAO.create(passageiro);

        // Deletar
        boolean deletado = passageiroDAO.delete(id);
        assertTrue("Deleção deve ser bem-sucedida", deletado);

        // Verificar
        Passageiro verificado = passageiroDAO.readById(id);
        assertNull("Passageiro não deve mais existir", verificado);
    }

    @Test
    public void testFindByDocumento() throws SQLException {
        // Usar passageiro existente do seed
        Passageiro passageiro = passageiroDAO.findByDocumento("12345678901");
        assertNotNull("Passageiro deve existir", passageiro);
        assertEquals("Nome deve ser João Silva", "João Silva", passageiro.getNome());
    }
}
