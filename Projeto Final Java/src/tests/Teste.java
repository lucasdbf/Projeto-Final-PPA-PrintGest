package tests;

import connections.ConexaoSQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Executa os testes automatizados de todos os módulos contra o banco de dados.
 */
public class Teste {

    /**
     * Executa os testes de clientes, funcionários e pedidos em sequência.
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        new ClienteTest().iniciar();
        new FuncionarioTest().iniciar();
        new PedidoTest().iniciar();
        restaurarSequencias();
    }

    /**
     * Devolve cada sequência de ID ao primeiro número livre (maior ID existente + 1).
     * Sem isso, os registros criados e apagados pelos testes fariam os IDs
     * seguintes "pularem" números.
     */
    private static void restaurarSequencias() {
        String[][] tabelas = {
                { "tbcliente", "cdcliente" },
                { "tbfuncionario", "cdfuncionario" },
                { "tbpedido", "cdpedido" }
        };
        try (Connection conexao = ConexaoSQL.getConexao();
             Statement st = conexao.createStatement()) {
            for (String[] t : tabelas) {
                st.execute("SELECT setval(pg_get_serial_sequence('" + t[0] + "', '" + t[1] + "'), "
                        + "COALESCE((SELECT MAX(" + t[1] + ") FROM " + t[0] + "), 0) + 1, false)");
            }
            System.out.println("Sequências de ID restauradas.");
        } catch (SQLException e) {
            System.err.println("Não foi possível restaurar as sequências: " + e.getMessage());
        }
    }
}
