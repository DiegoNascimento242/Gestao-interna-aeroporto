package dao;

import model.Assento;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AssentoDAO implements IDAO<Assento> {

    @Override
    public int create(Assento assento) throws SQLException {
        String sql = "INSERT INTO voo_assento (voo_id, codigo_assento, passageiro_id, data_criacao, data_modificacao) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, assento.getVooId());
            stmt.setString(2, assento.getCodigoAssento());
            if (assento.getPassageiroId() != null) {
                stmt.setInt(3, assento.getPassageiroId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setTimestamp(4, Timestamp.valueOf(assento.getDataCriacao()));
            stmt.setTimestamp(5, Timestamp.valueOf(assento.getDataModificacao()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                throw new SQLException("Falha ao criar assento.");
            }
        }
    }

    @Override
    public Assento readById(int id) throws SQLException {
        String sql = "SELECT * FROM voo_assento WHERE id = ?";
        
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
    public List<Assento> readAll() throws SQLException {
        List<Assento> list = new ArrayList<>();
        String sql = "SELECT * FROM voo_assento";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) list.add(mapResultSet(rs));
        }
        return list;
    }

    @Override
    public boolean update(Assento assento) throws SQLException {
        String sql = "UPDATE voo_assento SET passageiro_id = ?, data_modificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (assento.getPassageiroId() != null) {
                stmt.setInt(1, assento.getPassageiroId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, assento.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM voo_assento WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Busca todos os assentos de um voo.
     */
    public List<Assento> findByVoo(int vooId) throws SQLException {
        List<Assento> list = new ArrayList<>();
        String sql = "SELECT * FROM voo_assento WHERE voo_id = ? ORDER BY codigo_assento";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, vooId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }


    public List<Assento> findAssentosLivres(int vooId) throws SQLException {
        List<Assento> list = new ArrayList<>();
        String sql = "SELECT * FROM voo_assento WHERE voo_id = ? AND passageiro_id IS NULL ORDER BY codigo_assento";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, vooId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }


    public boolean atribuirPassageiro(int assentoId, int passageiroId) throws SQLException {
        String sql = "UPDATE voo_assento SET passageiro_id = ?, data_modificacao = ? WHERE id = ? AND passageiro_id IS NULL";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, passageiroId);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, assentoId);
            
            return stmt.executeUpdate() > 0;
        }
    }

    private Assento mapResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int vooId = rs.getInt("voo_id");
        String codigoAssento = rs.getString("codigo_assento");
        
        Assento assento = new Assento(id, vooId, codigoAssento);
        
        int passageiroId = rs.getInt("passageiro_id");
        if (!rs.wasNull()) {
            assento.setPassageiroId(passageiroId);
        }
        
        return assento;
    }
}
