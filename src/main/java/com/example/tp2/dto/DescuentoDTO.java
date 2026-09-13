package com.example.tp2.dto;

import java.util.List;

public class DescuentoDTO {

    private List<VentaConDescuentoDTO> ventasConDescuento;
    private double totalConDescuento;


     // Constructor con todos los parámetros para llenarlo desde el Service
     public  DescuentoDTO(List<VentaConDescuentoDTO>  ventasConDescuento,double totalConDescuento){
        this.totalConDescuento=totalConDescuento;
        this.ventasConDescuento=ventasConDescuento;
     }

     //getters y setters
    public List<VentaConDescuentoDTO> getVentasConDescuento() {
        return ventasConDescuento;
    }
    public void setVentasConDescuento(List<VentaConDescuentoDTO> ventasConDescuento) {
        this.ventasConDescuento = ventasConDescuento;
    }

     public double getTotalConDescuento() {
         return totalConDescuento;
     }
     public void setTotalConDescuento(double totalConDescuento) {
         this.totalConDescuento = totalConDescuento;
     }


    
}
