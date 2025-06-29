package br.com.brunno.api.order_food_service.restaurant.controller;

import br.com.brunno.api.order_food_service.restaurant.command.CreateRestaurantCommand;
import br.com.brunno.api.order_food_service.restaurant.dto.RestaurantCreateRequest;
import br.com.brunno.api.order_food_service.restaurant.dto.RestaurantResponse;
import br.com.brunno.api.order_food_service.restaurant.entity.Restaurant;
import br.com.brunno.api.order_food_service.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável por gerenciar as operações de restaurantes
 */
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * POST /api/restaurants - Cadastrar novo restaurante
     */
    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantCreateRequest request) {
        CreateRestaurantCommand command = new CreateRestaurantCommand(
            request.getUserId(),
            request.getName(),
            request.getCnpj()
        );
        
        Restaurant restaurant = restaurantService.createRestaurant(command);
        RestaurantResponse response = RestaurantResponse.fromEntity(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/restaurants - Listar restaurantes
     */
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.findAll();
        List<RestaurantResponse> responses = restaurants.stream()
                .map(RestaurantResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * GET /api/restaurants/{id} - Buscar restaurante por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        RestaurantResponse response = RestaurantResponse.fromEntity(restaurant);
        return ResponseEntity.ok(response);
    }

    // TODO: Implementar endpoints para:
    // - PUT /api/restaurants/{id} - Atualizar restaurante
    // - DELETE /api/restaurants/{id} - Deletar restaurante

} 