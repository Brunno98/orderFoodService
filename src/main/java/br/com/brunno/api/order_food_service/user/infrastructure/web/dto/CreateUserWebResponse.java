package br.com.brunno.api.order_food_service.user.infrastructure.web.dto;

import br.com.brunno.api.order_food_service.user.domain.entity.User;

import java.util.UUID;

/**
 * DTO de resposta para criação de usuário na camada web.
 * Contém os dados do usuário criado para retorno ao cliente.
 */
public class CreateUserWebResponse {
    
    private UUID id;
    private String nome;
    private String email;
    private User.UserType tipo;
    
    // Construtores
    public CreateUserWebResponse() {}
    
    public CreateUserWebResponse(UUID id, String nome, String email, User.UserType tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }
    
    // Getters e Setters
    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public User.UserType getTipo() {
        return tipo;
    }
    
    public void setTipo(User.UserType tipo) {
        this.tipo = tipo;
    }
} 