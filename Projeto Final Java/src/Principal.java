import controllers.ClienteController;
import controllers.FuncionarioController;
import controllers.PedidoController;
import utils.Utilidade;

import javax.swing.JOptionPane;

/**
 * Ponto de entrada do PrintGest: exibe o menu principal e abre o CRUD escolhido
 * (pedidos, clientes ou funcionários).
 */
public class Principal {

    /**
     * Abre o menu principal e mantém o sistema em execução até o usuário escolher "Sair".
     * @param args argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        PedidoController pedidoController = new PedidoController();
        ClienteController clienteController = new ClienteController();
        FuncionarioController funcionarioController = new FuncionarioController();

        String[] opcoesMenuInicio = { "Pedidos", "Clientes", "Funcionários", "Sair" };
        int opcao;
        do {
            opcao = JOptionPane.showOptionDialog(null, "Escolha qual CRUD você deseja acessar", "Menu Principal",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, Utilidade.FAVICON, opcoesMenuInicio,
                    opcoesMenuInicio[0]);

            switch (opcao) {
            case 0:
                pedidoController.iniciar();
                break;
            case 1:
                clienteController.iniciar();
                break;
            case 2:
                funcionarioController.iniciar();
                break;
            }
        } while (opcao >= 0 && opcao != 3);

        JOptionPane.showMessageDialog(null, "Sistema encerrado.");
    }
}
