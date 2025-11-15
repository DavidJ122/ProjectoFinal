package co.edu.uniquindio.proyectofinalprog2code.model;

public enum ServicioAdicional {
    SEGURO("Seguro", 5000),
    ENTREGA_PRIORITARIA("Entrega Prioritaria", 10000),
    FRAGIL("Fragil", 3000),
    FIRMA_REQUERIDA("Firma Requerida", 0);

    private final String nombre;
    private final double costoAdicional;

    ServicioAdicional(String nombre, double costoAdicional) {
        this.nombre = nombre;
        this.costoAdicional = costoAdicional;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCostoAdicional() {
        return costoAdicional;
    }
}
