package tests;

import models.Cliente;
import models.Pedido;
import services.ClienteService;
import services.PedidoService;

import java.util.ArrayList;

/**
 * Testes automatizados de pedidos: cadastrar, listar, alterar e excluir no banco.
 */
public class PedidoTest {

    private final PedidoService pedidoService = new PedidoService();
    private final ClienteService clienteService = new ClienteService();
    private Cliente cliente;
    private Pedido criado;

    /**
     * Executa todos os testes do módulo de pedidos na ordem: cadastrar, listar, alterar e excluir.
     */
    public void iniciar() {
        System.out.println("=== TESTES PEDIDO ===");
        try {
            clienteService.cadastrar(new Cliente("Cliente Teste Pedido", "123"));
            ArrayList<Cliente> clientes = clienteService.listar();
            cliente = clientes.get(clientes.size() - 1);
        } catch (Exception e) {
            System.err.println("Preparação: Falhou");
            return;
        }
        cadastrar();
        listar();
        alterar();
        excluir();
        // Remove o cliente de apoio (e quaisquer pedidos restantes)
        try {
            clienteService.excluir(cliente.getId());
        } catch (Exception e) {
            System.err.println("Limpeza: Falhou");
        }
    }

    /**
     * Teste de cadastro: grava um pedido de teste e confere se foi inserido.
     */
    public void cadastrar() {
        try {
            Boolean cadastrou = pedidoService.cadastrar(
                    new Pedido("BOPP Branco", 5.0, 3.0, 100, null, null, cliente));
            ArrayList<Pedido> pedidos = pedidoService.listar();
            criado = pedidos.get(pedidos.size() - 1);
            resultado("Cadastrar", cadastrou && criado.getValor() > 0);
        } catch (Exception e) {
            resultado("Cadastrar", false);
        }
    }

    /**
     * Teste de listagem: confere se há pedidos cadastrados.
     */
    public void listar() {
        try {
            resultado("Listar", !pedidoService.listar().isEmpty());
        } catch (Exception e) {
            resultado("Listar", false);
        }
    }

    /**
     * Teste de alteração: altera o pedido criado no teste de cadastro.
     */
    public void alterar() {
        try {
            criado.setQntdRotulos(500);
            resultado("Alterar", pedidoService.alterar(criado));
        } catch (Exception e) {
            resultado("Alterar", false);
        }
    }

    /**
     * Teste de exclusão: remove o pedido criado no teste de cadastro.
     */
    public void excluir() {
        try {
            resultado("Excluir", pedidoService.excluir(criado.getId()));
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
