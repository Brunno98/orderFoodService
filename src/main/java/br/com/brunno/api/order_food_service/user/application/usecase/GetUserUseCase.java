// Arquivo para o GetUserUseCase - Camada de Aplicação 

package br.com.brunno.api.order_food_service.user.application.usecase;

import br.com.brunno.api.order_food_service.user.application.usecase.dto.get.GetUserResponse;
import br.com.brunno.api.order_food_service.user.domain.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * Caso de uso para busca de usuário por ID.
 * Implementa as regras de negócio para buscar um usuário específico.
 */
@Service
public class GetUserUseCase {
    
    private final UserRepository userRepository;
    
    /**
     * Construtor que recebe a dependência do repositório
     * @param userRepository repositório de usuários
     */
    public GetUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    /**
     * Busca um usuário pelo ID
     * @param id ID do usuário
     * @return Optional contendo o usuário se encontrado
     */
    public Optional<GetUserResponse> execute(UUID id) {
        return userRepository.findById(id)
                .map(GetUserResponse::new);
    }
    
} 