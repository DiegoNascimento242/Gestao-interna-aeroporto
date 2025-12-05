package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnection {
    
    private static String DB_HOST;
    private static String DB_PORT;
    private static String DB_NAME;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static String DB_URL;
    
    private static boolean configured = false;


    private static void loadConfiguration() {
        if (configured) {
            return;
        }
        
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            
            DB_HOST = props.getProperty("db.host", "127.0.0.1");
            DB_PORT = props.getProperty("db.port", "3306");
            DB_NAME = props.getProperty("db.name", "aeroporto_db");
            DB_USER = props.getProperty("db.user", "root");
            DB_PASSWORD = props.getProperty("db.password", "");
            
            DB_URL = String.format(
                "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                DB_HOST, DB_PORT, DB_NAME
            );
            
            configured = true;
            
        } catch (IOException e) {
            System.err.println("ERRO: Não foi possível carregar config.properties");
            System.err.println("Usando configurações padrão: localhost:3306, user=root, password=''");
            
            // Configurações padrão
            DB_HOST = "127.0.0.1";
            DB_PORT = "3306";
            DB_NAME = "aeroporto_db";
            DB_USER = "root";
            DB_PASSWORD = "";
            DB_URL = String.format(
                "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                DB_HOST, DB_PORT, DB_NAME
            );
            
            configured = true;
        }
    }


    public static Connection getConnection() throws SQLException {
        loadConfiguration();
        
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado. Verifique as dependências.", e);
        }
    }


    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Erro ao testar conexão: " + e.getMessage());
            return false;
        }
    }


    public static String getConfigInfo() {
        loadConfiguration();
        return String.format("MySQL: %s:%s/%s (user: %s)", DB_HOST, DB_PORT, DB_NAME, DB_USER);
    }
}
