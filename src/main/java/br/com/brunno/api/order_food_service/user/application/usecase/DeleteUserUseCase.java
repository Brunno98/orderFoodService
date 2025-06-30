// Arquivo para o DeleteUserUseCase - Camada de Aplicação 

package br.com.brunno.api.order_food_service.user.application.usecase;

import org.springframework.stereotype.Service;

import br.com.brunno.api.order_food_service.user.application.usecase.dto.DeleteUserRequest;
import br.com.brunno.api.order_food_service.user.application.usecase.dto.DeleteUserResponse;
import br.com.brunno.api.order_food_service.user.domain.exceptions.UserNotFoundException;
import br.com.brunno.api.order_food_service.user.domain.repository.UserRepository;

/**
 * Caso de uso para exclusão de usuários.
 * Implementa as regras de negócio para exclusão de um usuário existente.
 */
@Service
public class DeleteUserUseCase {
    
    private final UserRepository userRepository;
    
    /**
     * Construtor que recebe a dependência do repositório
     * @param userRepository repositório de usuários
     */
    public DeleteUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Executa o caso de uso de exclusão de usuário
     * @param request dados para exclusão do usuário
     * @return resposta com informações sobre a exclusão
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws UserNotFoundException se o usuário não for encontrado
     */
    public DeleteUserResponse execute(DeleteUserRequest request) {
        // Validações de entrada
        if (request == null) {
            throw new IllegalArgumentException("Request não pode ser nulo");
        }
        
        request.validate();
        
        // Verifica se o usuário existe antes de tentar excluir
        if (!userRepository.findById(request.getUserId()).isPresent()) {
            throw new UserNotFoundException("Usuário não encontrado com o ID: " + request.getUserId());
        }
        
        // Executa a exclusão
        userRepository.deleteById(request.getUserId());
        
        // Retorna a resposta
        return new DeleteUserResponse(request.getUserId());
    }
    
} 