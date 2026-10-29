package tests;

import models.Funcionario;
import services.FuncionarioService;

import java.util.ArrayList;

/**
 * Testes automatizados de funcionários: cadastrar, listar, alterar e excluir no banco.
 */
public class FuncionarioTest {

    private final FuncionarioService funcionarioService = new FuncionarioService();
    private Funcionario criado;

    /**
     * Executa todos os testes do módulo de funcionários na ordem: cadastrar, listar, alterar e excluir.
     */
    public void iniciar() {
        System.out.println("=== TESTES FUNCIONÁRIO ===");
        cadastrar();
        listar();
        alterar();
        excluir();
    }

    /**
     * Teste de cadastro: grava um funcionário de teste e confere se foi inserido.
     */
    public void cadastrar() {
        try {
            Boolean cadastrou = funcionarioService.cadastrar(new Funcionario("Teste", "Cargo Teste"));
            ArrayList<Funcionario> funcionarios = funcionarioService.listar();
            criado = funcionarios.get(funcionarios.size() - 1);
            resultado("Cadastrar", cadastrou);
        } catch (Exception e) {
            resultado("Cadastrar", false);
        }
    }

    /**
     * Teste de listagem: confere se há funcionários cadastrados.
     */
    public void listar() {
        try {
            resultado("Listar", !funcionarioService.listar().isEmpty());
        } catch (Exception e) {
            resultado("Listar", false);
        }
    }

    /**
     * Teste de alteração: altera o funcionário criado no teste de cadastro.
     */
    public void alterar() {
        try {
            criado.setNome("Teste Alterado");
            resultado("Alterar", funcionarioService.alterar(criado));
        } catch (Exception e) {
            resultado("Alterar", false);
        }
    }

    /**
     * Teste de exclusão: remove o funcionário criado no teste de cadastro.
     */
    public void excluir() {
        try {
            resultado("Excluir", funcionarioService.excluir(criado.getId()));
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
