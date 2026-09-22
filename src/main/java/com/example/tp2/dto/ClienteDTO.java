package com.example.tp2.dto;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClienteDTO {
    @NotBlank (message = "el campo nombre no puede estar vacio")
    @Size(min = 2, message = "el campo nombre debe tener al menos 2 letras")
    private String nombre;
    @NotBlank (message = "el campo apellido no puede estar vacio")
    @Size(min = 2, message = "el campo apellido debe tener al menos 2 letras")
    private String apellido;
    @NotBlank (message = "el campo email no puede estar vacio")
    @Email (message = "El email debe tener un formato válido")
    private String email;

    /*este campo String debe coincidir con tal patrón de texto 
    regexp --> la expresión regular que define el patrón permitido*/
    /*como \ es un caracter especail hay q ponerlo doble para que se acepte el verdadero dato 
    que es \d*,
    \d --> significa "un dígito" (cualquier número del 0 al 9).
    * --> significa "cero o más repeticiones" del elemento anterior(gracias a esto no toma al campo nullo como una falla).
     */
    @Pattern (regexp = "\\d*", message = "el campo telefono solo puede contener numeros")
    private String telefono;

    public ClienteDTO(){}

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

  
    
    
    
}
