package exceptions;

/**
 * Erro de negócio ou de banco de dados no módulo de pedidos.
 */
public class PedidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * @param message descrição do erro, exibida ao usuário
     */
    public PedidoException(String message) {
        super(message);
    }
}
