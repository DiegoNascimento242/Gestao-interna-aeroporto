package dao;

import model.CompanhiaAerea;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompanhiaAereaDAO implements IDAO<CompanhiaAerea> {

    @Override
    public int create(CompanhiaAerea cia) throws SQLException {
        String sql = "INSERT INTO companhia_aerea (nome, abreviacao, data_criacao, data_modificacao) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cia.getNome());
            stmt.setString(2, cia.getAbreviacao());
            stmt.setTimestamp(3, Timestamp.valueOf(cia.getDataCriacao()));
            stmt.setTimestamp(4, Timestamp.valueOf(cia.getDataModificacao()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                throw new SQLException("Falha ao criar companhia aérea.");
            }
        }
    }

    @Override
    public CompanhiaAerea readById(int id) throws SQLException {
        String sql = "SELECT * FROM companhia_aerea WHERE id = ?";
        
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
    public List<CompanhiaAerea> readAll() throws SQLException {
        List<CompanhiaAerea> list = new ArrayList<>();
        String sql = "SELECT * FROM companhia_aerea ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) list.add(mapResultSet(rs));
        }
        return list;
    }

    @Override
    public boolean update(CompanhiaAerea cia) throws SQLException {
        String sql = "UPDATE companhia_aerea SET nome = ?, abreviacao = ?, data_modificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cia.getNome());
            stmt.setString(2, cia.getAbreviacao());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(4, cia.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM companhia_aerea WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public CompanhiaAerea findByAbreviacao(String abreviacao) throws SQLException {
        String sql = "SELECT * FROM companhia_aerea WHERE abreviacao = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, abreviacao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        }
        return null;
    }

    private CompanhiaAerea mapResultSet(ResultSet rs) throws SQLException {
        return new CompanhiaAerea(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("abreviacao")
        );
    }
}
