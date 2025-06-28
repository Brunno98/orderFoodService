package br.com.brunno.api.order_food_service.restaurant.controller;

import br.com.brunno.api.order_food_service.restaurant.dto.RestaurantCreateRequest;
import br.com.brunno.api.order_food_service.restaurant.dto.RestaurantResponse;
import br.com.brunno.api.order_food_service.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por gerenciar as operações de restaurantes
 */
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    /**
     * POST /api/restaurants - Cadastrar novo restaurante
     */
    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(@Valid @RequestBody RestaurantCreateRequest request) {
        RestaurantResponse response = restaurantService.createRestaurant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/restaurants - Listar restaurantes
     */
    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<RestaurantResponse> restaurants = restaurantService.findAll();
        return ResponseEntity.ok(restaurants);
    }

    /**
     * GET /api/restaurants/{id} - Buscar restaurante por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable Long id) {
        RestaurantResponse restaurant = restaurantService.findById(id);
        return ResponseEntity.ok(restaurant);
    }

    // TODO: Implementar endpoints para:
    // - PUT /api/restaurants/{id} - Atualizar restaurante
    // - DELETE /api/restaurants/{id} - Deletar restaurante

} 