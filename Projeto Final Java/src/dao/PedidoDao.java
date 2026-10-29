package dao;

import connections.ConexaoSQL;
import models.Cliente;
import models.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Acesso ao banco de dados para pedidos (camada DAO). É a única classe do módulo que escreve SQL.
 */
public class PedidoDao {

    private static final String SELECT = "SELECT p.cdpedido, p.nome_papel, p.altura_rotulo, p.largura_rotulo, "
            + "p.qntd_rotulos, p.qntd_folha, p.valor, c.cdcliente, c.nmcliente, c.nutelefone_cliente "
            + "FROM tbpedido p JOIN tbcliente c ON c.cdcliente = p.cdcliente ";

    /**
     * Busca todos os pedidos cadastrados no banco, ordenados pelo ID.
     * @return lista de pedidos (vazia se não houver nenhum)
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public ArrayList<Pedido> listar() throws SQLException {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(SELECT + "ORDER BY p.cdpedido");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pedidos.add(mapear(rs));
            }
        }
        return pedidos;
    }

    /**
     * Busca um pedido pelo ID.
     * @param id identificador do pedido
     * @return o pedido encontrado ou {@code null} se não existir
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public Pedido buscarPorId(Integer id) throws SQLException {
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(SELECT + "WHERE p.cdpedido = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    /**
     * Insere um novo pedido no banco.
     * @param pedido dados do pedido a cadastrar
     * @return {@code true} se o registro foi inserido
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public Boolean inserir(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO tbpedido (nome_papel, altura_rotulo, largura_rotulo, qntd_rotulos, qntd_folha, valor, cdcliente) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, pedido.getNomePapel());
            ps.setDouble(2, pedido.getAlturaRotulo());
            ps.setDouble(3, pedido.getLarguraRotulo());
            ps.setInt(4, pedido.getQntdRotulos());
            ps.setDouble(5, pedido.getQntdFolha());
            ps.setDouble(6, pedido.getValor());
            ps.setInt(7, pedido.getCliente().getId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Atualiza os dados de um pedido existente, identificado pelo ID.
     * @param pedido pedido com os dados já alterados
     * @return {@code true} se algum registro foi atualizado
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public Boolean alterar(Pedido pedido) throws SQLException {
        String sql = "UPDATE tbpedido SET nome_papel = ?, altura_rotulo = ?, largura_rotulo = ?, qntd_rotulos = ?, "
                + "qntd_folha = ?, valor = ?, cdcliente = ? WHERE cdpedido = ?";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, pedido.getNomePapel());
            ps.setDouble(2, pedido.getAlturaRotulo());
            ps.setDouble(3, pedido.getLarguraRotulo());
            ps.setInt(4, pedido.getQntdRotulos());
            ps.setDouble(5, pedido.getQntdFolha());
            ps.setDouble(6, pedido.getValor());
            ps.setInt(7, pedido.getCliente().getId());
            ps.setInt(8, pedido.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Exclui um pedido pelo ID.
     * @param id identificador do pedido
     * @return {@code true} se algum registro foi excluído
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public Boolean excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM tbpedido WHERE cdpedido = ?";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Converte a linha atual do {@code ResultSet} em um objeto {@link Pedido}.
     * @param rs resultado da consulta posicionado em uma linha
     * @return o objeto montado com os dados da linha
     * @throws SQLException se uma coluna não puder ser lida
     */
    private Pedido mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente(
                rs.getInt("cdcliente"),
                rs.getString("nmcliente"),
                rs.getString("nutelefone_cliente")
        );
        return new Pedido(
                rs.getInt("cdpedido"),
                rs.getString("nome_papel"),
                rs.getDouble("altura_rotulo"),
                rs.getDouble("largura_rotulo"),
                rs.getInt("qntd_rotulos"),
                rs.getDouble("qntd_folha"),
                rs.getDouble("valor"),
                cliente
        );
    }
}
