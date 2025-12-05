package dao;

import model.EntradaAviao;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntradaAviaoDAO implements IDAO<EntradaAviao> {

    @Override
    public int create(EntradaAviao entrada) throws SQLException {
        String sql = "INSERT INTO entrada_aviao (passageiro_id, voo_id, data_entrada, data_criacao, data_modificacao) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, entrada.getPassageiroId());
            stmt.setInt(2, entrada.getVooId());
            stmt.setTimestamp(3, Timestamp.valueOf(entrada.getDataEntrada()));
            stmt.setTimestamp(4, Timestamp.valueOf(entrada.getDataCriacao()));
            stmt.setTimestamp(5, Timestamp.valueOf(entrada.getDataModificacao()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                throw new SQLException("Falha ao registrar entrada no avião.");
            }
        }
    }

    @Override
    public EntradaAviao readById(int id) throws SQLException {
        String sql = "SELECT * FROM entrada_aviao WHERE id = ?";
        
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
    public List<EntradaAviao> readAll() throws SQLException {
        List<EntradaAviao> list = new ArrayList<>();
        String sql = "SELECT * FROM entrada_aviao ORDER BY data_entrada DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) list.add(mapResultSet(rs));
        }
        return list;
    }

    @Override
    public boolean update(EntradaAviao entrada) throws SQLException {
        String sql = "UPDATE entrada_aviao SET data_entrada = ?, data_modificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(entrada.getDataEntrada()));
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, entrada.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM entrada_aviao WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<EntradaAviao> findByVoo(int vooId) throws SQLException {
        List<EntradaAviao> list = new ArrayList<>();
        String sql = "SELECT * FROM entrada_aviao WHERE voo_id = ? ORDER BY data_entrada";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, vooId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    private EntradaAviao mapResultSet(ResultSet rs) throws SQLException {
        return new EntradaAviao(
            rs.getInt("id"),
            rs.getInt("passageiro_id"),
            rs.getInt("voo_id"),
            rs.getTimestamp("data_entrada").toLocalDateTime()
        );
    }
}
