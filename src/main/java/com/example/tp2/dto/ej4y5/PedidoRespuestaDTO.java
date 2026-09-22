package com.example.tp2.dto.ej4y5;

import java.time.LocalDate;
import java.util.List;

/*
 Representa la respuesta completa de UN pedido, tal como debe verse
en el JSON final. Contiene una LISTA de ProductoPedidoDTO adentro,
porque un pedido puede tener varios productos.
 */
public class PedidoRespuestaDTO {

    private Integer pedidoId;
    private String cliente;
    private LocalDate fecha;
    private String estado;
    private Double totalPedido;
    private List<ProductoPedidoDTO> productos;

    public PedidoRespuestaDTO() {
    }

    public PedidoRespuestaDTO(Integer pedidoId, String cliente, LocalDate fecha,
                               String estado, Double totalPedido, List<ProductoPedidoDTO> productos) {
        this.pedidoId = pedidoId;
        this.cliente = cliente;
        this.fecha = fecha;
        this.estado = estado;
        this.totalPedido = totalPedido;
        this.productos = productos;
    }

    public Integer getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Integer pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(Double totalPedido) {
        this.totalPedido = totalPedido;
    }

    public List<ProductoPedidoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoPedidoDTO> productos) {
        this.productos = productos;
    }
}