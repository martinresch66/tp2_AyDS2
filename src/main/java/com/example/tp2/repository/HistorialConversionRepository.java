package com.example.tp2.repository;
import com.example.tp2.domain.HistorialConversion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialConversionRepository extends JpaRepository<HistorialConversion, Integer> {

    /*
     * Método derivado: Spring genera automáticamente la consulta
     * "SELECT * FROM historial_conversiones 
     *  WHERE moneda_origen = ? AND moneda_destino = ?
     *  ORDER BY fecha_consulta DESC"
     *
     * "OrderByFechaConsultaDesc" cumple el requisito del enunciado:
     * "Ordenarlas por fecha de consulta, de la más reciente a la más antigua"
     * (Desc = descendente = de más nueva a más vieja)
     */
    List<HistorialConversion> findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(
            String monedaOrigen, String monedaDestino
    );

}