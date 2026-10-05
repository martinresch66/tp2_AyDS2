package com.example.tp2.controller.ej4_5;

import com.example.tp2.domain.ej4y5.Cliente;
import com.example.tp2.dto.ej4y5.ClienteDTO;
import com.example.tp2.dto.ApiResponse;
import com.example.tp2.service.ej4_5.ClienteService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * @RestController combina dos cosas en una sola anotación:
 * - @Controller (le dice a Spring que esta clase maneja peticiones HTTP)
 * - @ResponseBody (le dice que lo que devuelvan los métodos se convierte
 *   automáticamente a JSON)
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    //inyeccion de dependencia
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /*
     Endpoint 1: POST /api/clientes
     Alta simple, sin validaciones (no usa @Valid).
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Cliente>> altaSimple(@RequestBody ClienteDTO clienteDto) {// Captura el JSON del cuerpo de la petición POST y lo convierte automáticamente en un objeto de Java.

        Cliente clienteCreado = clienteService.altaCliente(clienteDto);
        
        ApiResponse<Cliente> response = new ApiResponse<>(
            HttpStatus.CREATED.value(),
            "Cliente registrado con éxito",
            clienteCreado
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /*
     Endpoint 2: POST /api/clientes/validado
     Alta con validación: @Valid activa las anotaciones del ClienteDTO
     (@NotBlank, @Size, @Email, @Pattern), que atrapa el GlobalExceptionHandler
     */
    @PostMapping("/validado")
    public ResponseEntity<ApiResponse<Cliente>> altaConValidacion(@Valid @RequestBody ClienteDTO clienteDto) {

        // Si el email ya existe, altaClienteConValidacion lanza
        // EmailDuplicadoException, que atrapa el GlobalExceptionHandler
        Cliente clienteCreado = clienteService.altaClienteConValidacion(clienteDto);

        ApiResponse<Cliente> response = new ApiResponse<>(
            HttpStatus.CREATED.value(),
            "Cliente validado y registrado con éxito",
            clienteCreado
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}