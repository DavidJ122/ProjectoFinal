package co.edu.uniquindio.proyectofinalprog2code.Model;

import java.time.LocalDateTime;
import java.io.Serializable;

public class Pago implements Serializable{
    private static final long serialVersionUID = 1L;

    private String idPago;
    private double monto;
    private LocalDateTime fecha;
    private MetodoPago metodoPago;
    private EstadoPago resultado;
    private String comprobante;

    public Pago() {
        this.fecha = LocalDateTime.now();
    }

    public Pago(String idPago, double monto, MetodoPago metodoPago, EstadoPago resultado) {
        this();
        this.idPago = idPago;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.resultado = resultado;
    }

    // Getters y Setters
    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPago getResultado() {
        return resultado;
    }

    public void setResultado(EstadoPago resultado) {
        this.resultado = resultado;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public boolean esAprobado() {
        return resultado == EstadoPago.APROBADO;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "idPago='" + idPago + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", metodoPago=" + metodoPago +
                ", resultado=" + resultado +
                '}';
    }
}
