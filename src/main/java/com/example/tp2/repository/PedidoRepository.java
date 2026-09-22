package com.example.tp2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tp2.domain.ej4y5.Pedido;

import java.time.LocalDate;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>  {
    
       /*
     Esta consulta busca pedidos aplicando 4 filtros OPCIONALES.
     La clave esta en el patron: (:parametro IS NULL OR condicion)
     
     Por ejemplo: (:clienteId IS NULL OR c.id = :clienteId)
     Significa: "si NO me mandaron clienteId, ignora esta condicion
     (deja pasar todo); si SI me lo mandaron, aplica el filtro real".
     
    Repetimos ese patron para cada uno de los 4 filtros, y los unimos
    con AND -> asi se cumple "si mandan varios, se aplican todos juntos".
     
     DISTINCT es necesario porque, al hacer JOIN con detalle_pedidos y
     productos, un mismo pedido puede aparecer repetido (una vez por
     cada producto que tiene). DISTINCT evita pedidos duplicados en
     el resultado.
     */
    @Query("SELECT DISTINCT p FROM Pedido p " +
           "JOIN p.cliente c " +
           "LEFT JOIN DetallePedido dp ON dp.pedido = p " +
           "LEFT JOIN dp.producto pr " +
           "LEFT JOIN pr.categoria cat " +
           "WHERE (:clienteId IS NULL OR c.id = :clienteId) " +
           "AND (:categoria IS NULL OR cat.nombre = :categoria) " +
           "AND (:fechaDesde IS NULL OR p.fechaPedido >= :fechaDesde) " +
           "AND (:fechaHasta IS NULL OR p.fechaPedido <= :fechaHasta) " +
           "AND (:estado IS NULL OR p.estado = :estado)")
    
    List<Pedido> buscarConFiltros(
            @Param("clienteId") Integer clienteId,
            @Param("categoria") String categoria,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta,
            @Param("estado") String estado
    );
}
