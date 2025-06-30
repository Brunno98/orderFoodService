// Arquivo para o GetRestaurantWebResponse - Camada de Infraestrutura Web

package br.com.brunno.api.order_food_service.restaurant.infrastructure.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de resposta para consulta de restaurante na camada web.
 * Contém os dados do restaurante consultado para retorno ao cliente.
 */
public class GetRestaurantWebResponse {
    
    private UUID id;
    private String userId;
    private String name;
    private String cnpj;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Construtores
    public GetRestaurantWebResponse() {}
    
    public GetRestaurantWebResponse(UUID id, String userId, String name, String cnpj, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters e Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 