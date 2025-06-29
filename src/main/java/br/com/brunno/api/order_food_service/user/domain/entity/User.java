// Arquivo para a entidade User - Camada de Domínio 

package br.com.brunno.api.order_food_service.user.domain.entity;

import java.util.Objects;
import java.util.UUID;

/**
 * Entidade de domínio User representando um usuário do sistema.
 * Segue a arquitetura hexagonal sem influências de ORM.
 */
public class User {
    
    private final UUID id;
    private String nome;
    private String email;
    private UserType tipo;
    
    /**
     * Enum para definir os tipos de usuário conforme especificado no README.md
     */
    public enum UserType {
        CLIENTE,
        RESTAURANTE
    }
    
    /**
     * Construtor para criação de um novo usuário
     */
    public User(String nome, String email, UserType tipo) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        validate();
    }
    
    /**
     * Construtor para reconstrução de um usuário existente
     */
    public User(UUID id, String nome, String email, UserType tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
        validate();
    }
    
    /**
     * Validações de domínio
     */
    private void validate() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email deve ter formato válido");
        }
        
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de usuário não pode ser nulo");
        }
    }
    
    /**
     * Validação simples de email
     */
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && !(email.startsWith("@") || email.endsWith("@"));
    }
    
    /**
     * Método para atualizar os dados do usuário
     */
    public void update(String nome, String email) {
        if (nome != null && !nome.trim().isEmpty()) {
            this.nome = nome;
        }
        
        if (email != null && !email.trim().isEmpty()) {
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Email deve ter formato válido");
            }
            this.email = email;
        }
        
        validate();
    }
    
    /**
     * Verifica se o usuário é do tipo CLIENTE
     */
    public boolean isCliente() {
        return UserType.CLIENTE.equals(this.tipo);
    }
    
    /**
     * Verifica se o usuário é do tipo RESTAURANTE
     */
    public boolean isRestaurante() {
        return UserType.RESTAURANTE.equals(this.tipo);
    }
    
    // Getters
    public UUID getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public UserType getTipo() {
        return tipo;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", tipo=" + tipo +
                '}';
    }
} 