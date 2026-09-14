package com.example.tp2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class NuevoProductoDTO {
    @NotBlank(message = "el nombre no puede estar en blanco")
     private String nombre;
     
     @NotBlank(message = "la categoria no puede estar en blanco")
     private String categoria; 

     @NotNull(message = "El precio es obligatorio")
     @Positive (message = "el precio debe ser mayor que 0")
     private Double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser mayor o igual que 0")
    private int stock;

    //Constructor vacio
    public NuevoProductoDTO(){}
   
    //getters y setters
    public String getCategoria() {
        return categoria;
    }public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }public void setStock(int stock) {
        this.stock = stock;
    }



    
}
