// Arquivo para o CreateRestaurantWebResponse - Camada de Infraestrutura Web

package br.com.brunno.api.order_food_service.restaurant.infrastructure.web.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO de resposta para criação de restaurante na camada web.
 * Contém os dados do restaurante criado para retorno ao cliente.
 */
public class CreateRestaurantWebResponse {
    
    private UUID id;
    private String userId;
    private String name;
    private String cnpj;
    @JsonProperty("isActive")
    private boolean active;
    private LocalDateTime createdAt;
    
    // Construtores
    public CreateRestaurantWebResponse() {}
    
    public CreateRestaurantWebResponse(UUID id, String userId, String name, String cnpj, boolean active, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
        this.active = active;
        this.createdAt = createdAt;
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
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 