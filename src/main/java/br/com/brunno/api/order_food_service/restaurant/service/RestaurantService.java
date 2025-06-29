package br.com.brunno.api.order_food_service.restaurant.service;

import br.com.brunno.api.order_food_service.exception.ResourceNotFoundException;
import br.com.brunno.api.order_food_service.restaurant.command.CreateRestaurantCommand;
import br.com.brunno.api.order_food_service.restaurant.entity.Restaurant;
import br.com.brunno.api.order_food_service.restaurant.exception.RestaurantException;
import br.com.brunno.api.order_food_service.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Restaurant createRestaurant(CreateRestaurantCommand command) {
        // Validar dados do restaurante
        validateRestaurantData(command);

        // Verificar se CNPJ já existe
        if (restaurantRepository.existsByCnpj(command.getCnpj())) {
            throw RestaurantException.cnpjAlreadyExists(command.getCnpj());
        }

        // Verificar se usuário já tem restaurante
        if (restaurantRepository.existsByUserId(command.getUserId())) {
            throw RestaurantException.userAlreadyHasRestaurant(command.getUserId());
        }

        // Criar entidade do restaurante
        Restaurant restaurant = new Restaurant(
            command.getUserId(),
            command.getName(),
            command.getCnpj()
        );

        // Salvar no banco de dados e retornar entidade
        return restaurantRepository.save(restaurant);
    }

    /**
     * Listar todos os restaurantes
     */
    public List<Restaurant> findAll() {
        return restaurantRepository.findAll();
    }

    /**
     * Buscar restaurante por ID
     */
    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.restaurantNotFound(id));
    }

    /**
     * Validar dados do restaurante
     */
    private void validateRestaurantData(CreateRestaurantCommand command) {
        if (command.getUserId() == null) {
            throw RestaurantException.invalidRestaurantData("userId");
        }
        if (command.getName() == null || command.getName().trim().isEmpty()) {
            throw RestaurantException.invalidRestaurantData("name");
        }
        if (command.getCnpj() == null || command.getCnpj().trim().isEmpty()) {
            throw RestaurantException.invalidRestaurantData("cnpj");
        }
    }

} 