package dao;

import model.Aeroporto;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class AeroportoDAO implements IDAO<Aeroporto> {

    @Override
    public int create(Aeroporto aeroporto) throws SQLException {
        String sql = "INSERT INTO aeroporto (nome, abreviacao, cidade, data_criacao, data_modificacao) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, aeroporto.getNome());
            stmt.setString(2, aeroporto.getAbreviacao());
            stmt.setString(3, aeroporto.getCidade());
            stmt.setTimestamp(4, Timestamp.valueOf(aeroporto.getDataCriacao()));
            stmt.setTimestamp(5, Timestamp.valueOf(aeroporto.getDataModificacao()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                throw new SQLException("Falha ao criar aeroporto.");
            }
        }
    }

    @Override
    public Aeroporto readById(int id) throws SQLException {
        String sql = "SELECT * FROM aeroporto WHERE id = ?";
        
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
    public List<Aeroporto> readAll() throws SQLException {
        List<Aeroporto> list = new ArrayList<>();
        String sql = "SELECT * FROM aeroporto ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) list.add(mapResultSet(rs));
        }
        return list;
    }

    @Override
    public boolean update(Aeroporto aeroporto) throws SQLException {
        String sql = "UPDATE aeroporto SET nome = ?, abreviacao = ?, cidade = ?, data_modificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, aeroporto.getNome());
            stmt.setString(2, aeroporto.getAbreviacao());
            stmt.setString(3, aeroporto.getCidade());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, aeroporto.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM aeroporto WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public Aeroporto findByAbreviacao(String abreviacao) throws SQLException {
        String sql = "SELECT * FROM aeroporto WHERE abreviacao = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, abreviacao);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }
        }
        return null;
    }

    private Aeroporto mapResultSet(ResultSet rs) throws SQLException {
        return new Aeroporto(
            rs.getInt("id"),
            rs.getString("nome"),
            rs.getString("abreviacao"),
            rs.getString("cidade")
        );
    }
}
