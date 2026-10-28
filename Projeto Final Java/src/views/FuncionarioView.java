package views;

import models.Funcionario;
import utils.Utilidade;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.util.ArrayList;

public class FuncionarioView {

    public int menu() {
        String[] menu = { "Adicionar", "Listar", "Atualizar", "Deletar", "Voltar" };
        return JOptionPane.showOptionDialog(null, "Menu Funcionários - Escolha uma opção", "Funcionários",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, Utilidade.FAVICON, menu, menu[0]);
    }

    public String formatarLista(ArrayList<Funcionario> funcionarios) {
        StringBuilder lista = new StringBuilder();
        for (Funcionario f : funcionarios) {
            lista.append(f).append("\n");
        }
        return lista.toString();
    }

    // Retorna null se o usuário cancelar; campos vazios são validados no service
    public Funcionario informarDados() {
        String nome = JOptionPane.showInputDialog("Nome do Funcionário:");
        if (nome == null)
            return null;
        String cargo = JOptionPane.showInputDialog("Cargo do Funcionário:");
        if (cargo == null)
            return null;
        return new Funcionario(nome, cargo);
    }

    // Retorna null se o usuário cancelar; campos vazios são validados no service
    public Funcionario informarAlteracao(Funcionario original) {
        String novoNome = JOptionPane.showInputDialog("Novo Nome:", original.getNome());
        if (novoNome == null)
            return null;
        String novoCargo = JOptionPane.showInputDialog("Novo Cargo:", original.getCargo());
        if (novoCargo == null)
            return null;
        return new Funcionario(original.getId(), novoNome, novoCargo);
    }

    public void exibirEmScroll(String conteudo, String titulo) {
        JTextArea textArea = new JTextArea(conteudo);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(650, 250));
        JOptionPane.showMessageDialog(null, scrollPane, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void alertaSucesso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem);
    }

    public void alertaAviso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public void alertaErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    // Retorna null se o usuário cancelar ou informar um ID vazio/inválido (neste caso exibe o erro)
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
