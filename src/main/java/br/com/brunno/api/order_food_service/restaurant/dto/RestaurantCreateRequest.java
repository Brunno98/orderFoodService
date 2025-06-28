package br.com.brunno.api.order_food_service.restaurant.dto;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO para requisição de criação de restaurante
 */
public class RestaurantCreateRequest {

    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;

    @NotBlank(message = "Nome do restaurante é obrigatório")
    @Size(min = 2, max = 255, message = "Nome deve ter entre 2 e 255 caracteres")
    private String name;

    @CNPJ(message = "CNPJ inválido")
    @NotBlank(message = "CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "CNPJ deve conter exatamente 14 dígitos numéricos")
    private String cnpj;

    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String description;

    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    private String address;

    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve conter 10 ou 11 dígitos numéricos")
    private String phone;

    // Construtores
    public RestaurantCreateRequest() {}

    public RestaurantCreateRequest(Long userId, String name, String cnpj, String description, String address, String phone) {
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
        this.description = description;
        this.address = address;
        this.phone = phone;
    }

    // Getters e Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "RestaurantCreateRequest{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
} 