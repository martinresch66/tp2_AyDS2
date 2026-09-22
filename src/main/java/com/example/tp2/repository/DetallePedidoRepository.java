package com.example.tp2.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tp2.domain.ej4y5.DetallePedido;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    // Metodo derivado: Spring genera automaticamente
    // SELECT * FROM detalle_pedidos WHERE pedido_id = ?
    List<DetallePedido> findByPedido_Id(Integer pedidoId);//findByPedido_Id navega a traves de la relacion pedido (el objeto Pedido completo dentro de DetallePedido), y filtra por su atributo id

}