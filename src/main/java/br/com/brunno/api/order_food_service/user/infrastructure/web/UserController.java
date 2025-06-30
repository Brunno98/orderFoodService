// Arquivo para o UserController - Camada de Infraestrutura 

package br.com.brunno.api.order_food_service.user.infrastructure.web;

import br.com.brunno.api.order_food_service.user.application.usecase.CreateUserUseCase;
import br.com.brunno.api.order_food_service.user.application.usecase.DeleteUserUseCase;
import br.com.brunno.api.order_food_service.user.application.usecase.GetUserUseCase;
import br.com.brunno.api.order_food_service.user.application.usecase.ListUsersUseCase;
import br.com.brunno.api.order_food_service.user.application.usecase.dto.CreateUserRequest;
import br.com.brunno.api.order_food_service.user.application.usecase.dto.CreateUserResponse;
import br.com.brunno.api.order_food_service.user.application.usecase.dto.DeleteUserRequest;
import br.com.brunno.api.order_food_service.user.application.usecase.dto.get.GetUserResponse;
import br.com.brunno.api.order_food_service.user.application.usecase.dto.list.ListUsersResponse;
import br.com.brunno.api.order_food_service.user.domain.exceptions.UserNotFoundException;
import br.com.brunno.api.order_food_service.user.infrastructure.web.dto.CreateUserWebRequest;
import br.com.brunno.api.order_food_service.user.infrastructure.web.dto.CreateUserWebResponse;
import br.com.brunno.api.order_food_service.user.infrastructure.web.dto.GetUserWebResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller REST para operações de usuário.
 * Responsável por expor endpoints HTTP para o módulo de usuário.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final ListUsersUseCase listUsersUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    
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
    
    /**
     * Busca um usuário pelo ID
     * @param id ID do usuário
     * @return dados do usuário encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetUserWebResponse> getUserById(@PathVariable UUID id) {
        GetUserResponse useCaseResponse = getUserUseCase.execute(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: " + id));
        
        GetUserWebResponse webResponse = new GetUserWebResponse(
            useCaseResponse.getId(),
            useCaseResponse.getNome(),
            useCaseResponse.getEmail(),
            useCaseResponse.getTipo()
        );
        
        return ResponseEntity.ok(webResponse);
    }
    
    /**
     * Lista todos os usuários
     * @return lista de usuários
     */
    @GetMapping
    public ResponseEntity<List<GetUserWebResponse>> getAllUsers() {
        ListUsersResponse useCaseResponse = listUsersUseCase.execute();
        
        List<GetUserWebResponse> webResponses = useCaseResponse.getUsers().stream()
                .map(userItem -> new GetUserWebResponse(
                    userItem.getId(),
                    userItem.getNome(),
                    userItem.getEmail(),
                    userItem.getTipo()
                ))
                .toList();
        
        return ResponseEntity.ok(webResponses);
    }
    
    /**
     * Exclui um usuário pelo ID
     * @param id ID do usuário a ser excluído
     * @return resposta com informações sobre a exclusão
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        DeleteUserRequest request = new DeleteUserRequest(id);
        deleteUserUseCase.execute(request);
        
        return ResponseEntity.noContent().build();
    }
    
} 