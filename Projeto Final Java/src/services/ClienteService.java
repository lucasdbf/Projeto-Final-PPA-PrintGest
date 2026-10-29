package services;

import dao.ClienteDao;
import exceptions.ClienteException;
import models.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Regras de negócio e validações de clientes (camada de serviço).
 */
public class ClienteService {

    private final ClienteDao clienteDao = new ClienteDao();

    /**
     * Lista todos os clientes cadastrados.
     * @return lista de clientes
     * @throws ClienteException se ocorrer erro no banco de dados
     */
    public ArrayList<Cliente> listar() throws ClienteException {
        try {
            return clienteDao.listar();
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    /**
     * Busca um cliente pelo ID.
     * @param id identificador do cliente
     * @return o cliente ou {@code null} se não existir
     * @throws ClienteException se ocorrer erro no banco de dados
     */
    public Cliente buscarPorId(Integer id) throws ClienteException {
        try {
            return clienteDao.buscarPorId(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    /**
     * Valida e cadastra um novo cliente.
     * @param cliente dados do cliente
     * @return {@code true} se o cadastro foi realizado
     * @throws ClienteException se os dados forem inválidos ou ocorrer erro no banco
     */
    public Boolean cadastrar(Cliente cliente) throws ClienteException {
        validarDados(cliente);
        try {
            return clienteDao.inserir(cliente);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    /**
     * Valida e altera os dados de um cliente existente.
     * @param cliente cliente com os dados alterados
     * @return {@code true} se a alteração foi realizada
     * @throws ClienteException se os dados forem inválidos ou ocorrer erro no banco
     */
    public Boolean alterar(Cliente cliente) throws ClienteException {
        validarDados(cliente);
        try {
            return clienteDao.alterar(cliente);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    // Exclui o cliente e também os pedidos atrelados a ele
    /**
     * Exclui um cliente pelo ID.
     * @param id identificador do cliente
     * @return {@code true} se a exclusão foi realizada
     * @throws ClienteException se ocorrer erro no banco de dados
     */
    public Boolean excluir(Integer id) throws ClienteException {
        try {
            return clienteDao.excluir(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    /**
     * Verifica os campos obrigatórios e reúne todos os erros em uma única mensagem.
     * @param cliente cliente a validar
     * @throws ClienteException com a lista de campos inválidos
     */
    private void validarDados(Cliente cliente) throws ClienteException {
        String erros = "";
        if (vazio(cliente.getNome())) erros += "\n- Nome é um campo obrigatório";
        if (vazio(cliente.getTelefone())) erros += "\n- Telefone é um campo obrigatório";

        if (!erros.isEmpty()) {
            throw new ClienteException("Ops! Erros encontrados:" + erros);
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
    private ClienteException erroBanco(SQLException e) {
        return new ClienteException("Ops! Erro no banco de dados: " + e.getMessage());
    }
}
