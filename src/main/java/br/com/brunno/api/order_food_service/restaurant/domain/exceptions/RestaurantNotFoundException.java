// Arquivo para a exceção RestaurantNotFoundException - Camada de Domínio 

package br.com.brunno.api.order_food_service.restaurant.domain.exceptions;

import java.util.UUID;

/**
 * Exceção lançada quando um restaurante não é encontrado.
 */
public class RestaurantNotFoundException extends RuntimeException {
    
    public RestaurantNotFoundException(String message) {
        super(message);
    }
    
    public RestaurantNotFoundException(UUID id) {
        super("Restaurante não encontrado com o ID: " + id);
    }
    
    public RestaurantNotFoundException(String cnpj, String reason) {
        super("Restaurante não encontrado com o CNPJ: " + cnpj + ". " + reason);
    }
} 