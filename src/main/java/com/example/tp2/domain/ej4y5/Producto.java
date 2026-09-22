package com.example.tp2.domain.ej4y5;


import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    /*
     En el DER, "productos" tiene una columna categoria_id que es
     FOREIGN KEY hacia categorias(id). Eso significa: "MUCHOS productos
     pueden pertenecer a UNA categoría" --> de ahí el nombre @ManyToOne
    
     En vez de guardar un "Integer categoriaId" como si fuera
     un número suelto, guardamos una REFERENCIA al objeto Categoria
     completo. Así, desde un Producto podés navegar directamente:
     producto.getCategoria().getNombre()
     sin tener que hacer una consulta aparte a mano.
     */
    @ManyToOne
    @JoinColumn(name = "categoria_id")   // el nombre real de la columna FK en la tabla
    private Categoria categoria;

    public Producto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
