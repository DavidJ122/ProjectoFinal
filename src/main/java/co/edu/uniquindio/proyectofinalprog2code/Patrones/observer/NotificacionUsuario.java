package co.edu.uniquindio.proyectofinalprog2code.Patrones.observer;

import co.edu.uniquindio.proyectofinalprog2code.model.EstadoEnvio;

public class NotificacionUsuario implements ObservadorEnvio{
    private String nombreUsuario;

    public NotificacionUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public void actualizar(String idEnvio, EstadoEnvio nuevoEstado) {
        System.out.println("Notificación para " + nombreUsuario +
                ": Su envío " + idEnvio + " ha cambiado a estado " + nuevoEstado);
        // Aquí se podría enviar email, push notification, etc.
    }
}

