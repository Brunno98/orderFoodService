package br.com.brunno.api.order_food_service.integration;

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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes integrados para o UserController.
 * Utiliza MockMvc para testar os endpoints HTTP com banco H2 em memória.
 */
@SpringBootTest
@ActiveProfiles("integration-test")
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

    @Test
    @DisplayName("Deve excluir usuário com sucesso")
    void deveExcluirUsuarioComSucesso() throws Exception {
        // Given
        UserJpaEntity userParaExcluir = userJpaRepository.save(new UserJpaEntity(
            UUID.randomUUID(),
            "Usuário para Excluir",
            "usuario.excluir@email.com",
            User.UserType.CLIENTE
        ));

        // Verificar que o usuário existe antes da exclusão
        assertTrue(userJpaRepository.findByDomainId(userParaExcluir.getDomainId()).isPresent());

        // When & Then
        mockMvc.perform(delete("/api/users/{id}", userParaExcluir.getDomainId()))
                .andExpect(status().isNoContent());

        // Verificar que o usuário foi realmente excluído
        assertFalse(userJpaRepository.findByDomainId(userParaExcluir.getDomainId()).isPresent());
    }

    @Test
    @DisplayName("Deve retornar 404 quando tentar excluir usuário inexistente")
    void deveRetornar404QuandoTentarExcluirUsuarioInexistente() throws Exception {
        // Given - Usando um UUID constante que com certeza não existe
        UUID idInexistente = UUID.fromString("00000000-0000-0000-0000-000000000000");

        // When & Then
        mockMvc.perform(delete("/api/users/{id}", idInexistente))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(containsString("Usuário não encontrado")));
    }

    @Test
    @DisplayName("Deve excluir usuário e manter outros usuários intactos")
    void deveExcluirUsuarioEManterOutrosIntactos() throws Exception {
        // Given
        UserJpaEntity user1 = userJpaRepository.save(new UserJpaEntity(
            UUID.randomUUID(),
            "Usuário 1",
            "usuario1@email.com",
            User.UserType.CLIENTE
        ));

        UserJpaEntity user2 = userJpaRepository.save(new UserJpaEntity(
            UUID.randomUUID(),
            "Usuário 2",
            "usuario2@email.com",
            User.UserType.RESTAURANTE
        ));

        UserJpaEntity user3 = userJpaRepository.save(new UserJpaEntity(
            UUID.randomUUID(),
            "Usuário 3",
            "usuario3@email.com",
            User.UserType.CLIENTE
        ));

        // Verificar que todos os usuários existem
        assertEquals(3, userJpaRepository.count());

        // When - Excluir apenas o user2
        mockMvc.perform(delete("/api/users/{id}", user2.getDomainId()))
                .andExpect(status().isNoContent());

        // Then - Verificar que apenas o user2 foi excluído
        assertFalse(userJpaRepository.findByDomainId(user2.getDomainId()).isPresent());
        assertTrue(userJpaRepository.findByDomainId(user1.getDomainId()).isPresent());
        assertTrue(userJpaRepository.findByDomainId(user3.getDomainId()).isPresent());
        assertEquals(2, userJpaRepository.count());
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

    static Stream<Arguments> dadosUsuariosParaExcluir() {
        return Stream.of(
            Arguments.of("Cliente para Excluir", "cliente.excluir@email.com", User.UserType.CLIENTE),
            Arguments.of("Restaurante para Excluir", "restaurante.excluir@email.com", User.UserType.RESTAURANTE),
            Arguments.of("Usuário Teste", "usuario.teste@email.com", User.UserType.CLIENTE)
        );
    }
} 