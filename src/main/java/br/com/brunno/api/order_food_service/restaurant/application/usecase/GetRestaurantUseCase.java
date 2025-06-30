// Arquivo para o GetRestaurantUseCase - Camada de Aplicação 

package br.com.brunno.api.order_food_service.restaurant.application.usecase;

import br.com.brunno.api.order_food_service.restaurant.application.dto.GetRestaurantResponse;
import br.com.brunno.api.order_food_service.restaurant.domain.repository.RestaurantRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * Caso de uso para busca de restaurante por ID.
 * Implementa as regras de negócio para buscar um restaurante específico.
 */
@Service
public class GetRestaurantUseCase {
    
    private final RestaurantRepository restaurantRepository;
    
    /**
     * Construtor que recebe a dependência do repositório
     * @param restaurantRepository repositório de restaurantes
     */
    public GetRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    
    /**
     * Busca um restaurante pelo ID
     * @param id ID do restaurante
     * @return Optional contendo o restaurante se encontrado
     */
    public Optional<GetRestaurantResponse> execute(UUID id) {
        return restaurantRepository.findById(id)
                .map(GetRestaurantResponse::new);
    }
} 