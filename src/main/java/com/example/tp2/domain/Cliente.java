package com.example.tp2.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity//lo que se guarda en la bd,en cambio ClienteDTO es lo que llega en el JSON de la peticion
@Table(name = "clientes" )
public class Cliente {

    @Id//clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY)// HIbernante no gnera el valor del id sino que lo genera la bd
    private Integer Id;

    @Column ( nullable = false, length = 100)
    private String nombre;

    
    @Column ( nullable = false, length = 100)
    private String apellido;

    @Column ( nullable = false,length = 100, unique = true)
    private String email;

    @Column (length = 20)
    private String telefono;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;


    public Cliente(){}

    public Integer getId() {
        return Id;
    }public void setId(Integer id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

   






    
}
