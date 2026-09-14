package com.example.tp2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.tp2.dto.ApiResponse;
import com.example.tp2.dto.NuevoProductoDTO;
import com.example.tp2.domain.Producto;
import com.example.tp2.service.CatalogoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        
        // 1. Si mandaron un criterio pero NO es ninguno de los dos válidos, rechazamos con 400
        if (criterio != null && !criterio.equalsIgnoreCase("nombre") && !criterio.equalsIgnoreCase("precio")) {
            
            ApiResponse<List<Producto>> responseError = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Criterio de ordenamiento inválido. Use 'nombre' o 'precio'.",
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
        }

        // 2. Si mandaron un orden pero NO es ninguno de los dos válidos, rechazamos con 400
        if ( orden != null && !orden.equalsIgnoreCase("asc") && !orden.equalsIgnoreCase("desc")) {
            
            ApiResponse<List<Producto>> responseError = new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                "Orden inválido. Use 'asc' o 'desc'.",
                null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseError);
        }

        // 3. Si todo está bien, llamamos al servicio normalmente
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

    //ENDPOINT 4: POST /api/catalogo
    @PostMapping
    public ResponseEntity<ApiResponse<Producto>> agregarProducto(@Valid @RequestBody NuevoProductoDTO productoDTO){
        // Llamamos al servicio para que procese el guardado
        Producto productoCreado = catalogoService.agregarProducto(productoDTO);

        // Armamos la respuesta estándar con HTTP 201 Created
        ApiResponse<Producto> response = new ApiResponse<>(
            HttpStatus.CREATED.value(),
            "Producto creado con éxito",
            productoCreado
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//ENDPOINT 5: PUT /api/catalogo/{id}/stock?cantidad=5
    @PutMapping("/{id}/stock") 
    public ResponseEntity<ApiResponse<Producto>> actualizarStock(
        @PathVariable long id,//Extrae un valor que está incrustado directamente dentro de la ruta (URL) de la peticion
        @RequestParam int cantidad //Extrae un parámetro que viaja despues del signo de interrogacion (?) en la URL
    ){
        // Llamamos al servicio para procesar la actualización
        Producto productoActualizado = catalogoService.actualizartock(id, cantidad);

        // Armamos la respuesta estándar con HTTP 200 OK
        ApiResponse<Producto> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Stock actualizado con éxito",
            productoActualizado
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

//ENDPOINT 6:/api/catalogo/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminarProducto(@PathVariable long id) {
        catalogoService.eliminarProducto(id);

        ApiResponse<Void> response = new ApiResponse<>(
            HttpStatus.OK.value(),
            "Producto eliminado con éxito",
            null
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}