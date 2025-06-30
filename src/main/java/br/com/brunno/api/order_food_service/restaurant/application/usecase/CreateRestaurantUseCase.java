// Arquivo para o CreateRestaurantUseCase - Camada de Aplicação 

package br.com.brunno.api.order_food_service.restaurant.application.usecase;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.brunno.api.order_food_service.restaurant.application.dto.CreateRestaurantRequest;
import br.com.brunno.api.order_food_service.restaurant.application.dto.CreateRestaurantResponse;
import br.com.brunno.api.order_food_service.restaurant.domain.entity.Restaurant;
import br.com.brunno.api.order_food_service.restaurant.domain.exceptions.RestaurantAlreadyExistsException;
import br.com.brunno.api.order_food_service.restaurant.domain.repository.RestaurantRepository;

/**
 * Caso de uso para criação de restaurantes.
 * Implementa as regras de negócio para criação de um novo restaurante.
 */
@Service
public class CreateRestaurantUseCase {
    
    private final RestaurantRepository restaurantRepository;
    
    /**
     * Construtor que recebe a dependência do repositório
     * @param restaurantRepository repositório de restaurantes
     */
    public CreateRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }
    
    /**
     * Executa o caso de uso de criação de restaurante
     * @param request dados para criação do restaurante
     * @return resposta com informações sobre o restaurante criado
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws RestaurantAlreadyExistsException se já existir restaurante com o CNPJ ou usuário
     */
    @Transactional
    public CreateRestaurantResponse execute(CreateRestaurantRequest request) {
        // Validações de entrada
        if (request == null) {
            throw new IllegalArgumentException("Request não pode ser nulo");
        }
        
        request.validate();
        
        // Verifica se já existe restaurante com o CNPJ fornecido
        if (restaurantRepository.existsByCnpj(request.getCnpj())) {
            throw RestaurantAlreadyExistsException.byCnpj(request.getCnpj());
        }
        
        // Verifica se o usuário já tem um restaurante cadastrado
        if (restaurantRepository.existsByUserId(request.getUserId())) {
            throw RestaurantAlreadyExistsException.byUserId(request.getUserId());
        }
        
        // Cria a entidade de domínio
        Restaurant restaurant = new Restaurant(request.getUserId(), request.getName(), request.getCnpj());
        
        // Salva no repositório
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        
        // Retorna a resposta
        return new CreateRestaurantResponse(savedRestaurant);
    }
} 