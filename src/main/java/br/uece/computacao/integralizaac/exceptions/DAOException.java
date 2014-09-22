package br.uece.computacao.integralizaac.exceptions;

public class DAOException extends RuntimeException {
    
    private static final long serialVersionUID = -8935712042044355058L;

    /**
     * Contructor.
     */
    public DAOException(String message) {
            super(message);
    }
    
    /**
     * Contructor.
     */
    public DAOException(Throwable throwable) {
            //super("Cod.: 800901 DATABASE - Operação não permitida." throwable.getMessage(),throwable);
            super(throwable.getMessage(),throwable);
    }
    
    /**
     * Contructor.
     */
    public DAOException(String message, Throwable throwable) {
            super(message, throwable);
    }
}
