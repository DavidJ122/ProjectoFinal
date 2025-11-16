package co.edu.uniquindio.proyectofinalprog2code.Patrones.strategy;

import co.edu.uniquindio.proyectofinalprog2code.Model.Envio;

public interface CalculoTarifaStrategy {
    double calcularTarifa(Envio envio);
}

