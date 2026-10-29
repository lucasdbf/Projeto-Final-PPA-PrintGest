package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Centraliza a conexão JDBC com o banco PostgreSQL {@code projeto_final}.
 */
public class ConexaoSQL {
    private static final String URL = "jdbc:postgresql://localhost:5432/projeto_final";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "Saclu@1803";

    /**
     * Abre uma nova conexão com o banco de dados.
     * @return conexão aberta (quem chama deve fechá-la)
     * @throws SQLException se o banco estiver inacessível ou as credenciais forem inválidas
     */
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


}