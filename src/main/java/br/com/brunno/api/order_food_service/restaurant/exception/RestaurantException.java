package br.com.brunno.api.order_food_service.restaurant.exception;

/**
 * Exceção customizada para operações relacionadas a restaurantes
 */
public class RestaurantException extends RuntimeException {

    public RestaurantException(String message) {
        super(message);
    }

    public RestaurantException(String message, Throwable cause) {
        super(message, cause);
    }

    // Métodos estáticos para criar exceções específicas
    public static RestaurantException restaurantNotFound(Long id) {
        return new RestaurantException("Restaurante não encontrado com ID: " + id);
    }

    public static RestaurantException cnpjAlreadyExists(String cnpj) {
        return new RestaurantException("CNPJ já cadastrado: " + cnpj);
    }

    public static RestaurantException userAlreadyHasRestaurant(Long userId) {
        return new RestaurantException("Usuário já possui restaurante cadastrado. ID do usuário: " + userId);
    }

    public static RestaurantException invalidRestaurantData(String field) {
        return new RestaurantException("Dados inválidos do restaurante. Campo: " + field);
    }

} 