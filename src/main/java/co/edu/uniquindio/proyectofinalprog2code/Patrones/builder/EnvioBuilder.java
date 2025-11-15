package co.edu.uniquindio.proyectofinalprog2code.Patrones.builder;

import co.edu.uniquindio.proyectofinalprog2code.model.*;

import java.time.LocalDateTime;

public class EnvioBuilder {
    private Envio envio;

    public EnvioBuilder() {
        this.envio = new Envio();
    }

    public EnvioBuilder conId(String idEnvio) {
        envio.setIdEnvio(idEnvio);
        return this;
    }

    public EnvioBuilder conOrigen(Direccion origen) {
        envio.setOrigen(origen);
        return this;
    }

    public EnvioBuilder conDestino(Direccion destino) {
        envio.setDestino(destino);
        return this;
    }

    public EnvioBuilder conPeso(double peso) {
        envio.setPeso(peso);
        return this;
    }

    public EnvioBuilder conVolumen(double volumen) {
        envio.setVolumen(volumen);
        return this;
    }

    public EnvioBuilder conCosto(double costo) {
        envio.setCosto(costo);
        return this;
    }

    public EnvioBuilder conUsuario(Usuario usuario) {
        envio.setUsuario(usuario);
        return this;
    }

    public EnvioBuilder conRepartidor(Repartidor repartidor) {
        envio.setRepartidor(repartidor);
        return this;
    }

    public EnvioBuilder conEstado(EstadoEnvio estado) {
        envio.setEstado(estado);
        return this;
    }

    public EnvioBuilder agregarServicioAdicional(ServicioAdicional servicio) {
        envio.agregarServicioAdicional(servicio);
        return this;
    }

    public EnvioBuilder conFechaEstimadaEntrega(LocalDateTime fecha) {
        envio.setFechaEstimadaEntrega(fecha);
        return this;
    }

    public EnvioBuilder conPago(Pago pago) {
        envio.setPago(pago);
        return this;
    }

    public Envio construir() {
        return envio;
    }
}
