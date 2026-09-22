package com.example.tp2.controller;




import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import com.example.tp2.dto.ApiResponse;
import com.example.tp2.dto.HistorialItemDTO;
import com.example.tp2.dto.RespuestaConvertorDTO;
import com.example.tp2.service.*;

@RestController
@RequestMapping("/api/divisas")
public class ConversorDivisasController {

     private final ConversorDivisasService conversorDivisasService;
    private final HistorialConversionService historialConversionService;

    public ConversorDivisasController(ConversorDivisasService conversorDivisasService,
                                       HistorialConversionService historialConversionService) { 
        this.conversorDivisasService = conversorDivisasService;
        this.historialConversionService = historialConversionService; 
    }

    // ENDPOINT: GET /api/divisas/convertir?monto=100&origen=USD&destino=ARS
    @GetMapping("/convertir")
    public ResponseEntity<ApiResponse<RespuestaConvertorDTO>> convertir(
            @RequestParam Double monto,
            @RequestParam String origen,
            @RequestParam String destino) {

        // Validación 
        if (monto == null || monto <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El monto debe ser mayor que 0");
        }

        if (origen == null || !origen.matches("[A-Za-z]{3}")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El código de moneda de origen debe tener 3 letras (ej: USD)");
        }

        if (destino == null || !destino.matches("[A-Za-z]{3}")) {//Exactamente 3 caracteres seguidos, cada uno siendo una letra (mayúscula o minúscula)
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El código de moneda de destino debe tener 3 letras (ej: USD)");
        }

        RespuestaConvertorDTO resultado = conversorDivisasService.convertir(monto, origen.toUpperCase(), destino.toUpperCase());

        ApiResponse<RespuestaConvertorDTO> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Conversión realizada con éxito",
            resultado
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ============================================================
    // ENDPOINT 1 (Ejercicio 6): POST /api/divisas/consultar
    // Consulta Frankfurter Y guarda el resultado en el historial
    // ============================================================
    @PostMapping("/consultar")
    public ResponseEntity<ApiResponse<RespuestaConvertorDTO>> consultarYGuardar(
            @RequestParam Double monto,
            @RequestParam String origen,
            @RequestParam String destino) {

        if (monto == null || monto <= 0) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El monto debe ser mayor que 0");
        }
        if (origen == null || !origen.matches("[A-Za-z]{3}")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El código de moneda de origen debe tener 3 letras (ej: USD)");
        }
        if (destino == null || !destino.matches("[A-Za-z]{3}")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El código de moneda de destino debe tener 3 letras (ej: USD)");
        }

        RespuestaConvertorDTO resultado = historialConversionService.consultarYGuardar(
                monto, origen.toUpperCase(), destino.toUpperCase()
        );

        ApiResponse<RespuestaConvertorDTO> response = new ApiResponse<>(
            HttpStatus.OK.value(), "Consulta realizada y guardada en el historial", resultado
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ============================================================
    // ENDPOINT 2 (Ejercicio 6): GET /api/divisas/historial
    // ============================================================
    @GetMapping("/historial")
    public ResponseEntity<ApiResponse<List<HistorialItemDTO>>> historial(
            @RequestParam String origen,
            @RequestParam String destino) {

        if (origen == null || !origen.matches("[A-Za-z]{3}")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El código de moneda de origen debe tener 3 letras (ej: USD)");
        }
        if (destino == null || !destino.matches("[A-Za-z]{3}")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "El código de moneda de destino debe tener 3 letras (ej: USD)");
        }

        List<HistorialItemDTO> resultado = historialConversionService.obtenerHistorial(
                origen.toUpperCase(), destino.toUpperCase()
        );

        ApiResponse<List<HistorialItemDTO>> response = new ApiResponse<>(
            HttpStatus.OK.value(), "Historial recuperado correctamente", resultado
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}