// Arquivo para o GetRestaurantResponse - Camada de Aplicação 

package br.com.brunno.api.order_food_service.restaurant.application.dto;

import br.com.brunno.api.order_food_service.restaurant.domain.entity.Restaurant;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Resultado da busca de restaurante por ID.
 */
public class GetRestaurantResponse {
    
    private final UUID id;
    private final String userId;
    private final String name;
    private final String cnpj;
    private final boolean isActive;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    
    public GetRestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.userId = restaurant.getUserId();
        this.name = restaurant.getName();
        this.cnpj = restaurant.getCnpj();
        this.isActive = restaurant.isActive();
        this.createdAt = restaurant.getCreatedAt();
        this.updatedAt = restaurant.getUpdatedAt();
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
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
} 