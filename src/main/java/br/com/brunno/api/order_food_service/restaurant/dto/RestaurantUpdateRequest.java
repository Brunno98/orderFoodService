package br.com.brunno.api.order_food_service.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para requisição de atualização de restaurante
 */
public class RestaurantUpdateRequest {

    @NotBlank(message = "Nome do restaurante é obrigatório")
    @Size(min = 2, max = 255, message = "Nome deve ter entre 2 e 255 caracteres")
    private String name;

    // TODO: Implementar:
    // - Construtores
    // - Getters e Setters
    // - Método toString()

} 