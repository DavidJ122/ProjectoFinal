package co.edu.uniquindio.proyectofinalprog2code.Model;

public class Incidencia {
    private String idIncidencia;
    private String descripcion;
    private String fecha;
    private String tipoIncidencia;
    private Envio envio;

    public Incidencia(String descripcion, String fecha, String tipoIncidencia, Envio envio) {
        this.idIncidencia = Long.toHexString(System.nanoTime());;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.tipoIncidencia = tipoIncidencia;
        this.envio = envio;
    }

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public String getFecha() {
        return fecha;
    }
    public String getTipoIncidencia() {
        return tipoIncidencia;
    }
    public Envio getIdEnvio() {
        return envio;
    }
    public void setIdIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }
    public void setEnvio(Envio envio) {
        this.envio = envio;
    }
}
