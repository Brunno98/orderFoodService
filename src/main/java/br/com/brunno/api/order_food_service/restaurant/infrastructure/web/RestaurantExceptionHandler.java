// Arquivo para o RestaurantExceptionHandler - Camada de Infraestrutura Web

package br.com.brunno.api.order_food_service.restaurant.infrastructure.web;

import br.com.brunno.api.order_food_service.restaurant.domain.exceptions.RestaurantAlreadyExistsException;
import br.com.brunno.api.order_food_service.restaurant.domain.exceptions.RestaurantNotFoundException;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler de exceções específico para o módulo Restaurant.
 * Trata apenas exceções relacionadas ao RestaurantController.
 */
@Order(1)
@RestControllerAdvice(assignableTypes = {RestaurantController.class})
public class RestaurantExceptionHandler {
    
    /**
     * Trata exceções de validação Bean Validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestaurantValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        RestaurantValidationErrorResponse errorResponse = new RestaurantValidationErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            errors,
            "Erro de validação"
        );
        
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    /**
     * Trata exceção quando restaurante já existe
     */
    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<RestaurantErrorResponse> handleRestaurantAlreadyExistsException(RestaurantAlreadyExistsException ex) {
        RestaurantErrorResponse errorResponse = new RestaurantErrorResponse(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "Restaurante já existe",
            ex.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    
    /**
     * Trata exceção quando restaurante não é encontrado
     */
    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<RestaurantErrorResponse> handleRestaurantNotFoundException(RestaurantNotFoundException ex) {
        RestaurantErrorResponse errorResponse = new RestaurantErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Restaurante não encontrado",
            ex.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    /**
     * Trata exceções genéricas
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestaurantErrorResponse> handleGenericException(Exception ex) {
        RestaurantErrorResponse errorResponse = new RestaurantErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno do servidor",
            ex.getMessage()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    /**
     * DTO para resposta de erro de validação
     */
    public static class RestaurantValidationErrorResponse {
        private final LocalDateTime timestamp;
        private final int status;
        private final Map<String, String> errors;
        private final String message;
        
        public RestaurantValidationErrorResponse(LocalDateTime timestamp, int status, Map<String, String> errors, String message) {
            this.timestamp = timestamp;
            this.status = status;
            this.errors = errors;
            this.message = message;
        }
        
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        
        public int getStatus() {
            return status;
        }
        
        public Map<String, String> getErrors() {
            return errors;
        }
        
        public String getMessage() {
            return message;
        }
    }
    
    /**
     * DTO para resposta de erro genérico
     */
    public static class RestaurantErrorResponse {
        private final LocalDateTime timestamp;
        private final int status;
        private final String error;
        private final String message;
        
        public RestaurantErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
        }
        
        public LocalDateTime getTimestamp() {
            return timestamp;
        }
        
        public int getStatus() {
            return status;
        }
        
        public String getError() {
            return error;
        }
        
        public String getMessage() {
            return message;
        }
    }
} 