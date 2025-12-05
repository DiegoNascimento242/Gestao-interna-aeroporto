package dao;

import model.CheckIn;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckInDAO implements IDAO<CheckIn> {

    @Override
    public int create(CheckIn checkIn) throws SQLException {
        String sql = "INSERT INTO checkin (ticket_id, documento, data_criacao, data_modificacao) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, checkIn.getTicketId());
            stmt.setString(2, checkIn.getDocumento());
            stmt.setTimestamp(3, Timestamp.valueOf(checkIn.getDataCriacao()));
            stmt.setTimestamp(4, Timestamp.valueOf(checkIn.getDataModificacao()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                throw new SQLException("Falha ao criar check-in.");
            }
        }
    }

    @Override
    public CheckIn readById(int id) throws SQLException {
        String sql = "SELECT * FROM checkin WHERE id = ?";
        
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
    public List<CheckIn> readAll() throws SQLException {
        List<CheckIn> list = new ArrayList<>();
        String sql = "SELECT * FROM checkin ORDER BY data_criacao DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) list.add(mapResultSet(rs));
        }
        return list;
    }

    @Override
    public boolean update(CheckIn checkIn) throws SQLException {
        String sql = "UPDATE checkin SET documento = ?, data_modificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, checkIn.getDocumento());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, checkIn.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM checkin WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }


    public CheckIn findByTicket(int ticketId) throws SQLException {
        String sql = "SELECT * FROM checkin WHERE ticket_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, ticketId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        }
        return null;
    }


    public boolean existeCheckIn(int ticketId) throws SQLException {
        return findByTicket(ticketId) != null;
    }

    private CheckIn mapResultSet(ResultSet rs) throws SQLException {
        return new CheckIn(
            rs.getInt("id"),
            rs.getInt("ticket_id"),
            rs.getString("documento"),
            "" // codigoBoardingPass será gerado depois
        );
    }
}
