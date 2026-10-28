package controllers;

import models.Cliente;
import models.Pedido;
import services.ClienteService;
import services.PedidoService;
import views.PedidoView;

import java.util.ArrayList;

public class PedidoController {

    private final PedidoView pedidoView = new PedidoView();
    private final PedidoService pedidoService = new PedidoService();
    private final ClienteService clienteService = new ClienteService();

    public void iniciar() {
        int opcao;
        do {
            opcao = pedidoView.menu();
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
            ArrayList<Cliente> clientes = clienteService.listar();
            if (clientes.isEmpty()) {
                pedidoView.alertaErro("Não é possível criar um pedido sem antes cadastrar pelo menos um cliente!");
                return;
            }

            Pedido pedido = pedidoView.informarDados(clientes, new Pedido());
            if (pedido == null) return;

            if (pedidoService.cadastrar(pedido)) {
                pedidoView.alertaSucesso("Pedido adicionado com sucesso!");
            } else {
                pedidoView.alertaErro("Erro ao adicionar pedido.");
            }
        } catch (Exception e) {
            pedidoView.alertaErro(e.getMessage());
        }
    }

    private void listar() {
        try {
            ArrayList<Pedido> pedidos = pedidoService.listar();
            if (pedidos.isEmpty()) {
                pedidoView.alertaAviso("Nenhum orçamento cadastrado.");
                return;
            }
            pedidoView.exibirEmScroll(pedidoView.formatarLista(pedidos), "Listagem de Pedidos");
        } catch (Exception e) {
            pedidoView.alertaErro(e.getMessage());
        }
    }

    private void alterar() {
        try {
            if (pedidoService.listar().isEmpty()) {
                pedidoView.alertaSucesso("Nenhum pedido cadastrado para alterar.");
                return;
            }

            Integer id = pedidoView.informarId("Insira o ID do pedido que deseja alterar:");
            if (id == null) return;

            Pedido pedido = pedidoService.buscarPorId(id);
            if (pedido == null) {
                pedidoView.alertaSucesso("Pedido com ID " + id + " não encontrado.");
                return;
            }

            pedido = pedidoView.informarDados(clienteService.listar(), pedido);
            if (pedido == null) return;

            if (pedidoService.alterar(pedido)) {
                pedidoView.alertaSucesso("Pedido atualizado com sucesso!");
            } else {
                pedidoView.alertaErro("Erro ao atualizar pedido.");
            }
        } catch (Exception e) {
            pedidoView.alertaErro(e.getMessage());
        }
    }

    private void excluir() {
        try {
            Integer id = pedidoView.informarId("Insira o ID do pedido que deseja excluir:");
            if (id == null) return;

            if (pedidoService.excluir(id)) {
                pedidoView.alertaSucesso("Pedido excluído com sucesso!");
            } else {
                pedidoView.alertaSucesso("Pedido não encontrado.");
            }
        } catch (Exception e) {
            pedidoView.alertaErro(e.getMessage());
        }
    }
}
