package tests;

import models.Cliente;
import services.ClienteService;

import java.util.ArrayList;

public class ClienteTest {

    private final ClienteService clienteService = new ClienteService();
    private Cliente criado;

    public void iniciar() {
        System.out.println("=== TESTES CLIENTE ===");
        cadastrar();
        listar();
        alterar();
        excluir();
    }

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

    public void listar() {
        try {
            resultado("Listar", !clienteService.listar().isEmpty());
        } catch (Exception e) {
            resultado("Listar", false);
        }
    }

    public void alterar() {
        try {
            criado.setNome("Teste Alterado");
            resultado("Alterar", clienteService.alterar(criado));
        } catch (Exception e) {
            resultado("Alterar", false);
        }
    }

    public void excluir() {
        try {
            resultado("Excluir", clienteService.excluir(criado.getId()));
        } catch (Exception e) {
            resultado("Excluir", false);
        }
    }

    private void resultado(String teste, Boolean passou) {
        if (passou) {
            System.out.println(teste + ": Passou");
        } else {
            System.err.println(teste + ": Falhou");
        }
    }
}
