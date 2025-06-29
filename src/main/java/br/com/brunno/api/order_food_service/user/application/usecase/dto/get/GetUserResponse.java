package br.com.brunno.api.order_food_service.user.application.usecase.dto.get;

import br.com.brunno.api.order_food_service.user.domain.entity.User;

import java.util.UUID;

/**
 * Resultado da busca de usuário por ID.
 */
public class GetUserResponse {
    
    private final UUID id;
    private final String nome;
    private final String email;
    private final User.UserType tipo;
    
    public GetUserResponse(User user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.tipo = user.getTipo();
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public User.UserType getTipo() {
        return tipo;
    }
} 