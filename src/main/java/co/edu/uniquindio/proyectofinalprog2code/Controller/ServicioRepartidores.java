package co.edu.uniquindio.proyectofinalprog2code.Controller;

import co.edu.uniquindio.proyectofinalprog2code.Model.*;
import co.edu.uniquindio.proyectofinalprog2code.Model.Repartidor;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;

import java.util.List;

/**
 * Servicio de negocio para gestión de repartidores (RF-019 a RF-021)
 */
public class ServicioRepartidores {
    private final Empresa empresa;

    public ServicioRepartidores() {
        this.empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();
    }

    public Repartidor crearRepartidor(String idRepartidor, String nombre, String documento,
                                      String telefono, String zonaCobertura) throws PlataformaLogisticaException {
        Repartidor repartidor = new Repartidor(idRepartidor, nombre, documento, telefono, zonaCobertura);
        repartidor.setEstado(EstadoRepartidor.ACTIVO);
        empresa.agregarRepartidor(repartidor);
        return repartidor;
    }

    public void actualizarRepartidor(Repartidor repartidor) throws PlataformaLogisticaException {
        Repartidor existente = empresa.buscarRepartidorPorId(repartidor.getIdRepartidor());
        if (existente == null) {
            throw new PlataformaLogisticaException("Repartidor no encontrado");
        }
        existente.setNombre(repartidor.getNombre());
        existente.setDocumento(repartidor.getDocumento());
        existente.setTelefono(repartidor.getTelefono());
        existente.setZonaCobertura(repartidor.getZonaCobertura());
    }

    public void eliminarRepartidor(String idRepartidor) throws PlataformaLogisticaException {
        empresa.eliminarRepartidor(idRepartidor);
    }

    public void cambiarDisponibilidad(String idRepartidor, EstadoRepartidor nuevoEstado)
            throws PlataformaLogisticaException {
        Repartidor repartidor = empresa.buscarRepartidorPorId(idRepartidor);
        if (repartidor == null) {
            throw new PlataformaLogisticaException("Repartidor no encontrado");
        }
        repartidor.setEstado(nuevoEstado);
    }

    public List<Envio> consultarEnviosAsignados(String idRepartidor) {
        return empresa.buscarEnviosPorRepartidor(idRepartidor);
    }

    public List<Repartidor> obtenerRepartidoresDisponibles() {
        return empresa.buscarRepartidoresDisponibles();
    }

    public List<Repartidor> obtenerTodosLosRepartidores() {
        return empresa.getRepartidores();
    }
}