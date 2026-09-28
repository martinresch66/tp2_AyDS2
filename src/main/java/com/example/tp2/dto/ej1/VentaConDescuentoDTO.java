package com.example.tp2.dto.ej1;

public class VentaConDescuentoDTO extends VentaDTO{
   
    private double montoConDescuento;
    // Constructor vacío (necesario para Spring/Jackson)
    public VentaConDescuentoDTO() {
    }

    public double getMontoConDescuento() {
        return montoConDescuento;
    }
    public void setMontoConDescuento(double montoConDescuento) {
        this.montoConDescuento = montoConDescuento;
    }

    
}
