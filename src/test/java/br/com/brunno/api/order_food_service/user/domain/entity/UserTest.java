package br.com.brunno.api.order_food_service.user.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User - Entidade de Domínio")
class UserTest {

    private static final String NOME_VALIDO = "João Silva";
    private static final String EMAIL_VALIDO = "joao@email.com";
    private static final User.UserType TIPO_VALIDO = User.UserType.CLIENTE;

    @Nested
    @DisplayName("Construtores")
    class ConstrutoresTest {

        @Test
        @DisplayName("Deve criar usuário com construtor de novo usuário")
        void deveCriarUsuarioComConstrutorDeNovoUsuario() {
            // When
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // Then
            assertNotNull(user.getId());
            assertEquals(NOME_VALIDO, user.getNome());
            assertEquals(EMAIL_VALIDO, user.getEmail());
            assertEquals(TIPO_VALIDO, user.getTipo());
        }

        @Test
        @DisplayName("Deve criar usuário com construtor de reconstrução")
        void deveCriarUsuarioComConstrutorDeReconstrucao() {
            // Given
            UUID id = UUID.randomUUID();

            // When
            User user = new User(id, NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // Then
            assertEquals(id, user.getId());
            assertEquals(NOME_VALIDO, user.getNome());
            assertEquals(EMAIL_VALIDO, user.getEmail());
            assertEquals(TIPO_VALIDO, user.getTipo());
        }

        @Test
        @DisplayName("Deve gerar IDs diferentes para usuários diferentes")
        void deveGerarIdsDiferentesParaUsuariosDiferentes() {
            // When
            User user1 = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);
            User user2 = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // Then
            assertNotEquals(user1.getId(), user2.getId());
        }
    }

    @Nested
    @DisplayName("Validações de Nome")
    class ValidacoesNomeTest {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "  ", "\t", "\n"})
        @DisplayName("Deve lançar exceção quando nome for inválido")
        void deveLancarExcecaoQuandoNomeForInvalido(String nomeInvalido) {
            // Then
            assertThrows(IllegalArgumentException.class, () -> {
                new User(nomeInvalido, EMAIL_VALIDO, TIPO_VALIDO);
            }, "Nome não pode ser vazio");
        }

        @Test
        @DisplayName("Deve aceitar nome válido")
        void deveAceitarNomeValido() {
            // When
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // Then
            assertEquals(NOME_VALIDO, user.getNome());
        }
    }

    @Nested
    @DisplayName("Validações de Email")
    class ValidacoesEmailTest {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "  ", "\t", "\n"})
        @DisplayName("Deve lançar exceção quando email for vazio")
        void deveLancarExcecaoQuandoEmailForVazio(String emailVazio) {
            // Then
            assertThrows(IllegalArgumentException.class, () -> {
                new User(NOME_VALIDO, emailVazio, TIPO_VALIDO);
            }, "Email não pode ser vazio");
        }

        @ParameterizedTest
        @ValueSource(strings = {"emailinvalido", "email@", "@email.com", "email.com"})
        @DisplayName("Deve lançar exceção quando email tiver formato inválido")
        void deveLancarExcecaoQuandoEmailTiverFormatoInvalido(String emailInvalido) {
            // Then
            assertThrows(IllegalArgumentException.class, () -> {
                new User(NOME_VALIDO, emailInvalido, TIPO_VALIDO);
            }, "Email deve ter formato válido");
        }

        @ParameterizedTest
        @ValueSource(strings = {"teste@email.com", "user@domain.org", "admin@company.co.uk"})
        @DisplayName("Deve aceitar emails válidos")
        void deveAceitarEmailsValidos(String emailValido) {
            // When
            User user = new User(NOME_VALIDO, emailValido, TIPO_VALIDO);

            // Then
            assertEquals(emailValido, user.getEmail());
        }
    }

    @Nested
    @DisplayName("Validações de Tipo")
    class ValidacoesTipoTest {

        @Test
        @DisplayName("Deve lançar exceção quando tipo for nulo")
        void deveLancarExcecaoQuandoTipoForNulo() {
            // Then
            assertThrows(IllegalArgumentException.class, () -> {
                new User(NOME_VALIDO, EMAIL_VALIDO, null);
            }, "Tipo de usuário não pode ser nulo");
        }

        @Test
        @DisplayName("Deve aceitar tipo CLIENTE")
        void deveAceitarTipoCliente() {
            // When
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, User.UserType.CLIENTE);

            // Then
            assertEquals(User.UserType.CLIENTE, user.getTipo());
            assertTrue(user.isCliente());
            assertFalse(user.isRestaurante());
        }

        @Test
        @DisplayName("Deve aceitar tipo RESTAURANTE")
        void deveAceitarTipoRestaurante() {
            // When
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, User.UserType.RESTAURANTE);

            // Then
            assertEquals(User.UserType.RESTAURANTE, user.getTipo());
            assertTrue(user.isRestaurante());
            assertFalse(user.isCliente());
        }
    }

    @Nested
    @DisplayName("Método Update")
    class MetodoUpdateTest {

        @Test
        @DisplayName("Deve atualizar nome e email válidos")
        void deveAtualizarNomeEEmailValidos() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);
            String novoNome = "Maria Santos";
            String novoEmail = "maria@email.com";

            // When
            user.update(novoNome, novoEmail);

            // Then
            assertEquals(novoNome, user.getNome());
            assertEquals(novoEmail, user.getEmail());
        }

        @Test
        @DisplayName("Deve manter nome atual quando novo nome for nulo")
        void deveManterNomeAtualQuandoNovoNomeForNulo() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // When
            user.update(null, EMAIL_VALIDO);

            // Then
            assertEquals(NOME_VALIDO, user.getNome());
        }

        @Test
        @DisplayName("Deve manter email atual quando novo email for nulo")
        void deveManterEmailAtualQuandoNovoEmailForNulo() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // When
            user.update(NOME_VALIDO, null);

            // Then
            assertEquals(EMAIL_VALIDO, user.getEmail());
        }

        @Test
        @DisplayName("Deve lançar exceção quando novo email for inválido")
        void deveLancarExcecaoQuandoNovoEmailForInvalido() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // Then
            assertThrows(IllegalArgumentException.class, () -> {
                user.update(NOME_VALIDO, "emailinvalido");
            }, "Email deve ter formato válido");
        }
    }

    @Nested
    @DisplayName("Métodos de Tipo")
    class MetodosDeTipoTest {

        @Test
        @DisplayName("isCliente deve retornar true para usuário CLIENTE")
        void isClienteDeveRetornarTrueParaUsuarioCliente() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, User.UserType.CLIENTE);

            // When & Then
            assertTrue(user.isCliente());
        }

        @Test
        @DisplayName("isCliente deve retornar false para usuário RESTAURANTE")
        void isClienteDeveRetornarFalseParaUsuarioRestaurante() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, User.UserType.RESTAURANTE);

            // When & Then
            assertFalse(user.isCliente());
        }

        @Test
        @DisplayName("isRestaurante deve retornar true para usuário RESTAURANTE")
        void isRestauranteDeveRetornarTrueParaUsuarioRestaurante() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, User.UserType.RESTAURANTE);

            // When & Then
            assertTrue(user.isRestaurante());
        }

        @Test
        @DisplayName("isRestaurante deve retornar false para usuário CLIENTE")
        void isRestauranteDeveRetornarFalseParaUsuarioCliente() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, User.UserType.CLIENTE);

            // When & Then
            assertFalse(user.isRestaurante());
        }
    }

    @Nested
    @DisplayName("Equals e HashCode")
    class EqualsHashCodeTest {

        @Test
        @DisplayName("Deve ser igual quando IDs forem iguais")
        void deveSerIgualQuandoIdsForemIguais() {
            // Given
            UUID id = UUID.randomUUID();
            User user1 = new User(id, NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);
            User user2 = new User(id, "Nome Diferente", "email@diferente.com", User.UserType.RESTAURANTE);

            // When & Then
            assertEquals(user1, user2);
            assertEquals(user1.hashCode(), user2.hashCode());
        }

        @Test
        @DisplayName("Deve ser diferente quando IDs forem diferentes")
        void deveSerDiferenteQuandoIdsForemDiferentes() {
            // Given
            User user1 = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);
            User user2 = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // When & Then
            assertNotEquals(user1, user2);
            assertNotEquals(user1.hashCode(), user2.hashCode());
        }

        @Test
        @DisplayName("Deve ser diferente de null")
        void deveSerDiferenteDeNull() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // When & Then
            assertNotEquals(null, user);
        }

        @Test
        @DisplayName("Deve ser diferente de objeto de outro tipo")
        void deveSerDiferenteDeObjetoDeOutroTipo() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);
            String string = "não é um User";

            // When & Then
            assertNotEquals(user, string);
        }
    }

    @Nested
    @DisplayName("ToString")
    class ToStringTest {

        @Test
        @DisplayName("Deve retornar string com informações do usuário")
        void deveRetornarStringComInformacoesDoUsuario() {
            // Given
            User user = new User(NOME_VALIDO, EMAIL_VALIDO, TIPO_VALIDO);

            // When
            String result = user.toString();

            // Then
            assertTrue(result.contains("id="));
            assertTrue(result.contains("nome='" + NOME_VALIDO + "'"));
            assertTrue(result.contains("email='" + EMAIL_VALIDO + "'"));
            assertTrue(result.contains("tipo=" + TIPO_VALIDO));
        }
    }

    @Nested
    @DisplayName("Enum UserType")
    class EnumUserTypeTest {

        @Test
        @DisplayName("Deve ter valores CLIENTE e RESTAURANTE")
        void deveTerValoresClienteERestaurante() {
            // When
            User.UserType[] tipos = User.UserType.values();

            // Then
            assertEquals(2, tipos.length);
            assertTrue(contains(tipos, User.UserType.CLIENTE));
            assertTrue(contains(tipos, User.UserType.RESTAURANTE));
        }

        private boolean contains(User.UserType[] tipos, User.UserType tipo) {
            for (User.UserType t : tipos) {
                if (t == tipo) {
                    return true;
                }
            }
            return false;
        }
    }
} 