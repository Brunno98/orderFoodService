// Arquivo para o ListRestaurantsUseCase - Camada de Aplicação 

package br.com.brunno.api.order_food_service.restaurant.application.usecase;

import org.springframework.stereotype.Service;

import br.com.brunno.api.order_food_service.restaurant.application.dto.ListRestaurantsResponse;
import br.com.brunno.api.order_food_service.restaurant.domain.repository.RestaurantRepository;

/**
 * Caso de uso para listagem de restaurantes.
 * Implementa as regras de negócio para listar todos os restaurantes.
 */
@Service
public class ListRestaurantsUseCase {
    
    private final RestaurantRepository restaurantRepository;
    
    /**
     * Construtor que recebe a dependência do repositório
     * @param restaurantRepository repositório de restaurantes
     */
    public ListRestaurantsUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    
    /**
     * Lista todos os restaurantes
     * @return lista de restaurantes
     */
    public ListRestaurantsResponse execute() {
        return new ListRestaurantsResponse(restaurantRepository.findAll());
    }
} 