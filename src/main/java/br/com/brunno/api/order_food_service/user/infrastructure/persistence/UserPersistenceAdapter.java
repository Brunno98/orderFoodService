// Arquivo para o UserPersistenceAdapter - Camada de Infraestrutura 

package br.com.brunno.api.order_food_service.user.infrastructure.persistence;

import br.com.brunno.api.order_food_service.user.domain.entity.User;
import br.com.brunno.api.order_food_service.user.domain.repository.UserRepository;
import br.com.brunno.api.order_food_service.user.infrastructure.persistence.entity.UserJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adaptador de persistência que implementa UserRepository.
 * Responsável por converter entre entidades de domínio e JPA.
 */
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    
    @Override
    public User save(User user) {
        UserJpaEntity userJpaEntity = UserJpaEntity.fromDomain(user);
        UserJpaEntity savedEntity = userJpaRepository.save(userJpaEntity);
        return savedEntity.toDomain();
    }
    
    @Override
    public Optional<User> findById(UUID id) {
        return userJpaRepository.findByDomainId(id)
                .map(UserJpaEntity::toDomain);
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserJpaEntity::toDomain);
    }
    
    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(UserJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
    
    @Override
    @Transactional
    public void deleteById(UUID id) {
        userJpaRepository.deleteByDomainId(id);
    }
} 