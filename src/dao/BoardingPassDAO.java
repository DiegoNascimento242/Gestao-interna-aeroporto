package dao;

import model.BoardingPass;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BoardingPassDAO implements IDAO<BoardingPass> {

    @Override
    public int create(BoardingPass bp) throws SQLException {
        String sql = "INSERT INTO boarding_pass (ticket_id, passageiro_nome, voo_info, assento, data_criacao, data_modificacao) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, bp.getTicketId());
            stmt.setString(2, bp.getPassageiroNome());
            stmt.setString(3, bp.getVooInfo());
            stmt.setString(4, bp.getAssento());
            stmt.setTimestamp(5, Timestamp.valueOf(bp.getDataCriacao()));
            stmt.setTimestamp(6, Timestamp.valueOf(bp.getDataModificacao()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                throw new SQLException("Falha ao criar boarding pass.");
            }
        }
    }

    @Override
    public BoardingPass readById(int id) throws SQLException {
        String sql = "SELECT * FROM boarding_pass WHERE id = ?";
        
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
    public List<BoardingPass> readAll() throws SQLException {
        List<BoardingPass> list = new ArrayList<>();
        String sql = "SELECT * FROM boarding_pass ORDER BY data_criacao DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) list.add(mapResultSet(rs));
        }
        return list;
    }

    @Override
    public boolean update(BoardingPass bp) throws SQLException {
        String sql = "UPDATE boarding_pass SET assento = ?, data_modificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, bp.getAssento());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, bp.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM boarding_pass WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public BoardingPass findByTicket(int ticketId) throws SQLException {
        String sql = "SELECT * FROM boarding_pass WHERE ticket_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ticketId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        }
        return null;
    }

    private BoardingPass mapResultSet(ResultSet rs) throws SQLException {
        return new BoardingPass(
            rs.getInt("id"),
            rs.getInt("ticket_id"),
            rs.getString("passageiro_nome"),
            rs.getString("voo_info"),
            rs.getString("assento")
        );
    }
}
