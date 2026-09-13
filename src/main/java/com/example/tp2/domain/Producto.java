package com.example.tp2.domain;

public class Producto {
    
    private long id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    // Constructor vacío
    public Producto() {}

    //Constructor con parametros
    public Producto(long id,String nombre,String categoria,double precio,int stock){
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    //getters y setters
    public long getId() {
        return id;
    }public void setId(long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCategoria() {
        return categoria;
    }public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public double getPrecio() {
        return precio;
    }public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public int getStock() {
        return stock;
    }public void setStock(int stock) {
        this.stock = stock;
    }


    
}
