package co.edu.uniquindio.proyectofinalprog2code.Patrones.decorator;

import co.edu.uniquindio.proyectofinalprog2code.model.Envio;

public class DecoradorSeguro implements ServicioAdicionalDecorator{
    @Override
    public double calcularCostoAdicional(Envio envio) {
        return 5000; // Costo fijo del seguro
    }
}
