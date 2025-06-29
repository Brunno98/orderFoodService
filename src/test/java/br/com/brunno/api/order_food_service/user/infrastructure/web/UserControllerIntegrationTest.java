package br.com.brunno.api.order_food_service.user.infrastructure.web;

import br.com.brunno.api.order_food_service.user.domain.entity.User;
import br.com.brunno.api.order_food_service.user.infrastructure.persistence.UserJpaRepository;
import br.com.brunno.api.order_food_service.user.infrastructure.persistence.entity.UserJpaEntity;
import br.com.brunno.api.order_food_service.user.infrastructure.web.dto.CreateUserWebRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes integrados para o UserController.
 * Utiliza MockMvc para testar os endpoints HTTP com banco H2 em memória.
 */
@SpringBootTest
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void deveCriarUsuarioComSucesso() throws Exception {
        // Given
        CreateUserWebRequest request = new CreateUserWebRequest(
            "João Silva",
            "joao.silva@email.com",
            User.UserType.CLIENTE
        );

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao.silva@email.com"))
                .andExpect(jsonPath("$.tipo").value("CLIENTE"));
    }

    @ParameterizedTest
    @MethodSource("dadosUsuariosValidos")
    @DisplayName("Deve criar usuários com diferentes tipos e dados válidos")
    void deveCriarUsuariosComDadosValidos(String nome, String email, User.UserType tipo) throws Exception {
        // Given
        CreateUserWebRequest request = new CreateUserWebRequest(nome, email, tipo);

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(nome))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.tipo").value(tipo.toString()));
    }

    @ParameterizedTest
    @MethodSource("dadosUsuariosInvalidos")
    @DisplayName("Deve retornar erro de validação para dados inválidos")
    void deveRetornarErroParaDadosInvalidos(String nome, String email, User.UserType tipo, String campoErro) throws Exception {
        // Given
        CreateUserWebRequest request = new CreateUserWebRequest(nome, email, tipo);

        var requestString = objectMapper.writeValueAsString(request);
        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors." + campoErro).exists());
    }

    @Test
    @DisplayName("Deve buscar usuário por ID com sucesso")
    void deveBuscarUsuarioPorIdComSucesso() throws Exception {
        // Given
        UserJpaEntity userEntity = new UserJpaEntity(
            UUID.randomUUID(),
            "Maria Santos",
            "maria.santos@email.com",
            User.UserType.RESTAURANTE
        );
        UserJpaEntity savedUser = userJpaRepository.save(userEntity);

        // When & Then
        mockMvc.perform(get("/api/users/{id}", savedUser.getDomainId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedUser.getDomainId().toString()))
                .andExpect(jsonPath("$.nome").value("Maria Santos"))
                .andExpect(jsonPath("$.email").value("maria.santos@email.com"))
                .andExpect(jsonPath("$.tipo").value("RESTAURANTE"));
    }

    @Test
    @DisplayName("Deve retornar 404 quando usuário não encontrado")
    void deveRetornar404QuandoUsuarioNaoEncontrado() throws Exception {
        // Given
        UUID idInexistente = UUID.randomUUID();

        // When & Then
        mockMvc.perform(get("/api/users/{id}", idInexistente))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("Usuário não encontrado")));
    }

    @Test
    @DisplayName("Deve listar todos os usuários com sucesso")
    void deveListarTodosUsuariosComSucesso() throws Exception {
        // Given
        UserJpaEntity user1 = userJpaRepository.save(new UserJpaEntity(
            UUID.randomUUID(),
            "Pedro Costa",
            "pedro.costa@email.com",
            User.UserType.CLIENTE
        ));
        
        UserJpaEntity user2 = userJpaRepository.save(new UserJpaEntity(
            UUID.randomUUID(),
            "Restaurante ABC",
            "restaurante.abc@email.com",
            User.UserType.RESTAURANTE
        ));

        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(user1.getDomainId().toString()))
                .andExpect(jsonPath("$[0].nome").value("Pedro Costa"))
                .andExpect(jsonPath("$[0].email").value("pedro.costa@email.com"))
                .andExpect(jsonPath("$[0].tipo").value("CLIENTE"))
                .andExpect(jsonPath("$[1].id").value(user2.getDomainId().toString()))
                .andExpect(jsonPath("$[1].nome").value("Restaurante ABC"))
                .andExpect(jsonPath("$[1].email").value("restaurante.abc@email.com"))
                .andExpect(jsonPath("$[1].tipo").value("RESTAURANTE"));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há usuários")
    void deveRetornarListaVaziaQuandoNaoHaUsuarios() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Deve retornar erro quando email já existe")
    void deveRetornarErroQuandoEmailJaExiste() throws Exception {
        // Given
        UserJpaEntity userExistente = userJpaRepository.save(new UserJpaEntity(
            UUID.randomUUID(),
            "Usuário Existente",
            "email.existente@email.com",
            User.UserType.CLIENTE
        ));

        CreateUserWebRequest request = new CreateUserWebRequest(
            "Novo Usuário",
            "email.existente@email.com", // Mesmo email
            User.UserType.RESTAURANTE
        );

        // When & Then
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString("Já existe um usuário com o email")));
    }

    // Métodos auxiliares para testes parametrizados

    static Stream<Arguments> dadosUsuariosValidos() {
        return Stream.of(
            Arguments.of("João Silva", "joao.silva@email.com", User.UserType.CLIENTE),
            Arguments.of("Restaurante XYZ", "restaurante.xyz@email.com", User.UserType.RESTAURANTE),
            Arguments.of("Ana Oliveira", "ana.oliveira@email.com", User.UserType.CLIENTE),
            Arguments.of("Pizzaria Bella", "pizzaria.bella@email.com", User.UserType.RESTAURANTE)
        );
    }

    static Stream<Arguments> dadosUsuariosInvalidos() {
        return Stream.of(
            Arguments.of("", "email@teste.com", User.UserType.CLIENTE, "nome"),
            Arguments.of(null, "email@teste.com", User.UserType.CLIENTE, "nome"),
            Arguments.of("Nome Válido", "", User.UserType.CLIENTE, "email"),
            Arguments.of("Nome Válido", null, User.UserType.CLIENTE, "email"),
            Arguments.of("Nome Válido", "email-invalido", User.UserType.CLIENTE, "email"),
            Arguments.of("Nome Válido", "email@teste.com", null, "tipo")
        );
    }
} 