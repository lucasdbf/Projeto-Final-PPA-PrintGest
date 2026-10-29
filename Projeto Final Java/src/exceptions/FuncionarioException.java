package exceptions;

/**
 * Erro de negócio ou de banco de dados no módulo de funcionários.
 */
public class FuncionarioException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * @param message descrição do erro, exibida ao usuário
     */
    public FuncionarioException(String message) {
        super(message);
    }
}
