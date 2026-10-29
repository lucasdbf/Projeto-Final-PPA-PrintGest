package models;

/**
 * Entidade que representa um pedido (orçamento) de rótulos.
 * <p>Cada pedido pertence a um {@link Cliente} e guarda o papel escolhido, as medidas
 * do rótulo, a quantidade de folhas necessárias e o valor calculado.
 */
public class Pedido {

    // Atributos
    /** Tipo de papel escolhido (um dos nomes de PedidoService.NOMES_PAPEL). */
    private String nomePapel;
    /** Altura do rótulo em centímetros. */
    private Double alturaRotulo;
    /** Largura do rótulo em centímetros. */
    private Double larguraRotulo;
    /** Quantidade de rótulos pedida. */
    private Integer qntdRotulos;
    /** Quantidade de folhas necessárias, calculada pelo service. */
    private Double qntdFolha;
    /** Valor final do pedido em reais, calculado pelo service. */
    private Double valor;
    /** Relacionamento: cada pedido pertence a um cliente. */
    private Cliente cliente;
    /** Identificador gerado pelo banco (coluna cdpedido). */
    private Integer id;

    // Construtores

    /** Cria um pedido vazio, preenchido depois pelos setters. */
    public Pedido() {}

    /**
     * Cria um pedido ainda não salvo (sem ID).
     * Folhas e valor podem ser nulos: o service calcula antes de gravar.
     */
    public Pedido(String nomePapel, Double alturaRotulo, Double larguraRotulo, Integer qntdRotulos, Double qntdFolha,
            Double valor, Cliente cliente) {
        this.nomePapel = nomePapel;
        this.alturaRotulo = alturaRotulo;
        this.larguraRotulo = larguraRotulo;
        this.qntdRotulos = qntdRotulos;
        this.qntdFolha = qntdFolha;
        this.valor = valor;
        this.cliente = cliente;
    }

    /** Cria um pedido já existente no banco, com o ID dele. */
    public Pedido(Integer id, String nomePapel, Double alturaRotulo, Double larguraRotulo, Integer qntdRotulos,
            Double qntdFolha, Double valor, Cliente cliente) {
        this(nomePapel, alturaRotulo, larguraRotulo, qntdRotulos, qntdFolha, valor, cliente);
        this.id = id;
    }

    /**
     * Monta o resumo de uma linha exibido na listagem de pedidos.
     * @return texto com cliente, papel, medidas, rótulos, folhas e preço
     */
    public String resumoPedido() {
        return "ID Pedido: " + id + " | " + cliente + " | Papel: " + nomePapel + " | Dimensões: " + alturaRotulo + "x"
                + larguraRotulo + " | Rótulos: " + qntdRotulos + " | Folhas: " + qntdFolha + " | Preço: R$"
                + String.format("%.2f", valor);
    }

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomePapel() {
        return nomePapel;
    }

    public void setNomePapel(String nomePapel) {
        this.nomePapel = nomePapel;
    }

    public Double getAlturaRotulo() {
        return alturaRotulo;
    }

    public void setAlturaRotulo(Double alturaRotulo) {
        this.alturaRotulo = alturaRotulo;
    }

    public Double getLarguraRotulo() {
        return larguraRotulo;
    }

    public void setLarguraRotulo(Double larguraRotulo) {
        this.larguraRotulo = larguraRotulo;
    }

    public Integer getQntdRotulos() {
        return qntdRotulos;
    }

    public void setQntdRotulos(Integer qntdRotulos) {
        this.qntdRotulos = qntdRotulos;
    }

    public Double getQntdFolha() {
        return qntdFolha;
    }

    public void setQntdFolha(Double qntdFolha) {
        this.qntdFolha = qntdFolha;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}