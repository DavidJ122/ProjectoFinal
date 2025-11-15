package co.edu.uniquindio.proyectofinalprog2code.Patrones.command;

import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;
import co.edu.uniquindio.proyectofinalprog2code.model.*;

public class ComandoActualizarEstadoEnvio implements Comando{
    private Empresa empresa;

    private String idEnvio;
    private EstadoEnvio nuevoEstado;
    private EstadoEnvio estadoAnterior;
    private boolean ejecutado = false;

    public ComandoActualizarEstadoEnvio(String idEnvio, EstadoEnvio nuevoEstado) {
        this.empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();
        this.idEnvio = idEnvio;
        this.nuevoEstado = nuevoEstado;
    }

    @Override
    public void ejecutar() throws Exception {
        if (!ejecutado) {
            Envio envio = empresa.buscarEnvioPorId(idEnvio);
            if (envio == null) {
                throw new PlataformaLogisticaException("Envío no encontrado");
            }
            estadoAnterior = envio.getEstado();
            empresa.actualizarEstadoEnvio(idEnvio, nuevoEstado);
            ejecutado = true;
        }
    }

    @Override
    public void deshacer() throws Exception {
        if (ejecutado && estadoAnterior != null) {
            empresa.actualizarEstadoEnvio(idEnvio, estadoAnterior);
            ejecutado = false;
        }
    }
}
