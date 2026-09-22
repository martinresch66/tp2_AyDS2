package com.example.tp2.dto;

import java.util.Map;

/*esat clase tiene que tener exactamente la forma del JSON que te devuelve un servicio externo que no controlamos ene ste caso api.frankfurter.app */
/* la respuesta es con este estilo
{"amount":100.0,"base":"USD","date":"2026-09-21","rates":{"EUR":87.03}}
*/
public class FrankfurterResponseDTO {
     private double amount;
    private String base;
    private String date;
    /*Uso Map<String, Double> para rates porque la clave ("ARS", "EUR", etc.) cambia según qué moneda destino se pidio, no es un campo fijo */
    private Map<String, Double> rates;

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Map<String, Double> getRates() { return rates; }
    public void setRates(Map<String, Double> rates) { this.rates = rates; }

    
}
