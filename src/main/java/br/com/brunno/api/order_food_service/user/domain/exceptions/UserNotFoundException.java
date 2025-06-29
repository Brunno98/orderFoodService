package br.com.brunno.api.order_food_service.user.domain.exceptions;

/**
 * Exceção lançada quando um usuário não é encontrado.
 * Exceção de domínio que representa uma regra de negócio violada.
 */
public class UserNotFoundException extends RuntimeException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 