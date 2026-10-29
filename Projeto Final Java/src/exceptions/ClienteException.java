package exceptions;

/**
 * Erro de negócio ou de banco de dados no módulo de clientes.
 */
public class ClienteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * @param message descrição do erro, exibida ao usuário
     */
    public ClienteException(String message) {
        super(message);
    }
}
