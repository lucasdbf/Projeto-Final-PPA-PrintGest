package tests;

import models.Funcionario;
import services.FuncionarioService;

import java.util.ArrayList;

public class FuncionarioTest {

    private final FuncionarioService funcionarioService = new FuncionarioService();
    private Funcionario criado;

    public void iniciar() {
        System.out.println("=== TESTES FUNCIONÁRIO ===");
        cadastrar();
        listar();
        alterar();
        excluir();
    }

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

    public void listar() {
        try {
            resultado("Listar", !funcionarioService.listar().isEmpty());
        } catch (Exception e) {
            resultado("Listar", false);
        }
    }

    public void alterar() {
        try {
            criado.setNome("Teste Alterado");
            resultado("Alterar", funcionarioService.alterar(criado));
        } catch (Exception e) {
            resultado("Alterar", false);
        }
    }

    public void excluir() {
        try {
            resultado("Excluir", funcionarioService.excluir(criado.getId()));
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
