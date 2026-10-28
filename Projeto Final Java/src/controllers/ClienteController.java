package controllers;

import models.Cliente;
import services.ClienteService;
import views.ClienteView;

import java.util.ArrayList;

public class ClienteController {

    private final ClienteView clienteView = new ClienteView();
    private final ClienteService clienteService = new ClienteService();

    public void iniciar() {
        int opcao;
        do {
            opcao = clienteView.menu();
            switch (opcao) {
                // Adicionar
                case 0:
                    this.cadastrar();
                    break;
                // Listar
                case 1:
                    this.listar();
                    break;
                // Atualizar
                case 2:
                    this.alterar();
                    break;
                // Deletar
                case 3:
                    this.excluir();
                    break;
            }
        } while (opcao >= 0 && opcao != 4);
    }

    private void cadastrar() {
        try {
            Cliente cliente = clienteView.informarDados();
            if (cliente == null) return;

            if (clienteService.cadastrar(cliente)) {
                clienteView.alertaSucesso("Cliente adicionado com sucesso!");
            } else {
                clienteView.alertaErro("Erro ao adicionar cliente.");
            }
        } catch (Exception e) {
            clienteView.alertaErro(e.getMessage());
        }
    }

    private void listar() {
        try {
            ArrayList<Cliente> clientes = clienteService.listar();
            if (clientes.isEmpty()) {
                clienteView.alertaSucesso("Nenhum cliente cadastrado.");
                return;
            }
            clienteView.exibirEmScroll(clienteView.formatarLista(clientes), "Listagem de Clientes");
        } catch (Exception e) {
            clienteView.alertaErro(e.getMessage());
        }
    }

    private void alterar() {
        try {
            Integer id = clienteView.informarId("Digite o ID do cliente que deseja atualizar:");
            if (id == null) return;

            Cliente original = clienteService.buscarPorId(id);
            if (original == null) {
                clienteView.alertaSucesso("Cliente não encontrado.");
                return;
            }

            Cliente alterado = clienteView.informarAlteracao(original);
            if (alterado == null) return;

            if (clienteService.alterar(alterado)) {
                clienteView.alertaSucesso("Cliente atualizado com sucesso!");
            } else {
                clienteView.alertaErro("Erro ao atualizar cliente.");
            }
        } catch (Exception e) {
            clienteView.alertaErro(e.getMessage());
        }
    }

    private void excluir() {
        try {
            Integer id = clienteView.informarId("Digite o ID do cliente a ser excluído:");
            if (id == null) return;

            if (clienteService.excluir(id)) {
                clienteView.alertaSucesso("Cliente e seus pedidos foram removidos com sucesso!");
            } else {
                clienteView.alertaSucesso("Cliente não encontrado.");
            }
        } catch (Exception e) {
            clienteView.alertaErro(e.getMessage());
        }
    }
}
