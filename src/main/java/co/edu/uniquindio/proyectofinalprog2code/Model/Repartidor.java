package co.edu.uniquindio.proyectofinalprog2code.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Repartidor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idRepartidor;
    private String nombre;
    private String documento;
    private String telefono;
    private EstadoRepartidor estado;
    private String zonaCobertura;
    private List<Envio> enviosAsignados;

    public Repartidor() {
        this.enviosAsignados = new ArrayList<>();
        this.estado = EstadoRepartidor.INACTIVO;
    }

    public Repartidor(String idRepartidor, String nombre, String documento, String telefono, String zonaCobertura) {
        this();
        this.idRepartidor = idRepartidor;
        this.nombre = nombre;
        this.documento = documento;
        this.telefono = telefono;
        this.zonaCobertura = zonaCobertura;
    }

    // Getters y Setters
    public String getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(String idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public EstadoRepartidor getEstado() {
        return estado;
    }

    public void setEstado(EstadoRepartidor estado) {
        this.estado = estado;
    }

    public String getZonaCobertura() {
        return zonaCobertura;
    }

    public void setZonaCobertura(String zonaCobertura) {
        this.zonaCobertura = zonaCobertura;
    }

    public List<Envio> getEnviosAsignados() {
        return enviosAsignados;
    }

    public void setEnviosAsignados(List<Envio> enviosAsignados) {
        this.enviosAsignados = enviosAsignados;
    }

    public void agregarEnvio(Envio envio) {
        enviosAsignados.add(envio);
    }

    public void removerEnvio(Envio envio) {
        enviosAsignados.remove(envio);
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "idRepartidor='" + idRepartidor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", documento='" + documento + '\'' +
                ", telefono='" + telefono + '\'' +
                ", estado=" + estado +
                ", zonaCobertura='" + zonaCobertura + '\'' +
                '}';
    }
}
