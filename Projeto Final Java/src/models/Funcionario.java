package models;

/**
 * Entidade que representa um funcionário da gráfica, com nome e cargo.
 */
public class Funcionario {

    // Atributos
    /** Identificador gerado pelo banco (coluna cdfuncionario). */
    private Integer id;
    /** Nome do funcionário (obrigatório). */
    private String nome;
    /** Cargo exercido na gráfica (obrigatório). */
    private String cargo;

    // Construtores

    /** Cria um funcionário vazio, preenchido depois pelos setters. */
    public Funcionario() {}

    /**
     * Cria um funcionário ainda não salvo (sem ID).
     * @param nome nome do funcionário
     * @param cargo cargo do funcionário
     */
    public Funcionario(String nome, String cargo) {
        this.nome = nome;
        this.cargo = cargo;
    }

    /**
     * Cria um funcionário já existente no banco.
     * @param id identificador do funcionário
     * @param nome nome do funcionário
     * @param cargo cargo do funcionário
     */
    public Funcionario(Integer id, String nome, String cargo) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
    }

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Texto usado nas listagens, com ID, nome e cargo do funcionário.
     */
    @Override
    public String toString() {
        return "ID Func: " + id + " | Nome: " + nome + " | Cargo: " + cargo;
    }
}
