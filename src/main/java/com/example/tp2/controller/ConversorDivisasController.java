package com.example.tp2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.tp2.dto.ApiResponse;
import com.example.tp2.dto.RespuestaConvertorDTO;
import com.example.tp2.service.ConversorDivisasService;

@RestController
@RequestMapping("/api/divisas")
public class ConversorDivisasController {

    private final ConversorDivisasService conversorDivisasService;

    public ConversorDivisasController(ConversorDivisasService conversorDivisasService) {
        this.conversorDivisasService = conversorDivisasService;
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
}