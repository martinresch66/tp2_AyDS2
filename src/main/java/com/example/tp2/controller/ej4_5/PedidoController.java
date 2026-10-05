package com.example.tp2.controller.ej4_5;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.tp2.dto.ApiResponse;
import com.example.tp2.dto.ej4y5.PedidoRespuestaDTO;
import com.example.tp2.service.ej4_5.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
        private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    /*GET /api/pedidos/buscar*/
    
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<PedidoRespuestaDTO>>> buscarPedidos(
            @RequestParam(required = false) Integer clienteId,
            @RequestParam(required = false) String categoria,

            /*
             @DateTimeFormat le dice a Spring cómo interpretar el texto
             que llega en la URL (ej: "2026-08-15") y convertirlo en un
             objeto LocalDate real. Sin esto, Spring no sabría qué formato
             de fecha esperar y tiraría error al intentar convertir el String.
             */
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaHasta,

            @RequestParam(required = false) String estado
    ) {

        List<PedidoRespuestaDTO> pedidos = pedidoService.buscarPedidos(
                clienteId, categoria, fechaDesde, fechaHasta, estado
        );

        // Tanto si hay resultados como si la lista viene vacía,
        // siempre devolvemos 200 OK con el mismo formato de respuesta.
        ApiResponse<List<PedidoRespuestaDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Consulta realizada correctamente",
                pedidos
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
