package br.com.brunno.api.order_food_service.restaurant.service;

import br.com.brunno.api.order_food_service.restaurant.dto.RestaurantCreateRequest;
import br.com.brunno.api.order_food_service.restaurant.dto.RestaurantResponse;
import br.com.brunno.api.order_food_service.restaurant.entity.Restaurant;
import br.com.brunno.api.order_food_service.restaurant.exception.RestaurantException;
import br.com.brunno.api.order_food_service.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsável pela lógica de negócio dos restaurantes
 */
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * Criar um novo restaurante
     */
    @Transactional
    public RestaurantResponse createRestaurant(RestaurantCreateRequest request) {
        // Validar dados do restaurante
        validateRestaurantData(request);

        // Verificar se CNPJ já existe
        if (restaurantRepository.existsByCnpj(request.getCnpj())) {
            throw RestaurantException.cnpjAlreadyExists(request.getCnpj());
        }

        // Verificar se usuário já tem restaurante
        if (restaurantRepository.existsByUserId(request.getUserId())) {
            throw RestaurantException.userAlreadyHasRestaurant(request.getUserId());
        }

        // Criar entidade do restaurante
        Restaurant restaurant = new Restaurant(
            request.getUserId(),
            request.getName(),
            request.getCnpj(),
            request.getDescription(),
            request.getAddress(),
            request.getPhone()
        );

        // Salvar no banco de dados
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        // Retornar DTO de resposta
        return RestaurantResponse.fromEntity(savedRestaurant);
    }

    /**
     * Listar todos os restaurantes
     */
    public List<RestaurantResponse> findAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream()
                .map(RestaurantResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Buscar restaurante por ID
     */
    public RestaurantResponse findById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> RestaurantException.restaurantNotFound(id));
        return RestaurantResponse.fromEntity(restaurant);
    }

    /**
     * Validar dados do restaurante
     */
    private void validateRestaurantData(RestaurantCreateRequest request) {
        if (request.getUserId() == null) {
            throw RestaurantException.invalidRestaurantData("userId");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw RestaurantException.invalidRestaurantData("name");
        }
        if (request.getCnpj() == null || request.getCnpj().trim().isEmpty()) {
            throw RestaurantException.invalidRestaurantData("cnpj");
        }
    }

} 