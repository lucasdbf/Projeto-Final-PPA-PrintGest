package services;

import dao.FuncionarioDao;
import exceptions.FuncionarioException;
import models.Funcionario;

import java.sql.SQLException;
import java.util.ArrayList;

public class FuncionarioService {

    private final FuncionarioDao funcionarioDao = new FuncionarioDao();

    public ArrayList<Funcionario> listar() throws FuncionarioException {
        try {
            return funcionarioDao.listar();
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Funcionario buscarPorId(Integer id) throws FuncionarioException {
        try {
            return funcionarioDao.buscarPorId(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Boolean cadastrar(Funcionario funcionario) throws FuncionarioException {
        validarDados(funcionario);
        try {
            return funcionarioDao.inserir(funcionario);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Boolean alterar(Funcionario funcionario) throws FuncionarioException {
        validarDados(funcionario);
        try {
            return funcionarioDao.alterar(funcionario);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Boolean excluir(Integer id) throws FuncionarioException {
        try {
            return funcionarioDao.excluir(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    private void validarDados(Funcionario funcionario) throws FuncionarioException {
        String erros = "";
        if (vazio(funcionario.getNome())) erros += "\n- Nome é um campo obrigatório";
        if (vazio(funcionario.getCargo())) erros += "\n- Cargo é um campo obrigatório";

        if (!erros.isEmpty()) {
            throw new FuncionarioException("Ops! Erros encontrados:" + erros);
        }
    }

    private boolean vazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private FuncionarioException erroBanco(SQLException e) {
        return new FuncionarioException("Ops! Erro no banco de dados: " + e.getMessage());
    }
}
