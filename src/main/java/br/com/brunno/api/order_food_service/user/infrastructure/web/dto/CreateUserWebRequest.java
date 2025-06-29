package br.com.brunno.api.order_food_service.user.infrastructure.web.dto;

import br.com.brunno.api.order_food_service.user.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de entrada para criação de usuário na camada web.
 * Contém validações Bean Validation para os dados de entrada.
 */
public class CreateUserWebRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;
    
    @NotNull(message = "Tipo de usuário é obrigatório")
    private User.UserType tipo;
    
    // Construtores
    public CreateUserWebRequest() {}
    
    public CreateUserWebRequest(String nome, String email, User.UserType tipo) {
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }
    
    // Getters e Setters
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