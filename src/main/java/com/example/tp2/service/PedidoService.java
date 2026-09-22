package com.example.tp2.service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.tp2.domain.ej4y5.DetallePedido;
import com.example.tp2.domain.ej4y5.Pedido;
import com.example.tp2.dto.ej4y5.PedidoRespuestaDTO;
import com.example.tp2.dto.ej4y5.ProductoPedidoDTO;
import com.example.tp2.repository.DetallePedidoRepository;
import com.example.tp2.repository.PedidoRepository;

@Service 
public class PedidoService {

     private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    // Inyección por constructor, mismo patrón que en ClienteService
    public PedidoService(PedidoRepository pedidoRepository,
                          DetallePedidoRepository detallePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public List<PedidoRespuestaDTO> buscarPedidos(Integer clienteId, String categoria,
                                                   LocalDate fechaDesde, LocalDate fechaHasta,
                                                   String estado) {

        // 1. Traemos los pedidos que cumplen los filtros (puede venir vacía la lista)
        List<Pedido> pedidos = pedidoRepository.buscarConFiltros(
                clienteId, categoria, fechaDesde, fechaHasta, estado
        );

        // 2. Convertimos CADA Pedido a su PedidoRespuestaDTO correspondiente
        return pedidos.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    /*
     * Convierte un Pedido (entidad) en un PedidoRespuestaDTO completo,
     * incluyendo la lista de productos y el total calculado.
     */
    private PedidoRespuestaDTO convertirADTO(Pedido pedido) {

        // Traemos TODOS los detalles de este pedido específico
        List<DetallePedido> detalles = detallePedidoRepository.findByPedido_Id(pedido.getId());

        // Convertimos cada DetallePedido en un ProductoPedidoDTO,
        // calculando el subtotal de cada línea (cantidad * precio_unitario)
        List<ProductoPedidoDTO> productosDTO = detalles.stream()
                .map(detalle -> new ProductoPedidoDTO(
                        detalle.getProducto().getNombre(),
                        detalle.getProducto().getCategoria().getNombre(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario().doubleValue() * detalle.getCantidad()
                ))
                .collect(Collectors.toList());

        // El total del pedido es la SUMA de todos los subtotales ya calculados
        double totalPedido = productosDTO.stream()
                .mapToDouble(ProductoPedidoDTO::getSubtotal)
                .sum();

        // Armamos el nombre completo del cliente: "Ana Garcia"
        String nombreCompleto = pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido();

        return new PedidoRespuestaDTO(
                pedido.getId(),
                nombreCompleto,
                pedido.getFechaPedido(),
                pedido.getEstado(),
                totalPedido,
                productosDTO
        );
    }

    
}
