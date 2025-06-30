// Arquivo para o RestaurantPersistenceAdapter - Camada de Infraestrutura 

package br.com.brunno.api.order_food_service.restaurant.infrastructure.persistence;

import br.com.brunno.api.order_food_service.restaurant.domain.entity.Restaurant;
import br.com.brunno.api.order_food_service.restaurant.domain.repository.RestaurantRepository;
import br.com.brunno.api.order_food_service.restaurant.infrastructure.persistence.entity.RestaurantJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adaptador de persistência que implementa RestaurantRepository.
 * Responsável por converter entre entidades de domínio e JPA.
 */
@Component
@RequiredArgsConstructor
public class RestaurantPersistenceAdapter implements RestaurantRepository {
    
    private final RestaurantJpaRepository restaurantJpaRepository;
    
    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantJpaEntity restaurantJpaEntity = RestaurantJpaEntity.fromDomain(restaurant);
        RestaurantJpaEntity savedEntity = restaurantJpaRepository.save(restaurantJpaEntity);
        return savedEntity.toDomain();
    }
    
    @Override
    public Optional<Restaurant> findById(UUID id) {
        return restaurantJpaRepository.findByDomainId(id)
                .map(RestaurantJpaEntity::toDomain);
    }
    
    @Override
    public Optional<Restaurant> findByCnpj(String cnpj) {
        return restaurantJpaRepository.findByCnpj(cnpj)
                .map(RestaurantJpaEntity::toDomain);
    }
    
    @Override
    public Optional<Restaurant> findByUserId(String userId) {
        return restaurantJpaRepository.findByUserId(userId)
                .map(RestaurantJpaEntity::toDomain);
    }
    
    @Override
    public List<Restaurant> findAll() {
        return restaurantJpaRepository.findAll()
                .stream()
                .map(RestaurantJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Restaurant> findByIsActive(boolean isActive) {
        return restaurantJpaRepository.findByIsActive(isActive)
                .stream()
                .map(RestaurantJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Restaurant> findByNameContainingIgnoreCase(String name) {
        return restaurantJpaRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(RestaurantJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByCnpj(String cnpj) {
        return restaurantJpaRepository.existsByCnpj(cnpj);
    }
    
    @Override
    public boolean existsByUserId(String userId) {
        return restaurantJpaRepository.existsByUserId(userId);
    }
    
    @Override
    public void deleteById(UUID id) {
        restaurantJpaRepository.deleteByDomainId(id);
    }
} 