package com.example.tp2.controller;

import com.example.tp2.domain.Cliente;
import com.example.tp2.dto.ClienteDTO;
import com.example.tp2.service.ClienteService;
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

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /*
     Endpoint 1: POST /api/clientes
     Alta simple, sin validaciones (no usa @Valid).
     */
    @PostMapping
    public ResponseEntity<Cliente> altaSimple(@RequestBody ClienteDTO clienteDto) {

        Cliente clienteCreado = clienteService.altaCliente(clienteDto);

        // HttpStatus.CREATED = 201
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
    }

    /*
     Endpoint 2: POST /api/clientes/validado
     Alta con validación: @Valid activa las anotaciones del ClienteDTO
     (@NotBlank, @Size, @Email, @Pattern), que atrapa el GlobalExceptionHandler
     */
    @PostMapping("/validado")
    public ResponseEntity<Cliente> altaConValidacion(@Valid @RequestBody ClienteDTO clienteDto) {

        // Si el email ya existe, altaClienteConValidacion lanza
        // EmailDuplicadoException, que atrapa el GlobalExceptionHandler
        Cliente clienteCreado = clienteService.altaClienteConValidacion(clienteDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCreado);
    }

}