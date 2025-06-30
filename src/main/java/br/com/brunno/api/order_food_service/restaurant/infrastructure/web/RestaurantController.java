// Arquivo para o RestaurantController - Camada de Infraestrutura 

package br.com.brunno.api.order_food_service.restaurant.infrastructure.web;

import br.com.brunno.api.order_food_service.restaurant.application.usecase.CreateRestaurantUseCase;
import br.com.brunno.api.order_food_service.restaurant.application.usecase.GetRestaurantUseCase;
import br.com.brunno.api.order_food_service.restaurant.application.usecase.ListRestaurantsUseCase;
import br.com.brunno.api.order_food_service.restaurant.application.dto.CreateRestaurantRequest;
import br.com.brunno.api.order_food_service.restaurant.application.dto.CreateRestaurantResponse;
import br.com.brunno.api.order_food_service.restaurant.application.dto.GetRestaurantResponse;
import br.com.brunno.api.order_food_service.restaurant.application.dto.ListRestaurantsResponse;
import br.com.brunno.api.order_food_service.restaurant.domain.exceptions.RestaurantNotFoundException;
import br.com.brunno.api.order_food_service.restaurant.infrastructure.web.dto.CreateRestaurantWebRequest;
import br.com.brunno.api.order_food_service.restaurant.infrastructure.web.dto.CreateRestaurantWebResponse;
import br.com.brunno.api.order_food_service.restaurant.infrastructure.web.dto.GetRestaurantWebResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller REST para operações de restaurante.
 * Responsável por expor endpoints HTTP para o módulo de restaurante.
 */
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    
    private final CreateRestaurantUseCase createRestaurantUseCase;
    private final GetRestaurantUseCase getRestaurantUseCase;
    private final ListRestaurantsUseCase listRestaurantsUseCase;
    
    /**
     * Cria um novo restaurante
     * @param request dados do restaurante a ser criado
     * @return dados do restaurante criado
     */
    @PostMapping
    public ResponseEntity<CreateRestaurantWebResponse> createRestaurant(@Valid @RequestBody CreateRestaurantWebRequest request) {
        // Converte DTO web para DTO do use case
        CreateRestaurantRequest useCaseRequest = new CreateRestaurantRequest(
            request.getUserId(),
            request.getName(),
            request.getCnpj()
        );
        
        // Executa o use case
        CreateRestaurantResponse useCaseResponse = createRestaurantUseCase.execute(useCaseRequest);
        
        // Converte resposta do use case para DTO web
        CreateRestaurantWebResponse webResponse = new CreateRestaurantWebResponse(
            useCaseResponse.getId(),
            useCaseResponse.getUserId(),
            useCaseResponse.getName(),
            useCaseResponse.getCnpj(),
            useCaseResponse.isActive(),
            useCaseResponse.getCreatedAt()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(webResponse);
    }
    
    /**
     * Busca um restaurante pelo ID
     * @param id ID do restaurante
     * @return dados do restaurante encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetRestaurantWebResponse> getRestaurantById(@PathVariable UUID id) {
        GetRestaurantResponse useCaseResponse = getRestaurantUseCase.execute(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado com ID: " + id));
        
        GetRestaurantWebResponse webResponse = new GetRestaurantWebResponse(
            useCaseResponse.getId(),
            useCaseResponse.getUserId(),
            useCaseResponse.getName(),
            useCaseResponse.getCnpj(),
            useCaseResponse.isActive(),
            useCaseResponse.getCreatedAt(),
            useCaseResponse.getUpdatedAt()
        );
        
        return ResponseEntity.ok(webResponse);
    }
    
    /**
     * Lista todos os restaurantes
     * @return lista de restaurantes
     */
    @GetMapping
    public ResponseEntity<List<GetRestaurantWebResponse>> getAllRestaurants() {
        ListRestaurantsResponse useCaseResponse = listRestaurantsUseCase.execute();
        
        List<GetRestaurantWebResponse> webResponses = useCaseResponse.getRestaurants().stream()
                .map(restaurantItem -> new GetRestaurantWebResponse(
                    restaurantItem.getId(),
                    restaurantItem.getUserId(),
                    restaurantItem.getName(),
                    restaurantItem.getCnpj(),
                    restaurantItem.isActive(),
                    restaurantItem.getCreatedAt(),
                    restaurantItem.getUpdatedAt()
                ))
                .toList();
        
        return ResponseEntity.ok(webResponses);
    }
} 