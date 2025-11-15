package co.edu.uniquindio.proyectofinalprog2code.Patrones.strategy;

import co.edu.uniquindio.proyectofinalprog2code.model.Envio;
import co.edu.uniquindio.proyectofinalprog2code.model.ServicioAdicional;

public class CalculoTarifaPrioritaria implements CalculoTarifaStrategy{

    private final CalculoTarifaEstandar calculoEstandar;

    public CalculoTarifaPrioritaria() {
        this.calculoEstandar = new CalculoTarifaEstandar();
    }

    @Override
    public double calcularTarifa(Envio envio) {
        double costoBase = calculoEstandar.calcularTarifa(envio);

        // Agregar recargo por prioridad
        if (envio.getServiciosAdicionales().contains(ServicioAdicional.ENTREGA_PRIORITARIA)) {
            costoBase = costoBase * 1.5; // 50% adicional
        }

        return costoBase;
    }
}
