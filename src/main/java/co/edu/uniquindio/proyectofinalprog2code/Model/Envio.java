package co.edu.uniquindio.proyectofinalprog2code.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Envio implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idEnvio;
    private Direccion origen;
    private Direccion destino;
    private double peso;
    private double volumen;
    private double costo;
    private EstadoEnvio estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEstimadaEntrega;
    private LocalDateTime fechaEntregaReal;
    private Usuario usuario;
    private Repartidor repartidor;
    private List<ServicioAdicional> serviciosAdicionales;
    private Pago pago;
    private List<String> historialEstados;

    public Envio() {
        this.serviciosAdicionales = new ArrayList<>();
        this.historialEstados = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoEnvio.SOLICITADO;
        this.historialEstados.add("SOLICITADO: " + fechaCreacion.toString());
    }

    // Getters y Setters
    public String getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(String idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Direccion getOrigen() {
        return origen;
    }

    public void setOrigen(Direccion origen) {
        this.origen = origen;
    }

    public Direccion getDestino() {
        return destino;
    }

    public void setDestino(Direccion destino) {
        this.destino = destino;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
        this.historialEstados.add(estado.name() + ": " + LocalDateTime.now().toString());
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaEstimadaEntrega() {
        return fechaEstimadaEntrega;
    }

    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) {
        this.fechaEstimadaEntrega = fechaEstimadaEntrega;
    }

    public LocalDateTime getFechaEntregaReal() {
        return fechaEntregaReal;
    }

    public void setFechaEntregaReal(LocalDateTime fechaEntregaReal) {
        this.fechaEntregaReal = fechaEntregaReal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setServiciosAdicionales(List<ServicioAdicional> serviciosAdicionales) {
        this.serviciosAdicionales = serviciosAdicionales;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public List<String> getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(List<String> historialEstados) {
        this.historialEstados = historialEstados;
    }

    public void agregarServicioAdicional(ServicioAdicional servicio) {
        serviciosAdicionales.add(servicio);
    }

    public double calcularDistancia() {
        if (origen != null && destino != null) {
            return origen.calcularDistancia(destino);
        }
        return 0;
    }

    public boolean puedeCancelarse() {
        return estado == EstadoEnvio.SOLICITADO;
    }

    public boolean estaPago() {
        return pago != null && pago.esAprobado();
    }

    @Override
    public String toString() {
        return "Envio{" +
                "idEnvio='" + idEnvio + '\'' +
                ", origen=" + origen +
                ", destino=" + destino +
                ", peso=" + peso +
                ", volumen=" + volumen +
                ", costo=" + costo +
                ", estado=" + estado +
                ", fechaCreacion=" + fechaCreacion +
                ", usuario=" + usuario +
                '}';
    }
}
