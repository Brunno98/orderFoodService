// Arquivo para o UserJpaRepository - Camada de Infraestrutura 

package br.com.brunno.api.order_food_service.user.infrastructure.persistence;

import br.com.brunno.api.order_food_service.user.infrastructure.persistence.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface JPA para persistência de usuários.
 * Responsável pela comunicação direta com o banco de dados.
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    
    /**
     * Busca um usuário pelo email
     * @param email email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<UserJpaEntity> findByEmail(String email);
    
    /**
     * Verifica se existe um usuário com o email fornecido
     * @param email email a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);

    /**
     * Busca um usuário pelo id de domínio
     * @param id ID de domínio
     * @return Optional contendo o usuário se encontrado
     */
    Optional<UserJpaEntity> findByDomainId(UUID id);

    /**
     * Deleta um usuário pelo id de domínio
     * @param id ID de domínio
     */
    void deleteByDomainId(UUID id);
} 