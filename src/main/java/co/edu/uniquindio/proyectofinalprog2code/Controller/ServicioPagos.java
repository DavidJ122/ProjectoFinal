package co.edu.uniquindio.proyectofinalprog2code.Controller;

import co.edu.uniquindio.proyectofinalprog2code.Model.*;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.facade.PlataformaFacade;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio de negocio para gestión de pagos (RF-033 a RF-035)
 */
public class ServicioPagos {
    private final PlataformaFacade facade;

    public ServicioPagos() {
        this.facade = new PlataformaFacade();
    }

    public Pago procesarPago(Envio envio, MetodoPago metodoPago) {
        Pago pago = facade.procesarPago(envio.getCosto(), metodoPago);
        envio.setPago(pago);
        return pago;
    }

    public String generarComprobante(Pago pago) {
        if (pago.esAprobado()) {
            return "Comprobante generado: " + pago.getComprobante();
        }
        return "El pago fue rechazado";
    }

    public List<Pago> filtrarPorFecha(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Empresa empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();
        return empresa.getEnvios().stream()
                .filter(e -> e.getPago() != null)
                .filter(e -> e.getPago().getFecha().isAfter(fechaInicio) &&
                        e.getPago().getFecha().isBefore(fechaFin))
                .map(Envio::getPago)
                .toList();
    }
}