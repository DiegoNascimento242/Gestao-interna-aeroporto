package dao;

import model.Ticket;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements IDAO<Ticket> {

    @Override
    public int create(Ticket ticket) throws SQLException {
        String sql = "INSERT INTO ticket (valor, voo_id, passageiro_id, codigo, data_criacao, data_modificacao) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setDouble(1, ticket.getValor());
            stmt.setInt(2, ticket.getVooId());
            stmt.setInt(3, ticket.getPassageiroId());
            stmt.setString(4, ticket.getCodigo());
            stmt.setTimestamp(5, Timestamp.valueOf(ticket.getDataCriacao()));
            stmt.setTimestamp(6, Timestamp.valueOf(ticket.getDataModificacao()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                throw new SQLException("Falha ao criar ticket.");
            }
        }
    }

    @Override
    public Ticket readById(int id) throws SQLException {
        String sql = "SELECT * FROM ticket WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        }
        return null;
    }

    @Override
    public List<Ticket> readAll() throws SQLException {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket ORDER BY data_criacao DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) list.add(mapResultSet(rs));
        }
        return list;
    }

    @Override
    public boolean update(Ticket ticket) throws SQLException {
        String sql = "UPDATE ticket SET valor = ?, data_modificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, ticket.getValor());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, ticket.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM ticket WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Busca ticket por código.
     */
    public Ticket findByCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM ticket WHERE codigo = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        }
        return null;
    }

    /**
     * Busca tickets por passageiro.
     */
    public List<Ticket> findByPassageiro(int passageiroId) throws SQLException {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE passageiro_id = ? ORDER BY data_criacao DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, passageiroId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    /**
     * Busca tickets por voo.
     */
    public List<Ticket> findByVoo(int vooId) throws SQLException {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT * FROM ticket WHERE voo_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, vooId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    /**
     * Calcula valor total arrecadado por companhia em um período.
     */
    public double calcularArrecadacaoPorCompanhia(int companhiaId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        String sql = "SELECT SUM(t.valor) as total FROM ticket t " +
                     "JOIN voo v ON t.voo_id = v.id " +
                     "WHERE v.companhia_aerea_id = ? AND v.data_hora BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, companhiaId);
            stmt.setTimestamp(2, Timestamp.valueOf(dataInicio));
            stmt.setTimestamp(3, Timestamp.valueOf(dataFim));
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        }
        return 0.0;
    }

    private Ticket mapResultSet(ResultSet rs) throws SQLException {
        return new Ticket(
            rs.getInt("id"),
            rs.getDouble("valor"),
            rs.getInt("voo_id"),
            rs.getInt("passageiro_id"),
            rs.getString("codigo")
        );
    }
}
