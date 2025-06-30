// Arquivo para o CreateRestaurantResponse - Camada de Aplicação 

package br.com.brunno.api.order_food_service.restaurant.application.dto;

import br.com.brunno.api.order_food_service.restaurant.domain.entity.Restaurant;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Resultado da criação de restaurante.
 * Contém os dados do restaurante criado para retorno ao cliente.
 */
public class CreateRestaurantResponse {
    
    private final UUID id;
    private final String userId;
    private final String name;
    private final String cnpj;
    private final boolean isActive;
    private final LocalDateTime createdAt;
    
    /**
     * Construtor que recebe a entidade Restaurant para criar a resposta
     * @param restaurant restaurante criado
     */
    public CreateRestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.userId = restaurant.getUserId();
        this.name = restaurant.getName();
        this.cnpj = restaurant.getCnpj();
        this.isActive = restaurant.isActive();
        this.createdAt = restaurant.getCreatedAt();
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
} 