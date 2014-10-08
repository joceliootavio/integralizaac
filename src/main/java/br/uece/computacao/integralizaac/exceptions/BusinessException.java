package br.uece.computacao.integralizaac.exceptions;

/**
 * @author Jocélio Otávio
 *
 * Classe de exceção utilizada para instanciar qualquer exceção
 * de negócio da aplicação.
 * 
 */
public class BusinessException extends RuntimeException {
    
    private static final long serialVersionUID = -8935712042044355058L;

    /**
     * Contructor.
     */
    public BusinessException(String message) {
            super(message);
    }
    
    /**
     * Contructor.
     */
    public BusinessException(Throwable throwable) {
            //super("Cod.: 800901 DATABASE - Operação não permitida." throwable.getMessage(),throwable);
            super(throwable.getMessage(),throwable);
    }
    
    /**
     * Contructor.
     */
    public BusinessException(String message, Throwable throwable) {
            super(message, throwable);
    }
}
