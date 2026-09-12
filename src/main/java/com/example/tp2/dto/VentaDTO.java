package com.example.tp2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class VentaDTO {
    //Variables con validaciones
    @NotBlank(message = "El producto no puede ser vacio") 
    private String producto;
    @Positive(message = "La cantidad del producto debe ser unn entero mayor a 0")
    private int cantidad;
    @Positive(message = "El precio unitario debe ser mayor a 0")
    private double precioUnitario; 

    // Constructor vacío escrito explícitamente, sino lo escribo Java lo genera automaticamente
    // Constructor vacío necesario para que Spring/Jackson pueda 
    // instanciar el objeto en blanco antes de inyectarle los 
    // datos del JSON recibido (deserialización).
    public VentaDTO() {
    }

    //setters y getters
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
    public String getProducto() {
        return producto;
    }

}
