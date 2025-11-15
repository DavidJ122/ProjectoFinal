package co.edu.uniquindio.proyectofinalprog2code.Patrones.observer;

import co.edu.uniquindio.proyectofinalprog2code.model.EstadoEnvio;

import java.util.ArrayList;
import java.util.List;

public class SistemaObservableEnvio {
    private List<ObservadorEnvio> observadores;

    public SistemaObservableEnvio() {
        this.observadores = new ArrayList<>();
    }

    public void agregarObservador(ObservadorEnvio observador) {
        observadores.add(observador);
    }

    public void removerObservador(ObservadorEnvio observador) {
        observadores.remove(observador);
    }

    public void notificarCambio(String idEnvio, EstadoEnvio nuevoEstado) {
        for (ObservadorEnvio observador : observadores) {
            observador.actualizar(idEnvio, nuevoEstado);
        }
    }
}
