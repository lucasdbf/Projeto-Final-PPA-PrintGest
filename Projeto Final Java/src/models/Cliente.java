package models;

/**
 * Entidade que representa um cliente da gráfica.
 * <p>Um cliente pode ter vários pedidos ({@link Pedido}).
 */
public class Cliente {

    // Atributos
    /** Identificador gerado pelo banco (coluna cdcliente). */
    private Integer id;
    /** Nome do cliente (obrigatório). */
    private String nome;
    /** Telefone do cliente, guardado como texto. */
    private String telefone;

    // Construtores

    /** Cria um cliente vazio, preenchido depois pelos setters. */
    public Cliente() {}

    /**
     * Cria um cliente ainda não salvo (sem ID).
     * @param nome nome do cliente
     * @param telefone telefone do cliente
     */
    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    /**
     * Cria um cliente já existente no banco.
     * @param id identificador do cliente
     * @param nome nome do cliente
     * @param telefone telefone do cliente
     */
    public Cliente(Integer id, String nome, String telefone) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Texto usado nas listagens, com nome e telefone do cliente.
     */
    @Override
    public String toString() {
        return "Cliente: " + nome + " (Tel: " + telefone + ")";
    }
}
