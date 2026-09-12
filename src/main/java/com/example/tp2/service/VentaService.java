package com.example.tp2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tp2.dto.EstadisticasDTO;
import com.example.tp2.dto.VentaDTO;

/* esta capa es la encargada de la
 lógica de negocio. Aquí no manejamos nada de 
 HTTP ni de anotaciones web (@RestController),
solo recibimos objetos de Java, hacemos los
 cálculos matemáticos y devolvemos los resultados*/
@Service 
 public class VentaService {
    

//ENDPOINT 1: POST /api/ventas/estadisticas
    public EstadisticasDTO calcularEstadistica(List<VentaDTO> ventas){
    
    // Control de seguridad por si la lista viene vacía o nula
    if (ventas == null || ventas.isEmpty()) {
        return new EstadisticasDTO(0, 0, 0, null, null, null);
    }

    double totalFacturado = 0;
    int cantidadVentas = ventas.size();
    
    double ticketPromedio = 0;

    VentaDTO ventaMayor = ventas.get(0);
    VentaDTO ventaMenor = ventas.get(0);

    String productoMasVendido = null;



    // Variables para ayudar a calcular el importe de cada venta (cantidad * precioUnitario)
    double mayorImporte = ventas.get(0).getCantidad() * ventas.get(0).getPrecioUnitario();
    double menorImporte = mayorImporte;

    for(int i=0; i < ventas.size(); i++){
        VentaDTO v = ventas.get(i);
        
        double importeActual = v.getPrecioUnitario()*v.getCantidad();

        totalFacturado= totalFacturado + importeActual;

        if(importeActual>mayorImporte){
            mayorImporte = importeActual;
            ventaMayor = v;
        }
        if(importeActual<menorImporte){
            menorImporte = importeActual;
            ventaMenor = v;
        }

    }

    ticketPromedio = totalFacturado/cantidadVentas;
    productoMasVendido = calcularProductoMasVendido(ventas); 

    return new EstadisticasDTO(
            totalFacturado, 
            cantidadVentas, 
            ticketPromedio, 
            ventaMayor, 
            ventaMenor, 
            productoMasVendido
        );
}
    private String calcularProductoMasVendido(List<VentaDTO> ventas){
            
        if (ventas == null || ventas.isEmpty()) {
            return null;
        }

        // 1. Usamos un Map para acumular la cantidad total de cada producto
        // Clave: Nombre del producto (String) | Valor: Cantidad total sumada (Integer)
        java.util.Map<String, Integer> mapaCantidades = new java.util.HashMap<>();

        for(int i=0; i < ventas.size(); i++){
            VentaDTO v = ventas.get(i);
            String nombreProducto = v.getProducto();
            int cantidadActual = v.getCantidad();

            // Si el producto ya estaba en el mapa, le sumamos la nueva cantidad. 
            // Si es la primera vez que aparece, arrancamos en 0.
            int cantidadTotal = mapaCantidades.getOrDefault(nombreProducto, 0) + cantidadActual;
            
            mapaCantidades.put(nombreProducto, cantidadTotal);
        }

        // 1. Extraemos las claves (los nombres de los productos) a una lista para poder usar el índice
        java.util.List<String> listaProductos = new java.util.ArrayList<>(mapaCantidades.keySet());

        String productoMasVendido = null;
        int mayorCantidadAcumulada = -1;

        // 2. Recorremos usando un for tradicional
        for (int i = 0; i < listaProductos.size(); i++) {
            String productoActual = listaProductos.get(i);
            int cantidadActual = mapaCantidades.get(productoActual); // Buscamos su valor en el map

            if (cantidadActual > mayorCantidadAcumulada) {
                mayorCantidadAcumulada = cantidadActual;
                productoMasVendido = productoActual;
            }
        }

        return productoMasVendido;
    }
}

