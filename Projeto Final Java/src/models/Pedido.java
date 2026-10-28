package models;

public class Pedido {

	private String nomePapel;
	private Double alturaRotulo;
	private Double larguraRotulo;
	private Integer qntdRotulos;
	private Double qntdFolha;
	private Double valor;
	private Cliente cliente;
	private Integer id;

	public Pedido() {}

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

	public Pedido(Integer id, String nomePapel, Double alturaRotulo, Double larguraRotulo, Integer qntdRotulos,
			Double qntdFolha, Double valor, Cliente cliente) {
		this(nomePapel, alturaRotulo, larguraRotulo, qntdRotulos, qntdFolha, valor, cliente);
		this.id = id;
	}

	public String resumoPedido() {
		return "ID Pedido: " + id + " | " + cliente + " | Papel: " + nomePapel + " | Dimensões: " + alturaRotulo + "x"
				+ larguraRotulo + " | Rótulos: " + qntdRotulos + " | Folhas: " + qntdFolha + " | Preço: R$"
				+ String.format("%.2f", valor);
	}

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