package co.edu.uniquindio.proyectofinalprog2code.Patrones.command;

import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;
import co.edu.uniquindio.proyectofinalprog2code.Model.*;

public class ComandoCrearEnvio implements Comando{
    private Empresa empresa;
    private Envio envio;
    private boolean ejecutado = false;

    public ComandoCrearEnvio(Envio envio) {
        this.empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();
        this.envio = envio;
    }

    @Override
    public void ejecutar() throws Exception {
        if (!ejecutado) {
            empresa.agregarEnvio(envio);
            ejecutado = true;
        }
    }

    @Override
    public void deshacer() throws Exception {
        if (ejecutado) {
            // Eliminar el envío (se podría guardar referencia previa)
            ejecutado = false;
        }
    }
}
