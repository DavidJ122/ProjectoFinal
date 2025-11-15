package co.edu.uniquindio.proyectofinalprog2code.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReporteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tipo;
    private String id;
    private LocalDateTime fecha;
    private String estado;
    private double monto;

    public ReporteDTO() {
    }

    public ReporteDTO(String tipo, String id, LocalDateTime fecha, String estado, double monto) {
        this.tipo = tipo;
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
