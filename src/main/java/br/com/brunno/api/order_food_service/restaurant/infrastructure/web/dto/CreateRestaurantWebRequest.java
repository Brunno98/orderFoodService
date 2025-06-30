// Arquivo para o CreateRestaurantWebRequest - Camada de Infraestrutura Web

package br.com.brunno.api.order_food_service.restaurant.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CNPJ;

/**
 * DTO de requisição para criação de restaurante na camada web.
 * Contém os dados do restaurante a ser criado para recebimento do cliente.
 */
public class CreateRestaurantWebRequest {
    

    @NotNull(message = "ID do usuário é obrigatório")
    private String userId;

    @NotBlank(message = "Nome do restaurante é obrigatório")
    @Size(min = 2, max = 255, message = "Nome deve ter entre 2 e 255 caracteres")
    private String name;

    @CNPJ(message = "CNPJ inválido")
    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter exatamente 14 dígitos numéricos")
    private String cnpj;
    
    // Construtores
    public CreateRestaurantWebRequest() {}
    
    public CreateRestaurantWebRequest(String userId, String name, String cnpj) {
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
    }
    
    // Getters e Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
} 