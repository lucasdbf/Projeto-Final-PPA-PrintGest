package tests;

import models.Cliente;
import services.ClienteService;

import java.util.ArrayList;

/**
 * Testes automatizados de clientes: cadastrar, listar, alterar e excluir no banco.
 */
public class ClienteTest {

    private final ClienteService clienteService = new ClienteService();
    private Cliente criado;

    /**
     * Executa todos os testes do módulo de clientes na ordem: cadastrar, listar, alterar e excluir.
     */
    public void iniciar() {
        System.out.println("=== TESTES CLIENTE ===");
        cadastrar();
        listar();
        alterar();
        excluir();
    }

    /**
     * Teste de cadastro: grava um cliente de teste e confere se foi inserido.
     */
    public void cadastrar() {
        try {
            Boolean cadastrou = clienteService.cadastrar(new Cliente("Teste", "123"));
            ArrayList<Cliente> clientes = clienteService.listar();
            criado = clientes.get(clientes.size() - 1);
            resultado("Cadastrar", cadastrou);
        } catch (Exception e) {
            resultado("Cadastrar", false);
        }
    }

    /**
     * Teste de listagem: confere se há clientes cadastrados.
     */
    public void listar() {
        try {
            resultado("Listar", !clienteService.listar().isEmpty());
        } catch (Exception e) {
            resultado("Listar", false);
        }
    }

    /**
     * Teste de alteração: altera o cliente criado no teste de cadastro.
     */
    public void alterar() {
        try {
            criado.setNome("Teste Alterado");
            resultado("Alterar", clienteService.alterar(criado));
        } catch (Exception e) {
            resultado("Alterar", false);
        }
    }

    /**
     * Teste de exclusão: remove o cliente criado no teste de cadastro.
     */
    public void excluir() {
        try {
            resultado("Excluir", clienteService.excluir(criado.getId()));
        } catch (Exception e) {
            resultado("Excluir", false);
        }
    }

    /**
     * Imprime "Passou" ou "Falhou" para o teste.
     * @param teste nome do teste
     * @param passou resultado obtido
     */
    private void resultado(String teste, Boolean passou) {
        if (passou) {
            System.out.println(teste + ": Passou");
        } else {
            System.err.println(teste + ": Falhou");
        }
    }
}
