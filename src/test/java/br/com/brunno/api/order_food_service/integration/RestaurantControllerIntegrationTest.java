package br.com.brunno.api.order_food_service.integration;

import br.com.brunno.api.order_food_service.restaurant.dto.RestaurantCreateRequest;
import br.com.brunno.api.order_food_service.restaurant.entity.Restaurant;
import br.com.brunno.api.order_food_service.restaurant.repository.RestaurantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de integração para o RestaurantController
 * Testa desde a camada de controller até a persistência de dados
 */
@SpringBootTest
@ActiveProfiles("integration-test")
@Transactional
class RestaurantControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        restaurantRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar restaurante com sucesso via API REST")
    void deveCriarRestauranteComSucesso() throws Exception {
        // Arrange
        RestaurantCreateRequest request = new RestaurantCreateRequest();
        request.setUserId(1L);
        request.setName("Restaurante Teste");
        request.setCnpj("11222333000181");

        // Act & Assert
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.name").value("Restaurante Teste"))
                .andExpect(jsonPath("$.cnpj").value("11222333000181"))
                .andExpect(jsonPath("$.isActive").value(true))
                .andExpect(jsonPath("$.createdAt").exists());

        // Verificar se foi salvo no banco
        assertTrue(restaurantRepository.existsByCnpj("11222333000181"));
        assertTrue(restaurantRepository.existsByUserId(1L));
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando dados obrigatórios estão faltando")
    void deveRetornarErro400QuandoDadosObrigatoriosFaltam() throws Exception {
        // Arrange
        RestaurantCreateRequest request = new RestaurantCreateRequest();
        request.setUserId(1L);
        // name e cnpj não definidos

        // Act & Assert
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    @DisplayName("Deve retornar erro 400 quando CNPJ é inválido")
    void deveRetornarErro400QuandoCnpjEInvalido() throws Exception {
        // Arrange
        RestaurantCreateRequest request = new RestaurantCreateRequest();
        request.setUserId(1L);
        request.setName("Restaurante Teste");
        request.setCnpj("123"); // CNPJ inválido

        // Act & Assert
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    @DisplayName("Deve retornar erro 409 quando CNPJ já existe")
    void deveRetornarErro409QuandoCnpjJaExiste() throws Exception {
        // Arrange - Criar primeiro restaurante
        RestaurantCreateRequest request1 = new RestaurantCreateRequest();
        request1.setUserId(1L);
        request1.setName("Restaurante 1");
        request1.setCnpj("11222333000181");

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated());

        // Tentar criar segundo restaurante com mesmo CNPJ
        RestaurantCreateRequest request2 = new RestaurantCreateRequest();
        request2.setUserId(2L);
        request2.setName("Restaurante 2");
        request2.setCnpj("11222333000181"); // Mesmo CNPJ

        // Act & Assert
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString("CNPJ já cadastrado")));
    }

    @Test
    @DisplayName("Deve retornar erro 409 quando usuário já tem restaurante")
    void deveRetornarErro409QuandoUsuarioJaTemRestaurante() throws Exception {
        // Arrange - Criar primeiro restaurante
        RestaurantCreateRequest request1 = new RestaurantCreateRequest();
        request1.setUserId(1L);
        request1.setName("Restaurante 1");
        request1.setCnpj("11222333000181");

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        // Tentar criar segundo restaurante com mesmo usuário
        RestaurantCreateRequest request2 = new RestaurantCreateRequest();
        request2.setUserId(1L); // Mesmo usuário
        request2.setName("Restaurante 2");
        request2.setCnpj("96702689000175");

        // Act & Assert
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString("Usuário já possui restaurante")));
    }

    @Test
    @DisplayName("Deve listar todos os restaurantes com sucesso")
    void deveListarTodosOsRestaurantes() throws Exception {
        // Arrange - Criar alguns restaurantes
        RestaurantCreateRequest request1 = new RestaurantCreateRequest();
        request1.setUserId(1L);
        request1.setName("Restaurante 1");
        request1.setCnpj("11222333000181");

        RestaurantCreateRequest request2 = new RestaurantCreateRequest();
        request2.setUserId(2L);
        request2.setName("Restaurante 2");
        request2.setCnpj("96702689000175");

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request2)))
                .andExpect(status().isCreated());

        // Act & Assert
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Restaurante 1"))
                .andExpect(jsonPath("$[0].cnpj").value("11222333000181"))
                .andExpect(jsonPath("$[1].name").value("Restaurante 2"))
                .andExpect(jsonPath("$[1].cnpj").value("96702689000175"));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há restaurantes")
    void deveRetornarListaVaziaQuandoNaoHaRestaurantes() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Deve buscar restaurante por ID com sucesso")
    void deveBuscarRestaurantePorIdComSucesso() throws Exception {
        // Arrange - Criar restaurante
        RestaurantCreateRequest request = new RestaurantCreateRequest();
        request.setUserId(1L);
        request.setName("Restaurante Teste");
        request.setCnpj("11222333000181");

        String response = mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extrair ID da resposta
        String id = objectMapper.readTree(response).get("id").asText();

        // Act & Assert - Buscar por ID
        mockMvc.perform(get("/api/restaurants/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Restaurante Teste"))
                .andExpect(jsonPath("$.cnpj").value("11222333000181"))
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    @DisplayName("Deve retornar erro 404 quando restaurante não encontrado por ID")
    void deveRetornarErro404QuandoRestauranteNaoEncontrado() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/restaurants/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("Restaurante não encontrado")));
    }

    @Test
    @DisplayName("Deve validar tamanho mínimo do nome")
    void deveValidarTamanhoMinimoDoNome() throws Exception {
        // Arrange
        RestaurantCreateRequest request = new RestaurantCreateRequest();
        request.setUserId(1L);
        request.setName("A"); // Nome muito curto
        request.setCnpj("11222333000181");

        // Act & Assert
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    @DisplayName("Deve persistir dados corretamente no banco de dados")
    void devePersistirDadosCorretamenteNoBanco() throws Exception {
        // Arrange
        RestaurantCreateRequest request = new RestaurantCreateRequest();
        request.setUserId(1L);
        request.setName("Restaurante Persistência");
        request.setCnpj("11222333000181");

        // Act
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Assert - Verificar no banco de dados
        Restaurant savedRestaurant = restaurantRepository.findByCnpj("11222333000181").orElse(null);
        assertNotNull(savedRestaurant);
        assertEquals("Restaurante Persistência", savedRestaurant.getName());
        assertEquals(1L, savedRestaurant.getUserId());
        assertEquals("11222333000181", savedRestaurant.getCnpj());
        assertTrue(savedRestaurant.getIsActive());
        assertNotNull(savedRestaurant.getCreatedAt());
    }
} 