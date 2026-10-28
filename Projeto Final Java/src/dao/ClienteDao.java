package dao;

import connections.ConexaoSQL;
import models.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteDao {

    public ArrayList<Cliente> listar() throws SQLException {
        String sql = "SELECT cdcliente, nmcliente, nutelefone_cliente FROM tbcliente ORDER BY cdcliente";
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                clientes.add(mapear(rs));
            }
        }
        return clientes;
    }

    public Cliente buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT cdcliente, nmcliente, nutelefone_cliente FROM tbcliente WHERE cdcliente = ?";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public Boolean inserir(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO tbcliente (nmcliente, nutelefone_cliente) VALUES (?, ?)";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            return ps.executeUpdate() > 0;
        }
    }

    public Boolean alterar(Cliente cliente) throws SQLException {
        String sql = "UPDATE tbcliente SET nmcliente = ?, nutelefone_cliente = ? WHERE cdcliente = ?";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setInt(3, cliente.getId());
            return ps.executeUpdate() > 0;
        }
    }

    // Remove também os pedidos do cliente para não violar a chave estrangeira
    public Boolean excluir(Integer id) throws SQLException {
        try (Connection conexao = ConexaoSQL.getConexao()) {
            conexao.setAutoCommit(false);
            try (PreparedStatement psPedidos = conexao.prepareStatement("DELETE FROM tbpedido WHERE cdcliente = ?");
                 PreparedStatement psCliente = conexao.prepareStatement("DELETE FROM tbcliente WHERE cdcliente = ?")) {
                psPedidos.setInt(1, id);
                psPedidos.executeUpdate();
                psCliente.setInt(1, id);
                boolean excluiu = psCliente.executeUpdate() > 0;
                conexao.commit();
                return excluiu;
            } catch (SQLException e) {
                conexao.rollback();
                throw e;
            }
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("cdcliente"),
                rs.getString("nmcliente"),
                rs.getString("nutelefone_cliente")
        );
    }
}
