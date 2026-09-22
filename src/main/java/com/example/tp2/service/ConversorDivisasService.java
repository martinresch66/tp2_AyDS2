package com.example.tp2.service;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import com.example.tp2.dto.FrankfurterResponseDTO;
import com.example.tp2.dto.RespuestaConvertorDTO;

@Service
public class ConversorDivisasService {


//el objeto que va a usarse para hablar con Frankfurter,
//RestClient(es una interfaz) es la herramienta que te da Spring para que la aplicacion pueda hacer peticiones HTTP hacia afuera(actuando como cliente de otra API)
    private final RestClient restClient = RestClient.builder()//builder (constructor por pasos),vas configurando distintas opciones encadenando métodos
            .baseUrl("https://api.frankfurter.dev/v1")//todas las peticiones que yo haga con este cliente van a tener esta URL como punto de partida
            .build();//paso final del builder, toma toda la configuracion dada y te devuelve el objeto RestClient

    // ENDPOINT 1: GET /api/divisas/convertir
    //parametros ya validados en Controller
    public RespuestaConvertorDTO convertir(double monto, String origen, String destino) {

        FrankfurterResponseDTO respuestaExterna;

        try {
            respuestaExterna = restClient.get()//Quiero hacer un GET
                    .uri("/latest?amount={monto}&from={origen}&to={destino}", monto, origen, destino)//A esta ruta, con estos valores(concatena baseUrl+uri)
                    .retrieve()//ejecuta la peticion y devuelve la respuesta
                    .body(FrankfurterResponseDTO.class);//convierto el JSON  de respuesta y lo convierte automáticamente (usando Jackson) en un objeto de esa clase.

        } catch (RestClientException ex) {//excepcion generica que cubre timeout, Frankfurter caído, error HTTP de Frankfurter, moneda inexistente, etc.
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,//transformamos la excepcion en un 502 Bad Gateway(que significa que yo tu servidor intente comunicarme con otro servidor y este servidor fallo)
                "No se pudo obtener la cotización desde el servicio externo: " + ex.getMessage());
        }


        double tasaCambio = respuestaExterna.getRates().get(destino);
        double montoConvertido = tasaCambio;

        return new RespuestaConvertorDTO(
            monto,
            origen.toUpperCase(),
            destino.toUpperCase(),
            tasaCambio / monto, // tasa unitaria: cuánto vale 1 unidad de origen en destino
            montoConvertido,
            respuestaExterna.getDate()
        );
    }
}
