package co.edu.uniquindio.proyectofinalprog2code.Controller;


import co.edu.uniquindio.proyectofinalprog2code.Model.*;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.facade.PlataformaFacade;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.observer.SistemaObservableEnvio;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;

import java.util.List;

/**
 * Servicio de negocio para gestión de envíos (RF-022 a RF-026)
 */
public class ServicioEnvios {
    private final Empresa empresa;
    private final PlataformaFacade facade;
    private final SistemaObservableEnvio observador;

    public ServicioEnvios() {
        this.empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();
        this.facade = new PlataformaFacade();
        this.observador = new SistemaObservableEnvio();
    }

    public Envio crearEnvio(Direccion origen, Direccion destino, double peso,
                            double volumen, Usuario usuario) throws PlataformaLogisticaException {
        Envio envio = new Envio();
        envio.setIdEnvio("ENV-" + System.currentTimeMillis());
        envio.setOrigen(origen);
        envio.setDestino(destino);
        envio.setPeso(peso);
        envio.setVolumen(volumen);
        envio.setUsuario(usuario);

        // Calcular costo
        PlataformaFacade.ResultadoCotizacion cotizacion =
                facade.cotizarEnvio(origen, destino, peso, volumen, false);
        envio.setCosto(cotizacion.getCostoTotal());

        empresa.agregarEnvio(envio);
        return envio;
    }

    public void cancelarEnvio(String idEnvio) throws PlataformaLogisticaException {
        Envio envio = empresa.buscarEnvioPorId(idEnvio);
        if (envio == null) {
            throw new PlataformaLogisticaException("Envío no encontrado");
        }
        if (!envio.puedeCancelarse()) {
            throw new PlataformaLogisticaException("No se puede cancelar el envío en estado " + envio.getEstado());
        }
    }

    public void actualizarEstado(String idEnvio, EstadoEnvio nuevoEstado) throws PlataformaLogisticaException {
        empresa.actualizarEstadoEnvio(idEnvio, nuevoEstado);
        observador.notificarCambio(idEnvio, nuevoEstado);
    }

    public void asignarRepartidor(String idEnvio, String idRepartidor) throws PlataformaLogisticaException {
        empresa.asignarRepartidorAEnvio(idEnvio, idRepartidor);
    }

    public List<Envio> filtrarPorFecha(java.time.LocalDate fecha) {
        return empresa.getEnvios().stream()
                .filter(e -> e.getFechaCreacion().toLocalDate().equals(fecha))
                .toList();
    }

    public List<Envio> filtrarPorEstado(EstadoEnvio estado) {
        return empresa.filtrarEnviosPorEstado(estado);
    }

    public Envio consultarEnvio(String idEnvio) {
        return empresa.buscarEnvioPorId(idEnvio);
    }

    public SistemaObservableEnvio getObservador() {
        return observador;
    }
}
