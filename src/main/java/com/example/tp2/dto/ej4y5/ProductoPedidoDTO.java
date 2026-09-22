package com.example.tp2.dto.ej4y5;

/*
 Representa CADA producto dentro de la lista "productos" de un pedido,
tal como lo pide el JSON de respuesta del enunciado.
No es la entidad Producto completa: solo los 4 campos que hay que mostrar.
 */
public class ProductoPedidoDTO {

    private String nombre;
    private String categoria;
    private Integer cantidad;
    private Double subtotal;

    public ProductoPedidoDTO() {
    }

    public ProductoPedidoDTO(String nombre, String categoria, Integer cantidad, Double subtotal) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}