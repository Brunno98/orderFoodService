package br.com.brunno.api.order_food_service.user.application.usecase.dto;

import java.util.UUID;

/**
 * DTO de resposta para exclusão de usuário.
 * Contém informações sobre o resultado da operação de exclusão.
 */
public class DeleteUserResponse {
    
    private final UUID userId;
    private final boolean deleted;
    private final String message;
    
    /**
     * Construtor que recebe o ID do usuário excluído
     * @param userId ID do usuário excluído
     */
    public DeleteUserResponse(UUID userId) {
        this.userId = userId;
        this.deleted = true;
        this.message = "Usuário excluído com sucesso";
    }
    
    /**
     * Construtor que recebe o ID do usuário e uma mensagem personalizada
     * @param userId ID do usuário excluído
     * @param message mensagem personalizada
     */
    public DeleteUserResponse(UUID userId, String message) {
        this.userId = userId;
        this.deleted = true;
        this.message = message;
    }
    
    /**
     * Retorna o ID do usuário excluído
     * @return ID do usuário
     */
    public UUID getUserId() {
        return userId;
    }
    
    /**
     * Indica se o usuário foi excluído com sucesso
     * @return true se foi excluído, false caso contrário
     */
    public boolean isDeleted() {
        return deleted;
    }
    
    /**
     * Retorna a mensagem de resposta
     * @return mensagem
     */
    public String getMessage() {
        return message;
    }
} 