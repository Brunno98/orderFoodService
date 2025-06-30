// Arquivo para a entidade RestaurantJpaEntity - Camada de Infraestrutura 

package br.com.brunno.api.order_food_service.restaurant.infrastructure.persistence.entity;

import br.com.brunno.api.order_food_service.restaurant.domain.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade JPA para persistência de restaurantes.
 * Representa a tabela restaurants no banco de dados.
 */
@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
public class RestaurantJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "domain_id", nullable = false, unique = true)
    private UUID domainId;
    
    @Column(name = "user_id", nullable = false)
    private String userId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String cnpj;
    
    @Column(name = "is_active", nullable = false)
    private boolean isActive;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public RestaurantJpaEntity(UUID domainId, String userId, String name, String cnpj, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.domainId = domainId;
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /**
     * Converte a entidade JPA para a entidade de domínio
     * @return entidade de domínio Restaurant
     */
    public Restaurant toDomain() {
        return new Restaurant(domainId, userId, name, cnpj, isActive, createdAt, updatedAt);
    }
    
    /**
     * Cria uma entidade JPA a partir da entidade de domínio
     * @param restaurant entidade de domínio
     * @return entidade JPA
     */
    public static RestaurantJpaEntity fromDomain(Restaurant restaurant) {
        return new RestaurantJpaEntity(
            restaurant.getId(),
            restaurant.getUserId(),
            restaurant.getName(),
            restaurant.getCnpj(),
            restaurant.isActive(),
            restaurant.getCreatedAt(),
            restaurant.getUpdatedAt()
        );
    }
} 