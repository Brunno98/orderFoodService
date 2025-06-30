// Arquivo para a exceção RestaurantAlreadyExistsException - Camada de Domínio 

package br.com.brunno.api.order_food_service.restaurant.domain.exceptions;

/**
 * Exceção lançada quando um restaurante já existe.
 */
public class RestaurantAlreadyExistsException extends RuntimeException {
    
    private RestaurantAlreadyExistsException(String message) {
        super(message);
    }

    public static RestaurantAlreadyExistsException byCnpj(String cnpj) {
        return new RestaurantAlreadyExistsException("Já existe um restaurante com o CNPJ: " + cnpj);
    }
    
    public static RestaurantAlreadyExistsException byUserId(String userId) {
        return new RestaurantAlreadyExistsException("O usuário com ID " + userId + " já possui um restaurante cadastrado");
    }
} 