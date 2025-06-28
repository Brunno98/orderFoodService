package br.com.brunno.api.order_food_service.restaurant.command;

/**
 * Command para criação de restaurante
 */
public class CreateRestaurantCommand {

    private final Long userId;
    private final String name;
    private final String cnpj;

    public CreateRestaurantCommand(Long userId, String name, String cnpj) {
        this.userId = userId;
        this.name = name;
        this.cnpj = cnpj;
    }

    // Getters
    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    @Override
    public String toString() {
        return "RestaurantCommand{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
} 