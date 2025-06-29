// Arquivo para o UserController - Camada de Infraestrutura 

package br.com.brunno.api.order_food_service.user.infrastructure.web;

import br.com.brunno.api.order_food_service.user.application.usecase.CreateUserUseCase;
import br.com.brunno.api.order_food_service.user.application.usecase.dto.CreateUserRequest;
import br.com.brunno.api.order_food_service.user.application.usecase.dto.CreateUserResponse;
import br.com.brunno.api.order_food_service.user.infrastructure.web.dto.CreateUserWebRequest;
import br.com.brunno.api.order_food_service.user.infrastructure.web.dto.CreateUserWebResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para operações de usuário.
 * Responsável por expor endpoints HTTP para o módulo de usuário.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final CreateUserUseCase createUserUseCase;
    
    /**
     * Cria um novo usuário
     * @param request dados do usuário a ser criado
     * @return dados do usuário criado
     */
    @PostMapping
    public ResponseEntity<CreateUserWebResponse> createUser(@Valid @RequestBody CreateUserWebRequest request) {
        // Converte DTO web para DTO do use case
        CreateUserRequest useCaseRequest = new CreateUserRequest(
            request.getNome(),
            request.getEmail(),
            request.getTipo()
        );
        
        // Executa o use case
        CreateUserResponse useCaseResponse = createUserUseCase.execute(useCaseRequest);
        
        // Converte resposta do use case para DTO web
        CreateUserWebResponse webResponse = new CreateUserWebResponse(
            useCaseResponse.getId(),
            useCaseResponse.getNome(),
            useCaseResponse.getEmail(),
            useCaseResponse.getTipo()
        );
        
        return ResponseEntity.status(HttpStatus.CREATED).body(webResponse);
    }
} 