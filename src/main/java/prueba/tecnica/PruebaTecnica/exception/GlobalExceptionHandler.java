package prueba.tecnica.PruebaTecnica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<Map<String, String>> manejarSaldoInsuficiente(SaldoInsuficienteException ex) {
        System.out.println("Se capturó la excepción SaldoInsuficienteException en el manejador global."); // Debug

        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class) // Capturar cualquier otro error
    public ResponseEntity<Map<String, String>> manejarErroresGenerales(Exception ex) {
        System.out.println("Se capturó una excepción general en el manejador global: " + ex.getMessage()); // Debug

        Map<String, String> response = new HashMap<>();
        response.put("error", "Error interno: " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
