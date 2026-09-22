package com.example.tp2.service;
import com.example.tp2.dto.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.example.tp2.repository.HistorialConversionRepository;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.tp2.domain.*;


@Service
public class HistorialConversionService {

    private final ConversorDivisasService conversorDivisasService;
    private final HistorialConversionRepository historialConversionRepository;

    public HistorialConversionService(ConversorDivisasService conversorDivisasService,
                                       HistorialConversionRepository historialConversionRepository) {
        this.conversorDivisasService = conversorDivisasService;
        this.historialConversionRepository = historialConversionRepository;
    }

    // ENDPOINT 1: POST /api/divisas/consultar
    public RespuestaConvertorDTO consultarYGuardar(double monto, String origen, String destino) {

        // 1. Reusamos TAL CUAL tu servicio del Ejercicio 3, sin tocarlo
        RespuestaConvertorDTO resultado = conversorDivisasService.convertir(monto, origen, destino);

        // 2. Armamos la entidad a partir del resultado obtenido
        HistorialConversion historial = new HistorialConversion();
        historial.setMonedaOrigen(resultado.getMonedaOrigen());
        historial.setMonedaDestino(resultado.getMonedaDestino());
        historial.setMonto(BigDecimal.valueOf(resultado.getMontoOriginal()));
        historial.setMontoConvertido(BigDecimal.valueOf(resultado.getMontoConvertido()));
        historial.setTasa(BigDecimal.valueOf(resultado.getTasaDeCambio()));
        historial.setFechaConsulta(LocalDateTime.now());

        // 3. Guardamos en la base de datos
        historialConversionRepository.save(historial);

        // 4. Devolvemos la misma info de la consulta actual (tal como pide el enunciado)
        return resultado;
    }

    // ENDPOINT 2: GET /api/divisas/historial
    public List<HistorialItemDTO> obtenerHistorial(String origen, String destino) {

        List<HistorialConversion> historial = historialConversionRepository
                .findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(
                        origen.toUpperCase(), destino.toUpperCase()
                );

        return historial.stream()
                .map(h -> new HistorialItemDTO(
                        h.getFechaConsulta().toString(),
                        h.getTasa().doubleValue()
                ))
                .collect(Collectors.toList());
    }

}