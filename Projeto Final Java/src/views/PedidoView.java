package views;

import models.Cliente;
import models.Pedido;
import services.PedidoService;
import utils.Utilidade;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Interface gráfica (JOptionPane) do módulo de pedidos: menus, diálogos e mensagens.
 */
public class PedidoView {

    /**
     * Exibe o menu de pedidos.
     * @return índice da opção escolhida (-1 se a janela for fechada)
     */
    public int menu() {
        String[] menu = { "Adicionar", "Listar", "Atualizar", "Deletar", "Voltar" };
        return JOptionPane.showOptionDialog(null, "Menu Pedidos - Escolha uma opção", "Pedidos",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, Utilidade.FAVICON, menu, menu[0]);
    }

    /**
     * Monta o texto da listagem de pedidos.
     * @param pedidos pedidos a exibir
     * @return texto com um registro por linha
     */
    public String formatarLista(ArrayList<Pedido> pedidos) {
        StringBuilder lista = new StringBuilder();
        for (Pedido p : pedidos) {
            lista.append(p.resumoPedido()).append("\n");
        }
        return lista.toString();
    }

    // Retorna null se o usuário cancelar; campos vazios ou inválidos ficam nulos e são validados no service
    /**
     * Solicita os dados para um novo cadastro.
     * @return os dados informados ou {@code null} se o usuário cancelar
     */
    public Pedido informarDados(ArrayList<Cliente> clientes, Pedido pedido) {
        Cliente cliente = selecionarCliente(clientes);
        if (cliente == null)
            return null;

        String papel = (String) JOptionPane.showInputDialog(null, "Selecione o tipo de papel", "Tipos de Papel",
                JOptionPane.PLAIN_MESSAGE, Utilidade.FAVICON, PedidoService.NOMES_PAPEL,
                pedido.getNomePapel() != null ? pedido.getNomePapel() : PedidoService.NOMES_PAPEL[0]);
        if (papel == null)
            return null;

        String altura = JOptionPane.showInputDialog("Qual a altura do rótulo?", pedido.getAlturaRotulo());
        if (altura == null)
            return null;
        String largura = JOptionPane.showInputDialog("Qual a largura do rótulo?", pedido.getLarguraRotulo());
        if (largura == null)
            return null;
        String quantidade = JOptionPane.showInputDialog("Qual a quantidade de rótulos?", pedido.getQntdRotulos());
        if (quantidade == null)
            return null;

        pedido.setCliente(cliente);
        pedido.setNomePapel(papel);
        pedido.setAlturaRotulo(Utilidade.converterDouble(altura.trim()));
        pedido.setLarguraRotulo(Utilidade.converterDouble(largura.trim()));
        pedido.setQntdRotulos(Utilidade.converterInteger(quantidade.trim()));
        return pedido;
    }

    /**
     * Mostra a lista de clientes para o usuário escolher um.
     * @param clientes clientes disponíveis
     * @return o cliente escolhido ou {@code null} se cancelar
     */
    private Cliente selecionarCliente(ArrayList<Cliente> clientes) {
        String[] nomesClientes = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            nomesClientes[i] = "ID: " + clientes.get(i).getId() + " - " + clientes.get(i).getNome();
        }
        String escolhido = (String) JOptionPane.showInputDialog(null, "Selecione o cliente para este pedido:",
                "Clientes Disponíveis", JOptionPane.PLAIN_MESSAGE, Utilidade.FAVICON, nomesClientes, nomesClientes[0]);
        if (escolhido == null)
            return null;

        for (int i = 0; i < nomesClientes.length; i++) {
            if (nomesClientes[i].equals(escolhido))
                return clientes.get(i);
        }
        return null;
    }

    /**
     * Exibe um texto longo em uma janela com barra de rolagem.
     * @param conteudo texto a mostrar
     * @param titulo título da janela
     */
    public void exibirEmScroll(String conteudo, String titulo) {
        JTextArea textArea = new JTextArea(conteudo);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(650, 250));
        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Exibe uma mensagem informativa.
     * @param mensagem texto a mostrar
     */
    public void alertaSucesso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    /**
     * Exibe uma mensagem de aviso.
     * @param mensagem texto a mostrar
     */
    public void alertaAviso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Exibe uma mensagem de erro.
     * @param mensagem texto a mostrar
     */
    public void alertaErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    // Retorna null se o usuário cancelar ou informar um ID vazio/inválido (neste caso exibe o erro)
    /**
     * Solicita um ID ao usuário.
     * @param texto pergunta exibida
     * @return o ID ou {@code null} se o usuário cancelar ou digitar um valor inválido
     */
    public Integer informarId(String texto) {
        String entrada = JOptionPane.showInputDialog(texto);
        if (entrada == null)
            return null;

        Integer id = Utilidade.converterInteger(entrada.trim());
        if (id == null)
            alertaErro("Ops! Id é um campo obrigatório e deve ser numérico.");
        return id;
    }
}
