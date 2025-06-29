// Arquivo para a interface UserRepository - Camada de Domínio 

package br.com.brunno.api.order_food_service.user.domain.repository;

import br.com.brunno.api.order_food_service.user.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface do repositório de usuários (porta da arquitetura hexagonal).
 * Define os contratos para persistência de usuários.
 */
public interface UserRepository {
    
    /**
     * Salva um usuário no repositório
     * @param user usuário a ser salvo
     * @return usuário salvo
     */
    User save(User user);
    
    /**
     * Busca um usuário pelo ID
     * @param id ID do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<User> findById(UUID id);
    
    /**
     * Busca um usuário pelo email
     * @param email email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Lista todos os usuários
     * @return lista de usuários
     */
    List<User> findAll();
    
    /**
     * Verifica se existe um usuário com o email fornecido
     * @param email email a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);
    
    /**
     * Deleta um usuário pelo ID
     * @param id ID do usuário a ser deletado
     */
    void deleteById(UUID id);
} 