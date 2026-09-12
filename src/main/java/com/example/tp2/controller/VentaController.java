package com.example.tp2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*Este controlador se va a encargar de recibir la 
lista de ventas, activar el "policía" de 
validación (@Valid), llamar a los métodos de VentaService 
y empaquetar todo dentro de la ApiResponse */

import com.example.tp2.dto.ApiResponse;
import com.example.tp2.dto.EstadisticasDTO;
import com.example.tp2.dto.VentaDTO;
import com.example.tp2.service.VentaService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
@RestController 
@RequestMapping("/api/ventas")/*Define la ruta base para todos los endpoints que estén dentro de esta clase. Significa que cualquier URL aquí escrita arrancará con /api/ventas */
public class VentaController {
    // Declaramos el servicio como final (inmutable y seguro)
    private final VentaService ventaService;

    public VentaController(VentaService ventaService){
        this.ventaService = ventaService;
    }

    // ENDPOINT 1: POST /api/ventas/estadisticas
    @PostMapping("/estadisticas")
    public ResponseEntity<ApiResponse<EstadisticasDTO>> obtenerEstadisticas(@Valid @RequestBody List<VentaDTO> ventas){
        //1. Llamo al servicio para que realize los calculos
        EstadisticasDTO estadisticas = ventaService.calcularEstadistica(ventas);

        //2. Envolvemos el resultado en la respuesta estandar solicitada
        ApiResponse<EstadisticasDTO> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Estadísticas calculadas con éxito",
            estadisticas);
    
        // 3. onstruye y devuelve la respuesta HTTP oficial hacia
        //  el cliente, asegurando que viaje con el código 
        // de estado 200 OK (que significa que todo salió 
        // bien) y metiendo tu objeto response dentro del cuerpo (body) en formato JSON.
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
