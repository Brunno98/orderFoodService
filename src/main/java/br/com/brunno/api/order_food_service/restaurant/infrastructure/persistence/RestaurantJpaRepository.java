// Arquivo para o RestaurantJpaRepository - Camada de Infraestrutura 

package br.com.brunno.api.order_food_service.restaurant.infrastructure.persistence;

import br.com.brunno.api.order_food_service.restaurant.infrastructure.persistence.entity.RestaurantJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface JPA para persistência de restaurantes.
 * Responsável pela comunicação direta com o banco de dados.
 */
@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantJpaEntity, Long> {
    
    /**
     * Busca um restaurante pelo CNPJ
     * @param cnpj CNPJ do restaurante
     * @return Optional contendo o restaurante se encontrado
     */
    Optional<RestaurantJpaEntity> findByCnpj(String cnpj);
    
    /**
     * Busca um restaurante pelo ID do usuário
     * @param userId ID do usuário
     * @return Optional contendo o restaurante se encontrado
     */
    Optional<RestaurantJpaEntity> findByUserId(String userId);
    
    /**
     * Busca restaurantes por status ativo/inativo
     * @param isActive status do restaurante
     * @return lista de restaurantes
     */
    List<RestaurantJpaEntity> findByIsActive(boolean isActive);
    
    /**
     * Busca restaurantes por nome (case insensitive)
     * @param name nome do restaurante
     * @return lista de restaurantes
     */
    List<RestaurantJpaEntity> findByNameContainingIgnoreCase(String name);
    
    /**
     * Verifica se existe um restaurante com o CNPJ fornecido
     * @param cnpj CNPJ a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByCnpj(String cnpj);
    
    /**
     * Verifica se existe um restaurante com o ID do usuário fornecido
     * @param userId ID do usuário a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByUserId(String userId);

    /**
     * Busca um restaurante pelo id de domínio
     * @param id ID de domínio
     * @return Optional contendo o restaurante se encontrado
     */
    Optional<RestaurantJpaEntity> findByDomainId(UUID id);

    /**
     * Deleta um restaurante pelo id de domínio
     * @param id ID de domínio
     */
    void deleteByDomainId(UUID id);
} 