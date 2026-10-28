package services;

import dao.PedidoDao;
import exceptions.PedidoException;
import models.Pedido;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class PedidoService {

    public static final String[] NOMES_PAPEL = { "Couchê Brilho ADC5420", "Couchê Brilho ADC6000", "BOPP Branco",
            "BOPP Transparente", "BOPP Metalizado", "Vinil Fasson Branco", "Vinil Fasson Transparente" };

    private static final double[][] TABELA_PRECOS = { { 6.4, 6.1, 5.7, 5.0, 4.3, 3.8, 3.5, 3.2 },
            { 6.8, 6.5, 6.1, 5.3, 4.6, 4.0, 3.7, 3.4 }, { 9.5, 9.1, 8.5, 7.0, 6.0, 5.4, 4.8, 4.3 },
            { 10.8, 10.3, 9.7, 7.9, 6.9, 6.1, 5.4, 4.9 }, { 11.2, 10.7, 10.0, 8.2, 7.1, 6.3, 5.6, 5.1 },
            { 15.6, 14.9, 13.9, 11.4, 9.9, 8.8, 7.8, 7.1 }, { 15.6, 14.9, 13.9, 11.4, 9.9, 8.8, 7.8, 7.1 } };

    private final PedidoDao pedidoDao = new PedidoDao();

    public ArrayList<Pedido> listar() throws PedidoException {
        try {
            return pedidoDao.listar();
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Pedido buscarPorId(Integer id) throws PedidoException {
        try {
            return pedidoDao.buscarPorId(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Boolean cadastrar(Pedido pedido) throws PedidoException {
        calcular(pedido);
        try {
            return pedidoDao.inserir(pedido);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Boolean alterar(Pedido pedido) throws PedidoException {
        calcular(pedido);
        try {
            return pedidoDao.alterar(pedido);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    public Boolean excluir(Integer id) throws PedidoException {
        try {
            return pedidoDao.excluir(id);
        } catch (SQLException e) {
            throw erroBanco(e);
        }
    }

    // Valida os dados e preenche a quantidade de folhas e o valor do pedido
    private void calcular(Pedido pedido) throws PedidoException {
        String erros = "";
        if (pedido.getCliente() == null) erros += "\n- Cliente é um campo obrigatório";
        if (pedido.getNomePapel() == null || pedido.getNomePapel().trim().isEmpty())
            erros += "\n- Tipo de papel é um campo obrigatório";
        if (pedido.getAlturaRotulo() == null || pedido.getAlturaRotulo() <= 0)
            erros += "\n- Altura do rótulo é obrigatória e deve ser um número maior que zero";
        if (pedido.getLarguraRotulo() == null || pedido.getLarguraRotulo() <= 0)
            erros += "\n- Largura do rótulo é obrigatória e deve ser um número maior que zero";
        if (pedido.getQntdRotulos() == null || pedido.getQntdRotulos() <= 0)
            erros += "\n- Quantidade de rótulos é obrigatória e deve ser um número inteiro maior que zero";

        int papelIndex = Arrays.asList(NOMES_PAPEL).indexOf(pedido.getNomePapel());
        if (erros.isEmpty() && papelIndex < 0) erros += "\n- Tipo de papel inválido";

        if (!erros.isEmpty()) {
            throw new PedidoException("Ops! Erros encontrados:" + erros);
        }

        double rotulosPorFolha = calcularMelhorAproveitamento(pedido.getAlturaRotulo(), pedido.getLarguraRotulo());
        if (rotulosPorFolha <= 0) {
            throw new PedidoException("O rótulo não cabe na folha. Informe dimensões menores.");
        }

        double qntdFolha = Math.ceil(pedido.getQntdRotulos() / rotulosPorFolha);
        pedido.setQntdFolha(qntdFolha);
        pedido.setValor(calcularPreco(TABELA_PRECOS[papelIndex], qntdFolha));
    }

    public double calcularMelhorAproveitamento(double alturaRotulo, double larguraRotulo) {
        double op1 = Math.floor(46.5 / alturaRotulo) * Math.floor(31.5 / larguraRotulo);
        double op2 = Math.floor(46.5 / larguraRotulo) * Math.floor(31.5 / alturaRotulo);
        return Math.max(op1, op2);
    }

    public double calcularPreco(double[] tabelaPrecos, double qntdFolha) {
        if (qntdFolha <= 0)
            return 0.0;
        double precoUnitario;
        if (qntdFolha <= 20)
            precoUnitario = tabelaPrecos[0];
        else if (qntdFolha <= 50)
            precoUnitario = tabelaPrecos[1];
        else if (qntdFolha <= 100)
            precoUnitario = tabelaPrecos[2];
        else if (qntdFolha <= 200)
            precoUnitario = tabelaPrecos[3];
        else if (qntdFolha <= 300)
            precoUnitario = tabelaPrecos[4];
        else if (qntdFolha <= 400)
            precoUnitario = tabelaPrecos[5];
        else if (qntdFolha <= 500)
            precoUnitario = tabelaPrecos[6];
        else
            precoUnitario = tabelaPrecos[7];

        return (precoUnitario * qntdFolha) + 10.0;
    }

    private PedidoException erroBanco(SQLException e) {
        return new PedidoException("Ops! Erro no banco de dados: " + e.getMessage());
    }
}
