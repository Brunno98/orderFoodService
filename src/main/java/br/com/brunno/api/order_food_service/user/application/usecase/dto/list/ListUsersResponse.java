package br.com.brunno.api.order_food_service.user.application.usecase.dto.list;

import br.com.brunno.api.order_food_service.user.domain.entity.User;

import java.util.List;
import java.util.UUID;

/**
 * Resultado da listagem de usuários.
 */
public class ListUsersResponse {
    
    private final List<UserItem> users;
    
    public ListUsersResponse(List<User> users) {
        this.users = users.stream()
                .map(UserItem::new)
                .toList();
    }
    
    public List<UserItem> getUsers() {
        return users;
    }
    
    /**
     * Item individual de usuário na listagem
     */
    public static class UserItem {
        private final UUID id;
        private final String nome;
        private final String email;
        private final User.UserType tipo;
        
        public UserItem(User user) {
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
} 