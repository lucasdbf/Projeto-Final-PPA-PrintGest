package dao;

import connections.ConexaoSQL;
import models.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuncionarioDao {

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

    public Boolean inserir(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO tbfuncionario (nmfuncionario, nmcargo_funcionario) VALUES (?, ?)";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCargo());
            return ps.executeUpdate() > 0;
        }
    }

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

    public Boolean excluir(Integer id) throws SQLException {
        String sql = "DELETE FROM tbfuncionario WHERE cdfuncionario = ?";
        try (Connection conexao = ConexaoSQL.getConexao();
             PreparedStatement ps = conexao.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Funcionario mapear(ResultSet rs) throws SQLException {
        return new Funcionario(
                rs.getInt("cdfuncionario"),
                rs.getString("nmfuncionario"),
                rs.getString("nmcargo_funcionario")
        );
    }
}
