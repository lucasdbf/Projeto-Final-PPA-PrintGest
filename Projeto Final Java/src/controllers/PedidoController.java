package controllers;

import models.Cliente;
import models.Pedido;
import services.ClienteService;
import services.PedidoService;
import views.PedidoView;

import java.util.ArrayList;

/**
 * Controla o fluxo do CRUD de pedidos, ligando a view ao service.
 */
public class PedidoController {

    private final PedidoView pedidoView = new PedidoView();
    private final PedidoService pedidoService = new PedidoService();
    private final ClienteService clienteService = new ClienteService();

    /**
     * Exibe o menu de pedidos e direciona cada opção escolhida até o usuário voltar.
     */
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

    /**
     * Fluxo de cadastro: coleta os dados na view, envia ao service e informa o resultado.
     */
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

    /**
     * Fluxo de listagem: busca os pedidos no service e exibe na view.
     */
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

    /**
     * Fluxo de alteração: localiza o pedido pelo ID, coleta os novos dados e atualiza.
     */
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

    /**
     * Fluxo de exclusão: pede o ID e remove o pedido pelo service.
     */
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
