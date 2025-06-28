package br.com.brunno.api.order_food_service.restaurant.dto;

import br.com.brunno.api.order_food_service.restaurant.entity.Restaurant;
import java.time.LocalDateTime;

/**
 * DTO para resposta de restaurante
 */
public class RestaurantResponse {

    private Long id;
    private Long userId;
    private String name;
    private String cnpj;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RestaurantResponse(Long id, Long userId, String name, String cnpj, Boolean isActive, 
                            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Método para converter entidade em DTO
    public static RestaurantResponse fromEntity(Restaurant restaurant) {
        return new RestaurantResponse(
            restaurant.getId(),
            restaurant.getUserId(),
            restaurant.getName(),
            restaurant.getCnpj(),
            restaurant.getIsActive(),
            restaurant.getCreatedAt(),
            restaurant.getUpdatedAt()
        );
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    @Override
    public String toString() {
        return "RestaurantResponse{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
} 