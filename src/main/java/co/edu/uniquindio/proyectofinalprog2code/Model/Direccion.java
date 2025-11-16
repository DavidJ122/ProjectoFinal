package co.edu.uniquindio.proyectofinalprog2code.Model;

import java.io.Serializable;

public class Direccion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idDireccion;
    private String alias;
    private String calle;
    private String ciudad;
    private double latitud;
    private double longitud;

    public Direccion() {
    }

    public Direccion(String idDireccion, String alias, String calle, String ciudad, double latitud, double longitud) {
        this.idDireccion = idDireccion;
        this.alias = alias;
        this.calle = calle;
        this.ciudad = ciudad;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    // Getters y Setters
    public String getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(String idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double calcularDistancia(Direccion destino) {
        // Fórmula de Haversine simplificada para distancia entre dos puntos
        double radioTierra = 6371; // km
        double dLat = Math.toRadians(destino.getLatitud() - this.latitud);
        double dLon = Math.toRadians(destino.getLongitud() - this.longitud);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.latitud)) * Math.cos(Math.toRadians(destino.getLatitud())) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return radioTierra * c;
    }

    @Override
    public String toString() {
        return "Direccion= " +
                "idDireccion: " + idDireccion +
                ", alias: " + alias +
                ", calle: " + calle +
                ", ciudad: " + ciudad +
                ", coordenadas: " + latitud + "," + longitud  ;
    }
}


