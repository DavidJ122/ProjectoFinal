package co.edu.uniquindio.proyectofinalprog2code.dto;

import java.io.Serializable;

public class CotizacionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private double costoBase;
    private double costoPeso;
    private double costoVolumen;
    private double costoPrioridad;
    private double costoTotal;
    private double distancia;

    public CotizacionDTO() {
    }

    public CotizacionDTO(double costoBase, double costoPeso, double costoVolumen,
                         double costoPrioridad, double costoTotal, double distancia) {
        this.costoBase = costoBase;
        this.costoPeso = costoPeso;
        this.costoVolumen = costoVolumen;
        this.costoPrioridad = costoPrioridad;
        this.costoTotal = costoTotal;
        this.distancia = distancia;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }

    public double getCostoPeso() {
        return costoPeso;
    }

    public void setCostoPeso(double costoPeso) {
        this.costoPeso = costoPeso;
    }

    public double getCostoVolumen() {
        return costoVolumen;
    }

    public void setCostoVolumen(double costoVolumen) {
        this.costoVolumen = costoVolumen;
    }

    public double getCostoPrioridad() {
        return costoPrioridad;
    }

    public void setCostoPrioridad(double costoPrioridad) {
        this.costoPrioridad = costoPrioridad;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
