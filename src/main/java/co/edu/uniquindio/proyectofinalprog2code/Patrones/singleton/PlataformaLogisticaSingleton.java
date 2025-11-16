package co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton;

import co.edu.uniquindio.proyectofinalprog2code.Model.Empresa;

public class PlataformaLogisticaSingleton {
    private static PlataformaLogisticaSingleton instancia;
    private Empresa empresa;

    private PlataformaLogisticaSingleton() {
        this.empresa = new Empresa();
    }

    public static PlataformaLogisticaSingleton getInstancia() {
        if (instancia == null) {
            instancia = new PlataformaLogisticaSingleton();
        }
        return instancia;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void reset() {
        instancia = null;
    }
}
