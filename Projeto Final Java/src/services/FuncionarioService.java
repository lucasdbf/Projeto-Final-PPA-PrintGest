package services;

import dao.FuncionarioDao;
import exceptions.FuncionarioException;
import models.Funcionario;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Regras de negócio e validações de funcionários (camada de serviço).
 */
public class FuncionarioService {

    private final FuncionarioDao funcionarioDao = new FuncionarioDao();

    /**
     * Lista todos os funcionários cadastrados.
     * @return lista de funcionários
     * @throws FuncionarioException se ocorrer erro no banco de dados
     */
    public ArrayList<Funcionario> listar() throws FuncionarioException {
        try {
            return funcionarioDao.listar();
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    /**
     * Busca um funcionário pelo ID.
     * @param id identificador do funcionário
     * @return o funcionário ou {@code null} se não existir
     * @throws FuncionarioException se ocorrer erro no banco de dados
     */
    public Funcionario buscarPorId(Integer id) throws FuncionarioException {
        try {
            return funcionarioDao.buscarPorId(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    /**
     * Valida e cadastra um novo funcionário.
     * @param funcionario dados do funcionário
     * @return {@code true} se o cadastro foi realizado
     * @throws FuncionarioException se os dados forem inválidos ou ocorrer erro no banco
     */
    public Boolean cadastrar(Funcionario funcionario) throws FuncionarioException {
        validarDados(funcionario);
        try {
            return funcionarioDao.inserir(funcionario);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    /**
     * Valida e altera os dados de um funcionário existente.
     * @param funcionario funcionário com os dados alterados
     * @return {@code true} se a alteração foi realizada
     * @throws FuncionarioException se os dados forem inválidos ou ocorrer erro no banco
     */
    public Boolean alterar(Funcionario funcionario) throws FuncionarioException {
        validarDados(funcionario);
        try {
            return funcionarioDao.alterar(funcionario);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    /**
     * Exclui um funcionário pelo ID.
     * @param id identificador do funcionário
     * @return {@code true} se a exclusão foi realizada
     * @throws FuncionarioException se ocorrer erro no banco de dados
     */
    public Boolean excluir(Integer id) throws FuncionarioException {
        try {
            return funcionarioDao.excluir(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    /**
     * Verifica os campos obrigatórios e reúne todos os erros em uma única mensagem.
     * @param funcionario funcionário a validar
     * @throws FuncionarioException com a lista de campos inválidos
     */
    private void validarDados(Funcionario funcionario) throws FuncionarioException {
        String erros = "";
        if (vazio(funcionario.getNome())) erros += "\n- Nome é um campo obrigatório";
        if (vazio(funcionario.getCargo())) erros += "\n- Cargo é um campo obrigatório";

        if (!erros.isEmpty()) {
            throw new FuncionarioException("Ops! Erros encontrados:" + erros);
        }
    }

    /**
     * Indica se o texto é nulo ou contém apenas espaços.
     * @param texto texto a verificar
     * @return {@code true} se estiver vazio
     */
    private boolean vazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    /**
     * Converte um erro de SQL em uma exceção de negócio com mensagem legível.
     * @param e erro original do banco
     * @return exceção pronta para ser lançada
     */
    private FuncionarioException erroBanco(SQLException e) {
        return new FuncionarioException("Ops! Erro no banco de dados: " + e.getMessage());
    }
}
