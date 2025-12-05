package dao;

import model.EntradaAeroporto;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EntradaAeroportoDAO implements IDAO<EntradaAeroporto> {

    @Override
    public int create(EntradaAeroporto entrada) throws SQLException {
        String sql = "INSERT INTO entrada_aeroporto (passageiro_id, data_entrada, data_criacao, data_modificacao) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, entrada.getPassageiroId());
            stmt.setTimestamp(2, Timestamp.valueOf(entrada.getDataEntrada()));
            stmt.setTimestamp(3, Timestamp.valueOf(entrada.getDataCriacao()));
            stmt.setTimestamp(4, Timestamp.valueOf(entrada.getDataModificacao()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                throw new SQLException("Falha ao registrar entrada no aeroporto.");
            }
        }
    }

    @Override
    public EntradaAeroporto readById(int id) throws SQLException {
        String sql = "SELECT * FROM entrada_aeroporto WHERE id = ?";
        
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
    public List<EntradaAeroporto> readAll() throws SQLException {
        List<EntradaAeroporto> list = new ArrayList<>();
        String sql = "SELECT * FROM entrada_aeroporto ORDER BY data_entrada DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) list.add(mapResultSet(rs));
        }
        return list;
    }

    @Override
    public boolean update(EntradaAeroporto entrada) throws SQLException {
        String sql = "UPDATE entrada_aeroporto SET data_entrada = ?, data_modificacao = ? WHERE id = ?";
        
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
        String sql = "DELETE FROM entrada_aeroporto WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private EntradaAeroporto mapResultSet(ResultSet rs) throws SQLException {
        return new EntradaAeroporto(
            rs.getInt("id"),
            rs.getInt("passageiro_id"),
            rs.getTimestamp("data_entrada").toLocalDateTime()
        );
    }
}
