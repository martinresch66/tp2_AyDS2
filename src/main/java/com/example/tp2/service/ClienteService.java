package com.example.tp2.service;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.tp2.repository.ClienteRepository;
import com.example.tp2.domain.Cliente;
import com.example.tp2.dto.ClienteDTO;
import com.example.tp2.exception.EmailDuplicadoException;

@Service 
public class ClienteService {

    private final ClienteRepository clienteRepository;/*un espacio de memoria que va a existir en cada objeto ClienteService que se cree, para guardar una referencia al ClienteRepository */

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Endpoint 1: POST /api/clientes (alta simple, sin validar duplicados)
    public Cliente altaCliente(ClienteDTO clienteDto) {
        Cliente cliente = convertirAEntidad(clienteDto);
        return clienteRepository.save(cliente);
    }

    // Endpoint 2: POST /api/clientes/validado (valida email duplicado)
    public Cliente altaClienteConValidacion(ClienteDTO clienteDto) {

        /*
         findByEmail devuelve un Optional<Cliente>.
         isPresent() es true si encontró un cliente con ese email
         (o sea, ya está registrado).
         */
        if (clienteRepository.findByEmail(clienteDto.getEmail()).isPresent()) {
            throw new EmailDuplicadoException("El email ya está registrado");
        }

        Cliente cliente = convertirAEntidad(clienteDto);
        return clienteRepository.save(cliente);
    }

    /*
     convierte un ClienteDTO en una entidad Cliente.
     */
    private Cliente convertirAEntidad(ClienteDTO clienteDto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteDto.getNombre());
        cliente.setApellido(clienteDto.getApellido());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setFechaRegistro(LocalDateTime.now());
        return cliente;
    }
    
}
