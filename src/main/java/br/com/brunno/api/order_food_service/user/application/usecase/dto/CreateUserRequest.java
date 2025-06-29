package br.com.brunno.api.order_food_service.user.application.usecase.dto;

import br.com.brunno.api.order_food_service.user.domain.entity.User;

/**
 * Dados de entrada para criação de usuário.
 * Contém os dados necessários para criar um novo usuário.
 */
public class CreateUserRequest {
    
    private final String nome;
    private final String email;
    private final User.UserType tipo;
    
    /**
     * Construtor que recebe os dados para criação do usuário
     * @param nome nome do usuário
     * @param email email do usuário
     * @param tipo tipo do usuário (CLIENTE ou RESTAURANTE)
     */
    public CreateUserRequest(String nome, String email, User.UserType tipo) {
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }
    
    /**
     * Valida os dados de entrada
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public void validate() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de usuário não pode ser nulo");
        }
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