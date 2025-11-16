package co.edu.uniquindio.proyectofinalprog2code.util;

import co.edu.uniquindio.proyectofinalprog2code.Model.Usuario;

/**
 * Clase singleton para manejar la sesión del usuario actual
 */
public class Sesion {
    private static Sesion instancia;
    private Usuario usuarioActual;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (instancia == null) {
            instancia = new Sesion();
        }
        return instancia;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    public boolean hayUsuarioLogeado() {
        return usuarioActual != null;
    }
}