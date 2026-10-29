package dao;

import connections.ConexaoSQL;
import models.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Acesso ao banco de dados para funcionários (camada DAO). É a única classe do módulo que escreve SQL.
 */
public class FuncionarioDao {

    /**
     * Busca todos os funcionários cadastrados no banco, ordenados pelo ID.
     * @return lista de funcionários (vazia se não houver nenhum)
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public ArrayList<Funcionario> listar() throws SQLException {
        String sql = "SELECT cdfuncionario, nmfuncionario, nmcargo_funcionario FROM tbfuncionario ORDER BY cdfuncionario";
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                funcionarios.add(mapear(rs));
            }
        }
        return funcionarios;
    }

    /**
     * Busca um funcionário pelo ID.
     * @param id identificador do funcionário
     * @return o funcionário encontrado ou {@code null} se não existir
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public Funcionario buscarPorId(Integer id) throws SQLException {
        String sql = "SELECT cdfuncionario, nmfuncionario, nmcargo_funcionario FROM tbfuncionario WHERE cdfuncionario = ?";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    /**
     * Insere um novo funcionário no banco.
     * @param funcionario dados do funcionário a cadastrar
     * @return {@code true} se o registro foi inserido
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public Boolean inserir(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO tbfuncionario (nmfuncionario, nmcargo_funcionario) VALUES (?, ?)";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCargo());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Atualiza os dados de um funcionário existente, identificado pelo ID.
     * @param funcionario funcionário com os dados já alterados
     * @return {@code true} se algum registro foi atualizado
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public Boolean alterar(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE tbfuncionario SET nmfuncionario = ?, nmcargo_funcionario = ? WHERE cdfuncionario = ?";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCargo());
            ps.setInt(3, funcionario.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Exclui um funcionário pelo ID.
     * @param id identificador do funcionário
     * @return {@code true} se algum registro foi excluído
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public Boolean excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM tbfuncionario WHERE cdfuncionario = ?";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Converte a linha atual do {@code ResultSet} em um objeto {@link Funcionario}.
     * @param rs resultado da consulta posicionado em uma linha
     * @return o objeto montado com os dados da linha
     * @throws SQLException se uma coluna não puder ser lida
     */
    private Funcionario mapear(ResultSet rs) throws SQLException {
        return new Funcionario(
                rs.getInt("cdfuncionario"),
                rs.getString("nmfuncionario"),
                rs.getString("nmcargo_funcionario")
        );
    }
}
