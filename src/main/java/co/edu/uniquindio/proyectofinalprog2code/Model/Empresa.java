package co.edu.uniquindio.proyectofinalprog2code.Model;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private List<Usuario> usuarios;
    private List<Repartidor> repartidores;
    private List<Envio> envios;
    private Tarifa tarifa;

    public Empresa() {
        this.usuarios = new ArrayList<>();
        this.repartidores = new ArrayList<>();
        this.envios = new ArrayList<>();
        this.tarifa = new Tarifa();
    }

    // Métodos para usuarios
    public void agregarUsuario(Usuario usuario) throws PlataformaLogisticaException {
        if (buscarUsuarioPorId(usuario.getIdUsuario()) != null) {
            throw new PlataformaLogisticaException("El usuario con ID " + usuario.getIdUsuario() + " ya existe");
        }
        usuarios.add(usuario);
    }

    public Usuario buscarUsuarioPorId(String idUsuario) {
        return usuarios.stream()
                .filter(u -> u.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        return usuarios.stream()
                .filter(u -> u.getCorreo().equals(correo))
                .findFirst()
                .orElse(null);
    }

    public boolean autenticarUsuario(String correo, String contrasena) {
        Usuario usuario = buscarUsuarioPorCorreo(correo);
        return usuario != null && usuario.getContrasena().equals(contrasena);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void eliminarUsuario(String idUsuario) throws PlataformaLogisticaException {
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new PlataformaLogisticaException("Usuario no encontrado");
        }
        usuarios.remove(usuario);
    }

    // Métodos para repartidores
    public void agregarRepartidor(Repartidor repartidor) throws PlataformaLogisticaException {
        if (buscarRepartidorPorId(repartidor.getIdRepartidor()) != null) {
            throw new PlataformaLogisticaException("El repartidor con ID " + repartidor.getIdRepartidor() + " ya existe");
        }
        repartidores.add(repartidor);
    }

    public Repartidor buscarRepartidorPorId(String idRepartidor) {
        return repartidores.stream()
                .filter(r -> r.getIdRepartidor().equals(idRepartidor))
                .findFirst()
                .orElse(null);
    }

    public List<Repartidor> getRepartidores() {
        return repartidores;
    }

    public void eliminarRepartidor(String idRepartidor) throws PlataformaLogisticaException {
        Repartidor repartidor = buscarRepartidorPorId(idRepartidor);
        if (repartidor == null) {
            throw new PlataformaLogisticaException("Repartidor no encontrado");
        }
        repartidores.remove(repartidor);
    }

    public List<Repartidor> buscarRepartidoresDisponibles() {
        return repartidores.stream()
                .filter(r -> r.getEstado() == EstadoRepartidor.ACTIVO)
                .toList();
    }

    // Métodos para envíos
    public void agregarEnvio(Envio envio) throws PlataformaLogisticaException {
        if (buscarEnvioPorId(envio.getIdEnvio()) != null) {
            throw new PlataformaLogisticaException("El envío con ID " + envio.getIdEnvio() + " ya existe");
        }
        envios.add(envio);
        if (envio.getUsuario() != null) {
            envio.getUsuario().agregarEnvio(envio);
        }
    }

    public Envio buscarEnvioPorId(String idEnvio) {
        return envios.stream()
                .filter(e -> e.getIdEnvio().equals(idEnvio))
                .findFirst()
                .orElse(null);
    }

    public List<Envio> getEnvios() {
        return envios;
    }

    public List<Envio> buscarEnviosPorUsuario(String idUsuario) {
        return envios.stream()
                .filter(e -> e.getUsuario() != null && e.getUsuario().getIdUsuario().equals(idUsuario))
                .toList();
    }

    public List<Envio> buscarEnviosPorRepartidor(String idRepartidor) {
        return envios.stream()
                .filter(e -> e.getRepartidor() != null && e.getRepartidor().getIdRepartidor().equals(idRepartidor))
                .toList();
    }

    public void asignarRepartidorAEnvio(String idEnvio, String idRepartidor) throws PlataformaLogisticaException {
        Envio envio = buscarEnvioPorId(idEnvio);
        Repartidor repartidor = buscarRepartidorPorId(idRepartidor);

        if (envio == null) {
            throw new PlataformaLogisticaException("Envío no encontrado");
        }
        if (repartidor == null) {
            throw new PlataformaLogisticaException("Repartidor no encontrado");
        }

        envio.setRepartidor(repartidor);
        repartidor.agregarEnvio(envio);
        envio.setEstado(EstadoEnvio.ASIGNADO);
    }

    public void actualizarEstadoEnvio(String idEnvio, EstadoEnvio nuevoEstado) throws PlataformaLogisticaException {
        Envio envio = buscarEnvioPorId(idEnvio);
        if (envio == null) {
            throw new PlataformaLogisticaException("Envío no encontrado");
        }
        envio.setEstado(nuevoEstado);
        if (nuevoEstado == EstadoEnvio.ENTREGADO) {
            envio.setFechaEntregaReal(java.time.LocalDateTime.now());
        }
    }
    public void eliminarEnvio(Envio envio) throws PlataformaLogisticaException {
        Usuario usuario = envio.getUsuario();
        Repartidor repartidor = envio.getRepartidor();
        if (repartidor == null) {
            throw new PlataformaLogisticaException("Repartidor no encontrado");
        }
        repartidor.removerEnvio(envio);
        usuario.eliminarEnvio(envio);
        envios.remove(envio);
    }

    // Métodos para tarifas
    public Tarifa getTarifa() {
        return tarifa;
    }

    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    // Métodos de búsqueda y filtrado
    public List<Envio> filtrarEnviosPorEstado(EstadoEnvio estado) {
        return envios.stream()
                .filter(e -> e.getEstado() == estado)
                .toList();
    }

    public byte getTotalIncidencias(){
        byte total = 0;
        for (Envio envio : envios) {
            total += envio.getIncidencias().size();
        }
        return total;
    }
}
