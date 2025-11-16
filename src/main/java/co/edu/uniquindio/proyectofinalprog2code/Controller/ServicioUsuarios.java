package co.edu.uniquindio.proyectofinalprog2code.Controller;

import co.edu.uniquindio.proyectofinalprog2code.Excepciones.PlataformaLogisticaException;
import co.edu.uniquindio.proyectofinalprog2code.Model.*;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;

import java.util.List;

/**
 * Servicio de negocio para gestión de usuarios (RF-015 a RF-018)
 * Aplica principio SRP: única responsabilidad = gestión de usuarios
 */
public class ServicioUsuarios {
    private final Empresa empresa;

    public ServicioUsuarios() {
        this.empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();
    }

    public Usuario autenticarUsuario(String correo, String contrasena) throws PlataformaLogisticaException {
        Usuario usuario = empresa.buscarUsuarioPorCorreo(correo);
        if (usuario == null) {
            throw new PlataformaLogisticaException("Usuario no encontrado");
        }
        if (!usuario.getContrasena().equals(contrasena)) {
            throw new PlataformaLogisticaException("Contraseña incorrecta");
        }
        return usuario;
    }

    public Usuario registrarUsuario(String idUsuario, String nombreCompleto, String correo,
                                    String telefono, String contrasena) throws PlataformaLogisticaException {
        if (empresa.buscarUsuarioPorCorreo(correo) != null) {
            throw new PlataformaLogisticaException("Ya existe un usuario con este correo");
        }

        Usuario usuario = new Usuario(idUsuario, nombreCompleto, correo, telefono, contrasena);
        empresa.agregarUsuario(usuario);
        return usuario;
    }

    public void actualizarUsuario(Usuario usuario) throws PlataformaLogisticaException {
        Usuario existente = empresa.buscarUsuarioPorId(usuario.getIdUsuario());


        if (existente == null) {
            throw new PlataformaLogisticaException("Usuario no encontrado");
        }

        existente.setNombreCompleto(usuario.getNombreCompleto());
        existente.setCorreo(usuario.getCorreo());
        existente.setTelefono(usuario.getTelefono());
        existente.setTipoUsuario(usuario.getTipoUsuario());
    }

    public void eliminarUsuario(Usuario usuario) throws PlataformaLogisticaException {

        List<Envio> Envios_usuario = empresa.buscarEnviosPorUsuario(usuario.getIdUsuario());
        try {
            for (Envio envio : Envios_usuario) {
                empresa.eliminarEnvio(envio);
            }
            empresa.eliminarUsuario(usuario.getIdUsuario());
        }
        catch (Exception ex) {
            throw new PlataformaLogisticaException(ex.getMessage());
        }

    }

    public void agregarDireccion(Usuario usuario, Direccion direccion) {
        usuario.agregarDireccion(direccion);
    }

    public void eliminarDireccion(Usuario usuario, Direccion direccion) {
        usuario.eliminarDireccion(direccion);
    }

    public void agregarMetodoPago(Usuario usuario, MetodoPago metodoPago) {
        usuario.agregarMetodoPago(metodoPago);
    }

    public void eliminarMetodoPago(Usuario usuario, MetodoPago metodoPago) {
        usuario.eliminarMetodoPago(metodoPago);
    }

    public List<Envio> consultarEnvios(Usuario usuario) {
        return empresa.buscarEnviosPorUsuario(usuario.getIdUsuario());
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return empresa.getUsuarios();
    }
}