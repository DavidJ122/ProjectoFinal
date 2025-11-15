package co.edu.uniquindio.proyectofinalprog2code.dto;

import java.io.Serializable;

public class MetricaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private double valor;
    private String unidad;

    public MetricaDTO() {
    }

    public MetricaDTO(String nombre, double valor, String unidad) {
        this.nombre = nombre;
        this.valor = valor;
        this.unidad = unidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

}
