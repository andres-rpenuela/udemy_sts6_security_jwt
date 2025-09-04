package com.codearp.application.products.advoices;

import com.codearp.application.products.advoices.exceptions.ProductNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    // Producto no encontrado o ProductException.class
    @ExceptionHandler( value = {PersistenceException.class})
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(PersistenceException ex) {
        log.error(ex.getMessage(),ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "Error en la persistencia",
                        "message", ex.getMessage(),
                        "status", 500
                ));
    }

    @ExceptionHandler( value = {ProductNotFoundException.class})
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(ProductNotFoundException ex) {
        log.error(ex.getMessage(),ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "error", "Producto no encontrado",
                        "message", ex.getMessage(),
                        "status", 404
                ));
    }

    // Violación de restricciones de BD
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error(ex.getMessage(),ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Error de integridad de datos",
                        "message", "Revisa las claves duplicadas o referencias inválidas",
                        "status", 400
                ));
    }

    // Validaciones de Bean Validation (@NotNull, @Size, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(),ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "error", "Validación fallida",
                        "details", errors,
                        "status", 400
                ));
    }

    // Excepciones genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        log.error(ex.getMessage(),ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of(
                        "error", "Error interno",
                        "message", ex.getMessage(),
                        "status", 500
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParse(HttpMessageNotReadableException ex, Locale locale) {
        log.error(ex.getMessage(),ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
