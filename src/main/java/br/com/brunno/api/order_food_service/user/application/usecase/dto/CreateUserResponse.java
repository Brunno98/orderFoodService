package br.com.brunno.api.order_food_service.user.application.usecase.dto;

import br.com.brunno.api.order_food_service.user.domain.entity.User;

import java.util.UUID;

/**
 * Resultado da criação de usuário.
 * Contém os dados do usuário criado para retorno ao cliente.
 */
public class CreateUserResponse {
    
    private final UUID id;
    private final String nome;
    private final String email;
    private final User.UserType tipo;
    
    /**
     * Construtor que recebe a entidade User para criar a resposta
     * @param user usuário criado
     */
    public CreateUserResponse(User user) {
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