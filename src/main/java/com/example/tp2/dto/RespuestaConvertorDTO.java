package com.example.tp2.dto;

import java.sql.Date;

import jakarta.validation.constraints.Positive;


/*Esta clase es el DTO  de salida, la respuesta que devuelve al cliente luego de la consulta */
public class RespuestaConvertorDTO {
    @Positive (message = "El monto de conversion debe ser mayor a 0")
    private double montoOriginal;
    private String monedaOrigen;
    private String monedaDestino;
    private double tasaDeCambio;
    private double montoConvertido;
    private String fecha;

    //constructor vacio
    public RespuestaConvertorDTO(){}
    
    public RespuestaConvertorDTO(double montoOriginal, String monedaOrigen, String monedaDestino,
                                   double tasaCambio, double montoConvertido, String fecha){
        this.montoOriginal = montoOriginal;
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.tasaDeCambio = tasaCambio;
        this.montoConvertido = montoConvertido;
        this.fecha = fecha;
    }
    public double getMontoOriginal() {
        return montoOriginal;
    }public void setMontoOriginal(double montoOriginal) {
        this.montoOriginal = montoOriginal;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }public void setMonedaOrigen(String monedaOrigen) {
        this.monedaOrigen = monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }public void setMonedaDestino(String monedaDestino) {
        this.monedaDestino = monedaDestino;
    }

    public double getTasaDeCambio() {
        return tasaDeCambio;
    }public void setTasaDeCambio(double tasaDeCambio) {
        this.tasaDeCambio = tasaDeCambio;
    }

    public double getMontoConvertido() {
        return montoConvertido;
    }public void setMontoConvertido(double montoConvertido) {
        this.montoConvertido = montoConvertido;
    }

    public String getFecha() {
        return fecha;
    }public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
