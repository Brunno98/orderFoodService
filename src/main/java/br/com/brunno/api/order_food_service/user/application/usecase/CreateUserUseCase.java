// Arquivo para o CreateUserUseCase - Camada de Aplicação 

package br.com.brunno.api.order_food_service.user.application.usecase;

import org.springframework.stereotype.Service;

import br.com.brunno.api.order_food_service.user.application.usecase.dto.CreateUserRequest;
import br.com.brunno.api.order_food_service.user.application.usecase.dto.CreateUserResponse;
import br.com.brunno.api.order_food_service.user.domain.entity.User;
import br.com.brunno.api.order_food_service.user.domain.exceptions.UserAlreadyExistsException;
import br.com.brunno.api.order_food_service.user.domain.repository.UserRepository;

/**
 * Caso de uso para criação de usuários.
 * Implementa as regras de negócio para criação de um novo usuário.
 */
@Service
public class CreateUserUseCase {
    
    private final UserRepository userRepository;
    
    /**
     * Construtor que recebe a dependência do repositório
     * @param userRepository repositório de usuários
     */
    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Executa o caso de uso de criação de usuário
     * @param request dados para criação do usuário
     * @return resposta com os dados do usuário criado
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws UserAlreadyExistsException se já existir usuário com o email fornecido
     */
    public CreateUserResponse execute(CreateUserRequest request) {
        // Validações de entrada
        if (request == null) {
            throw new IllegalArgumentException("Request não pode ser nulo");
        }
        
        request.validate();
        
        // Verifica se já existe usuário com o email fornecido
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Já existe um usuário com o email: " + request.getEmail());
        }
        
        // Cria a entidade de domínio
        User user = new User(request.getNome(), request.getEmail(), request.getTipo());
        
        // Salva no repositório
        User savedUser = userRepository.save(user);
        
        // Retorna a resposta
        return new CreateUserResponse(savedUser);
    }
} 