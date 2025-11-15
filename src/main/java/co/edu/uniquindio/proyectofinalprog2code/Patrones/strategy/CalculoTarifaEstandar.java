package co.edu.uniquindio.proyectofinalprog2code.Patrones.strategy;

import co.edu.uniquindio.proyectofinalprog2code.model.Envio;

public class CalculoTarifaEstandar implements CalculoTarifaStrategy{
    private final double costoBasePorKm = 2000;
    private final double costoPorKilo = 1000;
    private final double costoPorVolumen = 500;

    @Override
    public double calcularTarifa(Envio envio) {
        double distancia = envio.calcularDistancia();
        double costoBase = distancia * costoBasePorKm;
        double costoPeso = envio.getPeso() * costoPorKilo;
        double costoVolumen = envio.getVolumen() * costoPorVolumen;

        return costoBase + costoPeso + costoVolumen;
    }
}
