package com.example.tp2.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Comparator;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.tp2.domain.Producto;
import com.example.tp2.dto.NuevoProductoDTO;

import io.swagger.v3.oas.annotations.servers.Server;

@Service 
public class CatalogoService {

    // La colección en memoria que pide la consigna
    private final List<Producto> productos = new ArrayList<>();

    //Constructor:se ejecuta arrancar la app y carga 8 productos de ejemplo
    CatalogoService(){
        productos.add(new Producto(1,"Teclado","Perifericos",25000.0,10));
        productos.add(new Producto(2, "Mouse Gamer", "Perifericos", 12000.0, 15));
        productos.add(new Producto(3, "Monitor 24 pulgadas", "Monitores", 85000.0, 5));
        productos.add(new Producto(4, "Auriculares Inalámbricos", "Audio", 30000.0, 8));
        productos.add(new Producto(5, "Notebook i5", "Laptops", 350000.0, 3));
        productos.add(new Producto(6, "Silla Gamer", "Muebles", 120000.0, 4));
        productos.add(new Producto(7, "Pad Mouse XL", "Perifericos", 5000.0, 25));
        productos.add(new Producto(8, "Webcam HD", "Perifericos", 18000.0, 12));
    }

//ENDPOINT 1: GET /api/catalogo
    public List<Producto> obtenerTodos(){
        return productos;
    }

//ENDPOINT 2: GET /api/catalogo/buscar?-> Filtra por categoría, precio mínimo y/o precio máximo usando Streams
    
    public List<Producto> buscarProductos( String categoria , Double precioMin , Double precioMax ){
        return productos.stream()//Transforma tu lista normal de Java en un flujo de datos (Stream) para poder aplicarle operaciones funcionales
        // Filtra por categoría solo si el parámetro no es nulo ni está vacío
        .filter(p -> categoria == null || categoria.trim().isEmpty() || p.getCategoria().equalsIgnoreCase(categoria))
       // Filtra por precio mínimo solo si el parámetro no es nulo
        .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
        // Filtra por precio máximo solo si el parámetro no es nulo
        .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
        // Convierte el stream filtrado de vuelta a una Lista
        .toList();

    }

//ENDPOINT 3: GET /api/catalogo/ordenar
    public List<Producto> ordenarProductos(String criterio, String orden){
        List<Producto> listaOrdenada = new ArrayList<>(productos);
//1. Asignamos valores por default si los paramteros vienen vacios(/api/catalogo/ordenar/criterio="") o nullos(/api/catalogo/ordenar)
        if(criterio == null || criterio == ""){
            criterio = "precio";
        }
        if(orden == null || orden == ""){
            orden= "asc";
        }

// Declaramos el comparador inicializándolo por defecto con el precio
        Comparator<Producto> comparador = Comparator.comparing(Producto::getPrecio);

// 2. Validamos los criterios 
        if (criterio.equalsIgnoreCase("nombre")) {
            comparador = Comparator.comparing(Producto::getNombre);
        } 

 // 3. Validamos el sentido del orden
        if (orden.equalsIgnoreCase("desc")) {
            comparador = comparador.reversed();
        }
       

        return listaOrdenada.stream()
                .sorted(comparador)//recorre la lista mediante el Stream y ordena físicamente los elementos
                .toList();//el resultado ordenado se empaqueta en una lista

    }

//ENDPOINT 4: POST /api/catalogo
    public Producto agregarProducto(NuevoProductoDTO productoDTO){
        // Generamos un ID autoincremental basado en el tamaño actual de la lista
        long nuevoId = productos.size() + 1;
        // Mapeamos el DTO a nuestra entidad Producto
        Producto nuevoProducto = new Producto(
            nuevoId,
            productoDTO.getNombre(),
            productoDTO.getCategoria(),
            productoDTO.getPrecio(),
            productoDTO.getStock()
        );
        // Lo agregamos a la colección en memoria
        productos.add(nuevoProducto);

        return nuevoProducto;
    }

//ENDPOINT 5: PUT /api/catalogo/{id}/stock?cantidad=5

    public Producto actualizartock(long id , int cantidad){
        Producto producto = productos.stream()
        .filter(p->p.getId() == id)
        //como a lo sumo encuentra un id toma ese resultadoy lo envuelve en algo llamado un Optional (una cajita que puede contener el producto o venir vacía si no encontró a nadie)
        .findFirst()
         //lanza esta excepcion para ser capturada por GlobalExceptionHandler.java
        .orElseThrow(() -> new ResponseStatusException(//throw interrumpe la ejecución normal del método actualizarStock
            HttpStatus.NOT_FOUND, "Producto con id " + id + " no encontrado"));//si el optional tiene el producto,queda guardado en al variable producto, si no lanza excepcion

        int nuevoStock = producto.getStock() + cantidad;

        if(nuevoStock < 0){
            throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST, "El stock actual no puede quedar negativo" );
        }

        producto.setStock(nuevoStock);
        return producto;
        
    }

//ENDPOINT 6:/api/catalogo/{id}
    public void eliminarProducto(long id){
         Producto producto = productos.stream()
        .filter(p->p.getId() == id)
        //como a lo sumo encuentra un id toma ese resultado y lo envuelve en algo llamado un Optional (una cajita que puede contener el producto o venir vacía si no encontró a nadie)
        .findFirst()
         //lanza esta excepcion para ser capturada por GlobalExceptionHandler.java
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Producto con id " + id + " no encontrado"));

        productos.remove(producto);
        
    }
 
}
