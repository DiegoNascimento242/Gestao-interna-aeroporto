package dao;

import model.Passageiro;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class PassageiroDAO implements IDAO<Passageiro> {


    @Override
    public int create(Passageiro passageiro) throws SQLException {
        String sql = "INSERT INTO passageiro (nome, nascimento, documento, login, senha, data_criacao, data_modificacao) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, passageiro.getNome());
            stmt.setDate(2, Date.valueOf(passageiro.getNascimento()));
            stmt.setString(3, passageiro.getDocumento());
            stmt.setString(4, passageiro.getLogin());
            stmt.setString(5, passageiro.getSenha());
            stmt.setTimestamp(6, Timestamp.valueOf(passageiro.getDataCriacao()));
            stmt.setTimestamp(7, Timestamp.valueOf(passageiro.getDataModificacao()));
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao criar passageiro, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao criar passageiro, ID não obtido.");
                }
            }
        }
    }


    @Override
    public Passageiro readById(int id) throws SQLException {
        String sql = "SELECT * FROM passageiro WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPassageiro(rs);
                }
            }
        }
        return null;
    }


    @Override
    public List<Passageiro> readAll() throws SQLException {
        List<Passageiro> passageiros = new ArrayList<>();
        String sql = "SELECT * FROM passageiro ORDER BY nome";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                passageiros.add(mapResultSetToPassageiro(rs));
            }
        }
        return passageiros;
    }


    @Override
    public boolean update(Passageiro passageiro) throws SQLException {
        String sql = "UPDATE passageiro SET nome = ?, login = ?, senha = ?, data_modificacao = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, passageiro.getNome());
            stmt.setString(2, passageiro.getLogin());
            stmt.setString(3, passageiro.getSenha());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, passageiro.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }


    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM passageiro WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }


    public Passageiro findByDocumento(String documento) throws SQLException {
        String sql = "SELECT * FROM passageiro WHERE documento = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, documento);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPassageiro(rs);
                }
            }
        }
        return null;
    }


    public Passageiro findByLogin(String login) throws SQLException {
        String sql = "SELECT * FROM passageiro WHERE login = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, login);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPassageiro(rs);
                }
            }
        }
        return null;
    }


    private Passageiro mapResultSetToPassageiro(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        LocalDate nascimento = rs.getDate("nascimento").toLocalDate();
        String documento = rs.getString("documento");
        String login = rs.getString("login");
        String senha = rs.getString("senha");
        
        return new Passageiro(id, nome, nascimento, documento, login, senha);
    }
}
