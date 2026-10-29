package controllers;

import models.Funcionario;
import services.FuncionarioService;
import views.FuncionarioView;

import java.util.ArrayList;

/**
 * Controla o fluxo do CRUD de funcionários, ligando a view ao service.
 */
public class FuncionarioController {

    private final FuncionarioView funcionarioView = new FuncionarioView();
    private final FuncionarioService funcionarioService = new FuncionarioService();

    /**
     * Exibe o menu de funcionários e direciona cada opção escolhida até o usuário voltar.
     */
    public void iniciar() {
        int opcao;
        do {
            opcao = funcionarioView.menu();
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
            Funcionario funcionario = funcionarioView.informarDados();
            if (funcionario == null) return;

            if (funcionarioService.cadastrar(funcionario)) {
                funcionarioView.alertaSucesso("Funcionário cadastrado com sucesso!");
            } else {
                funcionarioView.alertaErro("Erro ao cadastrar funcionário.");
            }
        } catch (Exception e) {
            funcionarioView.alertaErro(e.getMessage());
        }
    }

    /**
     * Fluxo de listagem: busca os funcionários no service e exibe na view.
     */
    private void listar() {
        try {
            ArrayList<Funcionario> funcionarios = funcionarioService.listar();
            if (funcionarios.isEmpty()) {
                funcionarioView.alertaSucesso("Nenhum funcionário cadastrado.");
                return;
            }
            funcionarioView.exibirEmScroll(funcionarioView.formatarLista(funcionarios), "Listagem de Funcionários");
        } catch (Exception e) {
            funcionarioView.alertaErro(e.getMessage());
        }
    }

    /**
     * Fluxo de alteração: localiza o funcionário pelo ID, coleta os novos dados e atualiza.
     */
    private void alterar() {
        try {
            Integer id = funcionarioView.informarId("Digite o ID do funcionário que deseja atualizar:");
            if (id == null) return;

            Funcionario original = funcionarioService.buscarPorId(id);
            if (original == null) {
                funcionarioView.alertaSucesso("Funcionário não encontrado.");
                return;
            }

            Funcionario alterado = funcionarioView.informarAlteracao(original);
            if (alterado == null) return;

            if (funcionarioService.alterar(alterado)) {
                funcionarioView.alertaSucesso("Funcionário atualizado com sucesso!");
            } else {
                funcionarioView.alertaErro("Erro ao atualizar funcionário.");
            }
        } catch (Exception e) {
            funcionarioView.alertaErro(e.getMessage());
        }
    }

    /**
     * Fluxo de exclusão: pede o ID e remove o funcionário pelo service.
     */
    private void excluir() {
        try {
            Integer id = funcionarioView.informarId("Digite o ID do funcionário a ser excluído:");
            if (id == null) return;

            if (funcionarioService.excluir(id)) {
                funcionarioView.alertaSucesso("Funcionário removido com sucesso!");
            } else {
                funcionarioView.alertaSucesso("Funcionário não encontrado.");
            }
        } catch (Exception e) {
            funcionarioView.alertaErro(e.getMessage());
        }
    }
}
