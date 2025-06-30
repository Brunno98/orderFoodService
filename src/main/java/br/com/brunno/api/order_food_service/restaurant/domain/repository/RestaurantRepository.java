// Arquivo para a interface RestaurantRepository - Camada de Domínio 

package br.com.brunno.api.order_food_service.restaurant.domain.repository;

import br.com.brunno.api.order_food_service.restaurant.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface do repositório de restaurantes (porta da arquitetura hexagonal).
 * Define os contratos para persistência de restaurantes.
 */
public interface RestaurantRepository {
    
    /**
     * Salva um restaurante no repositório
     * @param restaurant restaurante a ser salvo
     * @return restaurante salvo
     */
    Restaurant save(Restaurant restaurant);
    
    /**
     * Busca um restaurante pelo ID
     * @param id ID do restaurante
     * @return Optional contendo o restaurante se encontrado
     */
    Optional<Restaurant> findById(UUID id);
    
    /**
     * Busca um restaurante pelo CNPJ
     * @param cnpj CNPJ do restaurante
     * @return Optional contendo o restaurante se encontrado
     */
    Optional<Restaurant> findByCnpj(String cnpj);
    
    /**
     * Busca um restaurante pelo ID do usuário
     * @param userId ID do usuário
     * @return Optional contendo o restaurante se encontrado
     */
    Optional<Restaurant> findByUserId(String userId);
    
    /**
     * Lista todos os restaurantes
     * @return lista de restaurantes
     */
    List<Restaurant> findAll();
    
    /**
     * Lista restaurantes por status ativo/inativo
     * @param isActive status do restaurante
     * @return lista de restaurantes
     */
    List<Restaurant> findByIsActive(boolean isActive);
    
    /**
     * Lista restaurantes por nome (case insensitive)
     * @param name nome do restaurante
     * @return lista de restaurantes
     */
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    
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
     * Deleta um restaurante pelo ID
     * @param id ID do restaurante a ser deletado
     */
    void deleteById(UUID id);
} 