package br.com.brunno.api.order_food_service.exception;

/**
 * Exceção lançada quando um recurso não é encontrado
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Método estático para criar exceção de restaurante não encontrado
     */
    public static ResourceNotFoundException restaurantNotFound(Long id) {
        return new ResourceNotFoundException("Restaurante não encontrado com ID: " + id);
    }
} 