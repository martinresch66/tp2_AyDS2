package com.example.tp2.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.tp2.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;


/*Le avisa a Spring Boot que esta clase va a estar escuchando globalmente todo lo que pase en los controladores.
Gracias a esto, no tenemos que escribir código de manejo de errores en cada endpoint. */
@RestControllerAdvice 
public class GlobalExceptionHandler {

    // =========================================================================
    // MÉTODO 1: Atrapa los errores cuando fallan las validaciones de los DTOs (@Valid)
    // Por ejemplo: si el usuario manda un precio en negativo o un nombre vacío.
    // =========================================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        
        // Creamos un diccionario (Map) de Java. Aquí guardaremos en qué campo 
        // ocurrió el error y qué mensaje explicativo tiene.
        Map<String, String> errores = new HashMap<>();
        
        // Guardamos en una lista todos los errores que detectó Spring al validar los datos.
        List<ObjectError> listaDeErrores = ex.getBindingResult().getAllErrors();
        
        // Sirve para recorrer la lista de errores 
        for (int i = 0; i < listaDeErrores.size(); i++) {
            
            // Obtenemos el error actual
            ObjectError errorActual = listaDeErrores.get(i);
            
            // Como ObjectError es muy general, hacemos un "cast" a 
            // FieldError para poder preguntarle qué atributo exacto falló.
            FieldError campoError = (FieldError) errorActual;
            
            // Extraemos el nombre del campo con problemas (ejemplo: "precio" o "producto").
            String campo = campoError.getField();
            
            // Extraemos el mensaje de texto que escribimos en el DTO (ejemplo: "El precio debe ser mayor a 0").
            String mensaje = errorActual.getDefaultMessage();
            
            // Guardamos el campo y su mensaje dentro de nuestro diccionario de errores.
            errores.put(campo, mensaje);
        }

        // Empaquetamos el diccionario con todos los errores dentro de la clase estándar ApiResponse.
        ApiResponse<Map<String, String>> response = new ApiResponse<>(
            HttpStatus.BAD_REQUEST.value(), // El código de error numérico (400)
            "Error de validación en los datos", // Mensaje descriptivo para el usuario
            errores // Los datos reales (el mapa con los detalles de qué falló)
        );
        
        // Devolvemos la respuesta HTTP oficial indicando que hubo un error 400 (Bad Request).
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // =========================================================================
    // MÉTODO 2: Atrapa excepciones HTTP controladas
    // Por ejemplo: cuando buscas un producto por ID y no existe, lanzando un 404.
    // =========================================================================
   //cualquier excepción de tipo ResponseStatusException que se lance en cualquier controller, atrapala aca
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Object>> handleResponseStatusException(ResponseStatusException ex) {
        
        // Creamos la respuesta estándar poniendo la "data" en null.
        ApiResponse<Object> response = new ApiResponse<>(
            ex.getStatusCode().value(), // Extrae el número de estado de la excepción (ej: 404 o 400)
            ex.getReason() != null ? ex.getReason() : "Ocurrió un error en la solicitud", // El texto del motivo del error
            null // En los errores no hay datos que devolver, por lo tanto va null
        );
        
        // Devolvemos la respuesta con el código exacto que traía la excepción.
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    // =========================================================================
    // MÉTODO 3: Red de seguridad para cualquier otro error imprevisto
    // Si ocurre un fallo inesperado en el servidor, esto evita que la app se rompa.
    // =========================================================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception ex) {
        
        // Armamos un ApiResponse genérico informando que ocurrió un problema interno grave.
        ApiResponse<Object> response = new ApiResponse<>(
            HttpStatus.INTERNAL_SERVER_ERROR.value(), // Código numérico 500
            "Error interno del servidor: " + ex.getMessage(), // Descripción técnica del problema
            null // Data en null
        );
        
        // Devolvemos la respuesta asegurando el formato estándar con un código 500.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // =========================================================================
    // MÉTODO 4: excepcion para email duplicado en ejercicio 4
    // =========================================================================

@ExceptionHandler(EmailDuplicadoException.class)
public ResponseEntity<ApiResponse<Object>> handleEmailDuplicado(EmailDuplicadoException ex) {

    // Armamos la respuesta estándar: 400, el mensaje de la excepción, y data en null
    ApiResponse<Object> response = new ApiResponse<>(
        HttpStatus.BAD_REQUEST.value(), // 400
        ex.getMessage(),                // "El email ya está registrado" (viene del throw en el Service)
        null                             // no hay datos que devolver en un error
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
}

    
}
