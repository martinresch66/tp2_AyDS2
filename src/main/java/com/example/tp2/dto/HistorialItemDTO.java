package com.example.tp2.dto;

public class HistorialItemDTO {

    private String fecha;
    private Double tasaCambio;

    public HistorialItemDTO() {
    }

    public HistorialItemDTO(String fecha, Double tasaCambio) {
        this.fecha = fecha;
        this.tasaCambio = tasaCambio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(Double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }
}