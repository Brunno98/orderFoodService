package br.com.brunno.api.order_food_service.restaurant.repository;

import br.com.brunno.api.order_food_service.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository responsável pela persistência de dados dos restaurantes
 */
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // Buscar restaurante por CNPJ
    Optional<Restaurant> findByCnpj(String cnpj);

    // Buscar restaurante por ID do usuário
    Optional<Restaurant> findByUserId(Long userId);

    // Buscar restaurantes por status ativo/inativo
    List<Restaurant> findByIsActive(Boolean isActive);

    // Buscar restaurantes por nome (case insensitive)
    List<Restaurant> findByNameContainingIgnoreCase(String name);

    // Verificar se CNPJ já existe
    boolean existsByCnpj(String cnpj);

    // Verificar se usuário já tem restaurante
    boolean existsByUserId(Long userId);

} 