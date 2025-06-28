package br.com.brunno.api.order_food_service.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para requisição de atualização de restaurante
 */
public class RestaurantUpdateRequest {

    @NotBlank(message = "Nome do restaurante é obrigatório")
    @Size(min = 2, max = 255, message = "Nome deve ter entre 2 e 255 caracteres")
    private String name;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String description;

    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    private String address;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos numéricos")
    private String phone;

    private Boolean isActive;

    // TODO: Implementar:
    // - Construtores
    // - Getters e Setters
    // - Método toString()

} 