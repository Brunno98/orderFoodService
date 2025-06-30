// Arquivo para o CreateRestaurantRequest - Camada de Aplicação 

package br.com.brunno.api.order_food_service.restaurant.application.dto;

/**
 * Dados de entrada para criação de restaurante.
 * Contém os dados necessários para criar um novo restaurante.
 */
public class CreateRestaurantRequest {
    
    private final String userId;
    private final String name;
    private final String cnpj;
    
    /**
     * Construtor que recebe os dados para criação do restaurante
     * @param userId ID do usuário proprietário
     * @param name nome do restaurante
     * @param cnpj CNPJ do restaurante
     */
    public CreateRestaurantRequest(String userId, String name, String cnpj) {
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
    }
    
    /**
     * Valida os dados de entrada
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public void validate() {
        if (userId == null) {
            throw new IllegalArgumentException("ID do usuário não pode ser nulo");
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ não pode ser vazio");
        }
    }
    
    public String getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCnpj() {
        return cnpj;
    }
} 