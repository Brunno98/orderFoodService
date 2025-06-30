// Arquivo para a entidade Restaurant - Camada de Domínio 

package br.com.brunno.api.order_food_service.restaurant.domain.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade de domínio Restaurant representando um restaurante do sistema.
 * Segue a arquitetura hexagonal sem influências de ORM.
 */
public class Restaurant {
    
    private final UUID id;
    private final String userId;
    private String name;
    private String cnpj;
    private boolean isActive;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Construtor para criação de um novo restaurante
     */
    public Restaurant(String userId, String name, String cnpj) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        validate();
    }
    
    /**
     * Construtor para reconstrução de um restaurante existente
     */
    public Restaurant(UUID id, String userId, String name, String cnpj, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        validate();
    }
    
    /**
     * Validações de domínio
     */
    private void validate() {
        if (userId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ não pode ser vazio");
        }
        
        if (!isValidCnpj(cnpj)) {
            throw new IllegalArgumentException("CNPJ deve ter formato válido");
        }
    }
    
    /**
     * Validação simples de CNPJ
     */
    private boolean isValidCnpj(String cnpj) {
        // Remove caracteres não numéricos
        String cnpjNumerico = cnpj.replaceAll("[^0-9]", "");
        return cnpjNumerico.length() == 14;
    }
    
    /**
     * Método para atualizar os dados do restaurante
     */
    public void update(String name, String cnpj) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
        
        if (cnpj != null && !cnpj.trim().isEmpty()) {
            if (!isValidCnpj(cnpj)) {
                throw new IllegalArgumentException("CNPJ deve ter formato válido");
            }
            this.cnpj = cnpj;
        }
        
        this.updatedAt = LocalDateTime.now();
        validate();
    }
    
    /**
     * Ativa o restaurante
     */
    public void activate() {
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Desativa o restaurante
     */
    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Verifica se o restaurante está ativo
     */
    public boolean isActive() {
        return isActive;
    }
    
    // Getters
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Restaurant{" +
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