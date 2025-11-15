package co.edu.uniquindio.proyectofinalprog2code.Patrones.decorator;

import co.edu.uniquindio.proyectofinalprog2code.model.Envio;

public class DecoradorPrioritario implements ServicioAdicionalDecorator{
    @Override
    public double calcularCostoAdicional(Envio envio) {
        return 10000; // Costo fijo de entrega prioritaria
    }
}
