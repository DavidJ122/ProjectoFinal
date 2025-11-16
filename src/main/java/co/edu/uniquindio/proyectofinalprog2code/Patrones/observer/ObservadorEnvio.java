package co.edu.uniquindio.proyectofinalprog2code.Patrones.observer;

import co.edu.uniquindio.proyectofinalprog2code.Model.EstadoEnvio;

public interface ObservadorEnvio {
    void actualizar(String idEnvio, EstadoEnvio nuevoEstado);
}