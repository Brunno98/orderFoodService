package br.com.brunno.api.order_food_service.user.infrastructure.persistence.entity;

import br.com.brunno.api.order_food_service.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Entidade JPA para persistência de usuários.
 * Representa a tabela users no banco de dados.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID domainId;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private User.UserType tipo;
    
    public UserJpaEntity(UUID domainId, String nome, String email, User.UserType tipo) {
        this.domainId = domainId;
        this.nome = nome;
        this.email = email;
        this.tipo = tipo;
    }

    /**
     * Converte a entidade JPA para a entidade de domínio
     * @return entidade de domínio User
     */
    public User toDomain() {
        return new User(domainId, nome, email, tipo);
    }
    
    /**
     * Cria uma entidade JPA a partir da entidade de domínio
     * @param user entidade de domínio
     * @return entidade JPA
     */
    public static UserJpaEntity fromDomain(User user) {
        return new UserJpaEntity(
            user.getId(),
            user.getNome(),
            user.getEmail(),
            user.getTipo()
        );
    }
} 