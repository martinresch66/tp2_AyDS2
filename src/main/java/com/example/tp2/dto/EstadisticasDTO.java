package com.example.tp2.dto;

public class EstadisticasDTO {

    private double totalFacturado;
    private int cantidadVentas;
    private double ticketPromedio;
    private VentaDTO ventaMayor;
    private VentaDTO ventaMenor;
    private String productoMasVendido;

    // Constructor con todos los parámetros para llenarlo desde el Service
    public EstadisticasDTO(double totalFacturado, int cantidadVentas, double ticketPromedio, 
                           VentaDTO ventaMayor, VentaDTO ventaMenor, String productoMasVendido) {
        this.totalFacturado = totalFacturado;
        this.cantidadVentas = cantidadVentas;
        this.ticketPromedio = ticketPromedio;
        this.ventaMayor = ventaMayor;
        this.ventaMenor = ventaMenor;
        this.productoMasVendido = productoMasVendido;
    }
// Getters y Setters
    public int getCantidadVentas() {
        return cantidadVentas;
    }public void setCantidadVentas(int cantidadVentas) {
        this.cantidadVentas = cantidadVentas;
    }
    
    public String getProductoMasVendido() {
        return productoMasVendido;
    }public void setProductoMasVendido(String productoMasVendido) {
        this.productoMasVendido = productoMasVendido;
    }
    
    public double getTicketPromedio() {
        return ticketPromedio;
    }public void setTicketPromedio(double ticketPromedio) {
        this.ticketPromedio = ticketPromedio;
    }
    
    public double getTotalFacturado() {
        return totalFacturado;
    }public void setTotalFacturado(double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }
    
    public VentaDTO getVentaMayor() {
        return ventaMayor;
    }public void setVentaMayor(VentaDTO ventaMayor) {
        this.ventaMayor = ventaMayor;
    }
    
    public VentaDTO getVentaMenor() {
        return ventaMenor;
    }public void setVentaMenor(VentaDTO ventaMenor) {
        this.ventaMenor = ventaMenor;
    }


    
}
