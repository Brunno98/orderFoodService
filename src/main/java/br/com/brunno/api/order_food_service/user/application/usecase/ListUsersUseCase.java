package br.com.brunno.api.order_food_service.user.application.usecase;

import org.springframework.stereotype.Service;

import br.com.brunno.api.order_food_service.user.application.usecase.dto.list.ListUsersResponse;
import br.com.brunno.api.order_food_service.user.domain.repository.UserRepository;

/**
 * Caso de uso para listagem de usuários.
 * Implementa as regras de negócio para listar todos os usuários.
 */
@Service
public class ListUsersUseCase {
    
    private final UserRepository userRepository;
    
    /**
     * Construtor que recebe a dependência do repositório
     * @param userRepository repositório de usuários
     */
    public ListUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Lista todos os usuários
     * @param request dados da requisição (por enquanto vazio)
     * @return lista de usuários
     */
    public ListUsersResponse execute() {
        return new ListUsersResponse(userRepository.findAll());
    }
} 