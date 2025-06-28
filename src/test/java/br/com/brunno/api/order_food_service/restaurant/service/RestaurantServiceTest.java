package br.com.brunno.api.order_food_service.restaurant.service;

import br.com.brunno.api.order_food_service.restaurant.command.CreateRestaurantCommand;
import br.com.brunno.api.order_food_service.restaurant.entity.Restaurant;
import br.com.brunno.api.order_food_service.restaurant.exception.RestaurantException;
import br.com.brunno.api.order_food_service.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe RestaurantService
 */
@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private CreateRestaurantCommand validCommand;
    private Restaurant validRestaurant;

    @BeforeEach
    void setUp() {
        validCommand = new CreateRestaurantCommand(1L, "Restaurante Teste", "11222333000181");
        validRestaurant = new Restaurant(1L, "Restaurante Teste", "11222333000181");
        validRestaurant.setId(1L);
        validRestaurant.setCreatedAt(LocalDateTime.now());
    }

    @Test
    @DisplayName("Deve criar restaurante com sucesso quando dados são válidos")
    void deveCriarRestauranteComSucesso() {
        // Arrange
        when(restaurantRepository.existsByCnpj(validCommand.getCnpj())).thenReturn(false);
        when(restaurantRepository.existsByUserId(validCommand.getUserId())).thenReturn(false);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(validRestaurant);

        // Act
        Restaurant result = restaurantService.createRestaurant(validCommand);

        // Assert
        assertNotNull(result);
        assertEquals(validRestaurant.getId(), result.getId());
        assertEquals(validRestaurant.getName(), result.getName());
        assertEquals(validRestaurant.getCnpj(), result.getCnpj());
        assertEquals(validRestaurant.getUserId(), result.getUserId());
        
        verify(restaurantRepository).existsByCnpj(validCommand.getCnpj());
        verify(restaurantRepository).existsByUserId(validCommand.getUserId());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando userId é null")
    void deveLancarExcecaoQuandoUserIdENull() {
        // Arrange
        CreateRestaurantCommand command = new CreateRestaurantCommand(null, "Restaurante Teste", "11222333000181");

        // Act & Assert
        RestaurantException exception = assertThrows(RestaurantException.class, 
            () -> restaurantService.createRestaurant(command));
        
        assertEquals("Dados inválidos do restaurante. Campo: userId", exception.getMessage());
        verify(restaurantRepository, never()).existsByCnpj(any());
        verify(restaurantRepository, never()).existsByUserId(any());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é null")
    void deveLancarExcecaoQuandoNomeENull() {
        // Arrange
        CreateRestaurantCommand command = new CreateRestaurantCommand(1L, null, "11222333000181");

        // Act & Assert
        RestaurantException exception = assertThrows(RestaurantException.class, 
            () -> restaurantService.createRestaurant(command));
        
        assertEquals("Dados inválidos do restaurante. Campo: name", exception.getMessage());
        verify(restaurantRepository, never()).existsByCnpj(any());
        verify(restaurantRepository, never()).existsByUserId(any());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome é vazio")
    void deveLancarExcecaoQuandoNomeEVazio() {
        // Arrange
        CreateRestaurantCommand command = new CreateRestaurantCommand(1L, "", "11222333000181");

        // Act & Assert
        RestaurantException exception = assertThrows(RestaurantException.class, 
            () -> restaurantService.createRestaurant(command));
        
        assertEquals("Dados inválidos do restaurante. Campo: name", exception.getMessage());
        verify(restaurantRepository, never()).existsByCnpj(any());
        verify(restaurantRepository, never()).existsByUserId(any());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome contém apenas espaços")
    void deveLancarExcecaoQuandoNomeContemApenasEspacos() {
        // Arrange
        CreateRestaurantCommand command = new CreateRestaurantCommand(1L, "   ", "11222333000181");

        // Act & Assert
        RestaurantException exception = assertThrows(RestaurantException.class, 
            () -> restaurantService.createRestaurant(command));
        
        assertEquals("Dados inválidos do restaurante. Campo: name", exception.getMessage());
        verify(restaurantRepository, never()).existsByCnpj(any());
        verify(restaurantRepository, never()).existsByUserId(any());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando CNPJ é null")
    void deveLancarExcecaoQuandoCnpjENull() {
        // Arrange
        CreateRestaurantCommand command = new CreateRestaurantCommand(1L, "Restaurante Teste", null);

        // Act & Assert
        RestaurantException exception = assertThrows(RestaurantException.class, 
            () -> restaurantService.createRestaurant(command));
        
        assertEquals("Dados inválidos do restaurante. Campo: cnpj", exception.getMessage());
        verify(restaurantRepository, never()).existsByCnpj(any());
        verify(restaurantRepository, never()).existsByUserId(any());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando CNPJ é vazio")
    void deveLancarExcecaoQuandoCnpjEVazio() {
        // Arrange
        CreateRestaurantCommand command = new CreateRestaurantCommand(1L, "Restaurante Teste", "");

        // Act & Assert
        RestaurantException exception = assertThrows(RestaurantException.class, 
            () -> restaurantService.createRestaurant(command));
        
        assertEquals("Dados inválidos do restaurante. Campo: cnpj", exception.getMessage());
        verify(restaurantRepository, never()).existsByCnpj(any());
        verify(restaurantRepository, never()).existsByUserId(any());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando CNPJ já existe")
    void deveLancarExcecaoQuandoCnpjJaExiste() {
        // Arrange
        when(restaurantRepository.existsByCnpj(validCommand.getCnpj())).thenReturn(true);

        // Act & Assert
        RestaurantException exception = assertThrows(RestaurantException.class, 
            () -> restaurantService.createRestaurant(validCommand));
        
        assertEquals("CNPJ já cadastrado: " + validCommand.getCnpj(), exception.getMessage());
        verify(restaurantRepository).existsByCnpj(validCommand.getCnpj());
        verify(restaurantRepository, never()).existsByUserId(any());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário já tem restaurante")
    void deveLancarExcecaoQuandoUsuarioJaTemRestaurante() {
        // Arrange
        when(restaurantRepository.existsByCnpj(validCommand.getCnpj())).thenReturn(false);
        when(restaurantRepository.existsByUserId(validCommand.getUserId())).thenReturn(true);

        // Act & Assert
        RestaurantException exception = assertThrows(RestaurantException.class, 
            () -> restaurantService.createRestaurant(validCommand));
        
        assertEquals("Usuário já possui restaurante cadastrado. ID do usuário: " + validCommand.getUserId(), 
            exception.getMessage());
        verify(restaurantRepository).existsByCnpj(validCommand.getCnpj());
        verify(restaurantRepository).existsByUserId(validCommand.getUserId());
        verify(restaurantRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os restaurantes com sucesso")
    void deveListarTodosOsRestaurantes() {
        // Arrange
        List<Restaurant> expectedRestaurants = Arrays.asList(
            new Restaurant(1L, "Restaurante 1", "11222333000181"),
            new Restaurant(2L, "Restaurante 2", "11222333000182")
        );
        when(restaurantRepository.findAll()).thenReturn(expectedRestaurants);

        // Act
        List<Restaurant> result = restaurantService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedRestaurants, result);
        verify(restaurantRepository).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há restaurantes")
    void deveRetornarListaVaziaQuandoNaoHaRestaurantes() {
        // Arrange
        when(restaurantRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Restaurant> result = restaurantService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(restaurantRepository).findAll();
    }

    @Test
    @DisplayName("Deve buscar restaurante por ID com sucesso")
    void deveBuscarRestaurantePorIdComSucesso() {
        // Arrange
        Long restaurantId = 1L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(validRestaurant));

        // Act
        Restaurant result = restaurantService.findById(restaurantId);

        // Assert
        assertNotNull(result);
        assertEquals(validRestaurant.getId(), result.getId());
        assertEquals(validRestaurant.getName(), result.getName());
        assertEquals(validRestaurant.getCnpj(), result.getCnpj());
        verify(restaurantRepository).findById(restaurantId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando restaurante não é encontrado por ID")
    void deveLancarExcecaoQuandoRestauranteNaoEncontradoPorId() {
        // Arrange
        Long restaurantId = 999L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        // Act & Assert
        RestaurantException exception = assertThrows(RestaurantException.class, 
            () -> restaurantService.findById(restaurantId));
        
        assertEquals("Restaurante não encontrado com ID: " + restaurantId, exception.getMessage());
        verify(restaurantRepository).findById(restaurantId);
    }

    @Test
    @DisplayName("Deve validar dados do restaurante com sucesso quando todos os campos são válidos")
    void deveValidarDadosDoRestauranteComSucesso() {
        // Arrange
        CreateRestaurantCommand command = new CreateRestaurantCommand(1L, "Restaurante Válido", "11222333000181");
        when(restaurantRepository.existsByCnpj(command.getCnpj())).thenReturn(false);
        when(restaurantRepository.existsByUserId(command.getUserId())).thenReturn(false);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(validRestaurant);

        // Act
        Restaurant result = restaurantService.createRestaurant(command);

        // Assert
        assertNotNull(result);
        verify(restaurantRepository).existsByCnpj(command.getCnpj());
        verify(restaurantRepository).existsByUserId(command.getUserId());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    @DisplayName("Deve criar restaurante com nome contendo espaços nas extremidades")
    void deveCriarRestauranteComNomeContendoEspacosNasExtremidades() {
        // Arrange
        CreateRestaurantCommand command = new CreateRestaurantCommand(1L, "  Restaurante Teste  ", "11222333000181");
        when(restaurantRepository.existsByCnpj(command.getCnpj())).thenReturn(false);
        when(restaurantRepository.existsByUserId(command.getUserId())).thenReturn(false);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(validRestaurant);

        // Act
        Restaurant result = restaurantService.createRestaurant(command);

        // Assert
        assertNotNull(result);
        verify(restaurantRepository).existsByCnpj(command.getCnpj());
        verify(restaurantRepository).existsByUserId(command.getUserId());
        verify(restaurantRepository).save(any(Restaurant.class));
    }

    @Test
    @DisplayName("Deve criar restaurante com CNPJ contendo espaços nas extremidades")
    void deveCriarRestauranteComCnpjContendoEspacosNasExtremidades() {
        // Arrange
        CreateRestaurantCommand command = new CreateRestaurantCommand(1L, "Restaurante Teste", "  11222333000181  ");
        when(restaurantRepository.existsByCnpj(command.getCnpj())).thenReturn(false);
        when(restaurantRepository.existsByUserId(command.getUserId())).thenReturn(false);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(validRestaurant);

        // Act
        Restaurant result = restaurantService.createRestaurant(command);

        // Assert
        assertNotNull(result);
        verify(restaurantRepository).existsByCnpj(command.getCnpj());
        verify(restaurantRepository).existsByUserId(command.getUserId());
        verify(restaurantRepository).save(any(Restaurant.class));
    }
} 