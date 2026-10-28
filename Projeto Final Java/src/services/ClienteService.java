package services;

import dao.ClienteDao;
import exceptions.ClienteException;
import models.Cliente;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClienteService {

    private final ClienteDao clienteDao = new ClienteDao();

    public ArrayList<Cliente> listar() throws ClienteException {
        try {
            return clienteDao.listar();
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Cliente buscarPorId(Integer id) throws ClienteException {
        try {
            return clienteDao.buscarPorId(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Boolean cadastrar(Cliente cliente) throws ClienteException {
        validarDados(cliente);
        try {
            return clienteDao.inserir(cliente);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Boolean alterar(Cliente cliente) throws ClienteException {
        validarDados(cliente);
        try {
            return clienteDao.alterar(cliente);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    // Exclui o cliente e também os pedidos atrelados a ele
    public Boolean excluir(Integer id) throws ClienteException {
        try {
            return clienteDao.excluir(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    private void validarDados(Cliente cliente) throws ClienteException {
        String erros = "";
        if (vazio(cliente.getNome())) erros += "\n- Nome é um campo obrigatório";
        if (vazio(cliente.getTelefone())) erros += "\n- Telefone é um campo obrigatório";

        if (!erros.isEmpty()) {
            throw new ClienteException("Ops! Erros encontrados:" + erros);
        }
    }

    private boolean vazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private ClienteException erroBanco(SQLException e) {
        return new ClienteException("Ops! Erro no banco de dados: " + e.getMessage());
    }
}
