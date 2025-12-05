package dao;

import model.Voo;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VooDAO implements IDAO<Voo> {

    @Override
    public int create(Voo voo) throws SQLException {
        String sql = "INSERT INTO voo (origem_id, destino_id, data_hora, duracao_minutos, companhia_aerea_id, capacidade, estado, data_criacao, data_modificacao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, voo.getOrigemId());
            stmt.setInt(2, voo.getDestinoId());
            stmt.setTimestamp(3, Timestamp.valueOf(voo.getDataHora()));
            stmt.setInt(4, voo.getDuracaoMinutos());
            stmt.setInt(5, voo.getCompanhiaAereaId());
            stmt.setInt(6, voo.getCapacidade());
            stmt.setString(7, voo.getEstado());
            stmt.setTimestamp(8, Timestamp.valueOf(voo.getDataCriacao()));
            stmt.setTimestamp(9, Timestamp.valueOf(voo.getDataModificacao()));
            
            stmt.executeUpdate();
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) return generatedKeys.getInt(1);
                throw new SQLException("Falha ao criar voo.");
            }
        }
    }

    @Override
    public Voo readById(int id) throws SQLException {
        String sql = "SELECT * FROM voo WHERE id = ?";
        
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
    public List<Voo> readAll() throws SQLException {
        List<Voo> list = new ArrayList<>();
        String sql = "SELECT * FROM voo ORDER BY data_hora";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) list.add(mapResultSet(rs));
        }
        return list;
    }

    @Override
    public boolean update(Voo voo) throws SQLException {
        String sql = "UPDATE voo SET estado = ?, data_modificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, voo.getEstado());
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(3, voo.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM voo WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /*
     * Busca voos por origem e destino em uma faixa de datas.
     */
    public List<Voo> buscarVoos(int origemId, int destinoId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        List<Voo> list = new ArrayList<>();
        String sql = "SELECT * FROM voo WHERE origem_id = ? AND destino_id = ? AND data_hora BETWEEN ? AND ? ORDER BY data_hora";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, origemId);
            stmt.setInt(2, destinoId);
            stmt.setTimestamp(3, Timestamp.valueOf(dataInicio));
            stmt.setTimestamp(4, Timestamp.valueOf(dataFim));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    /*
     * Busca voos por aeroporto de origem em um período.
     */
    public List<Voo> buscarPorOrigem(int origemId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        List<Voo> list = new ArrayList<>();
        String sql = "SELECT * FROM voo WHERE origem_id = ? AND data_hora BETWEEN ? AND ? ORDER BY data_hora";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, origemId);
            stmt.setTimestamp(2, Timestamp.valueOf(dataInicio));
            stmt.setTimestamp(3, Timestamp.valueOf(dataFim));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    /*
      Busca voos por aeroporto de destino em um período.
     */
    public List<Voo> buscarPorDestino(int destinoId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        List<Voo> list = new ArrayList<>();
        String sql = "SELECT * FROM voo WHERE destino_id = ? AND data_hora BETWEEN ? AND ? ORDER BY data_hora";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, destinoId);
            stmt.setTimestamp(2, Timestamp.valueOf(dataInicio));
            stmt.setTimestamp(3, Timestamp.valueOf(dataFim));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    /*
      Busca voos por companhia aérea em um período.
     */
    public List<Voo> buscarPorCompanhia(int companhiaId, LocalDateTime dataInicio, LocalDateTime dataFim) throws SQLException {
        List<Voo> list = new ArrayList<>();
        String sql = "SELECT * FROM voo WHERE companhia_aerea_id = ? AND data_hora BETWEEN ? AND ? ORDER BY data_hora";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, companhiaId);
            stmt.setTimestamp(2, Timestamp.valueOf(dataInicio));
            stmt.setTimestamp(3, Timestamp.valueOf(dataFim));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) list.add(mapResultSet(rs));
            }
        }
        return list;
    }

    private Voo mapResultSet(ResultSet rs) throws SQLException {
        return new Voo(
            rs.getInt("id"),
            rs.getInt("origem_id"),
            rs.getInt("destino_id"),
            rs.getTimestamp("data_hora").toLocalDateTime(),
            rs.getInt("duracao_minutos"),
            rs.getInt("companhia_aerea_id"),
            rs.getInt("capacidade"),
            rs.getString("estado")
        );
    }
}
