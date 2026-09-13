package com.example.tp2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tp2.dto.ApiResponse;
import com.example.tp2.domain.Producto;
import com.example.tp2.service.CatalogoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController 
@RequestMapping("/api/catalogo")/*Define la ruta base para todos los endpoints que estén dentro de esta clase.*/ 
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService){
        this.catalogoService = catalogoService;
    }

    //ENDPOINT 1: GET /api/catalogo -> Devuelve todos los productos
    @GetMapping
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerTodos() {
        List<Producto> lista = catalogoService.obtenerTodos();
        
        ApiResponse<List<Producto>> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Catálogo obtenido con éxito",
            lista
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ENDPOINT 2: GET /api/catalogo/buscar -> Filtra por categoría, precioMin y/o precioMax
    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Producto>>> buscarProductos(
        /*El @RequestParam es la anotación que le indica a Spring Boot: "busca en la URL de la petición web un parámetro que venga después del signo de interrogación (?) y asígnale su valor a esta variable de Java" */  
        /*Por defecto, cuando pones un @RequestParam, Spring asume que el parámetro es obligatorio.
        Al agregarle required = false, le permites al usuario enviar solo los filtros que quiera, combinarlos como prefiera, o incluso no enviar ninguno, haciendo que el endpoint sea totalmente flexible. */
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        
        List<Producto> resultados = catalogoService.buscarProductos(categoria, precioMin, precioMax);

        ApiResponse<List<Producto>> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Productos filtrados con éxito",
            resultados
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    //ENDPOINT 3: GET /api/catalogo/ordenar-> Ordena el catálogo según criterio y orden
    @GetMapping("/ordenar")
    public ResponseEntity<ApiResponse<List<Producto>>> ordenarProductos(
        @RequestParam (required = false) String criterio,
        @RequestParam (required = false) String orden){

        // Llamamos al servicio pasándole los parámetros que llegaron de la URL
        List<Producto> resultados = catalogoService.ordenarProductos(criterio, orden);

        // Empaquetamos el resultado dentro de tu formato estándar ApiResponse
        ApiResponse<List<Producto>> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Productos ordenados con éxito",
            resultados
        );
        
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    
    
     
    

    
}
