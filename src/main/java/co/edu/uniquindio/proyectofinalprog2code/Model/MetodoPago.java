package co.edu.uniquindio.proyectofinalprog2code.model;

import java.io.Serializable;

public class MetodoPago implements Serializable{
    private static final long serialVersionUID = 1L;

    private String idMetodoPago;
    private String numeroTarjeta;
    private String titular;
    private TipoPago tipoPago;

    public MetodoPago() {
    }

    public MetodoPago(String idMetodoPago, String numeroTarjeta, String titular, TipoPago tipoPago) {
        this.idMetodoPago = idMetodoPago;
        this.numeroTarjeta = numeroTarjeta;
        this.titular = titular;
        this.tipoPago = tipoPago;
    }

    // Getters y Setters
    public String getIdMetodoPago() {
        return idMetodoPago;
    }

    public void setIdMetodoPago(String idMetodoPago) {
        this.idMetodoPago = idMetodoPago;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getNumeroOculto() {
        if (numeroTarjeta != null && numeroTarjeta.length() >= 4) {
            return "****-" + numeroTarjeta.substring(numeroTarjeta.length() - 4);
        }
        return "****";
    }

    @Override
    public String toString() {
        return "MetodoPago{" +
                "idMetodoPago='" + idMetodoPago + '\'' +
                ", numeroTarjeta='" + getNumeroOculto() + '\'' +
                ", titular='" + titular + '\'' +
                ", tipoPago=" + tipoPago +
                '}';
    }
}
