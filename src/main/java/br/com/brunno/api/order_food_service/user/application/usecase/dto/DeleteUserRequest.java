package br.com.brunno.api.order_food_service.user.application.usecase.dto;

import java.util.UUID;

/**
 * DTO de requisição para exclusão de usuário.
 * Contém os dados necessários para identificar o usuário a ser excluído.
 */
public class DeleteUserRequest {
    
    private final UUID userId;
    
    /**
     * Construtor que recebe o ID do usuário a ser excluído
     * @param userId ID do usuário
     */
    public DeleteUserRequest(UUID userId) {
        this.userId = userId;
    }
    
    /**
     * Construtor que recebe o ID do usuário como String
     * @param userId ID do usuário como String
     */
    public DeleteUserRequest(String userId) {
        this.userId = UUID.fromString(userId);
    }
    
    /**
     * Retorna o ID do usuário
     * @return ID do usuário
     */
    public UUID getUserId() {
        return userId;
    }
    
    /**
     * Valida os dados da requisição
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public void validate() {
        if (userId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }
    }
} 