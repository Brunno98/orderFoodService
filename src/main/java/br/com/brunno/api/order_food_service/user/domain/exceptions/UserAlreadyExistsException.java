package br.com.brunno.api.order_food_service.user.domain.exceptions;

/**
 * Exceção lançada quando já existe um usuário com o email fornecido.
 * Exceção de domínio que representa uma regra de negócio violada.
 */
public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    
    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
} 