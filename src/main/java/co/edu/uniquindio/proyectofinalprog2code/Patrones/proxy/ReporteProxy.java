package co.edu.uniquindio.proyectofinalprog2code.Patrones.proxy;

import co.edu.uniquindio.proyectofinalprog2code.dto.*;
import co.edu.uniquindio.proyectofinalprog2code.Model.TipoUsuario;
import co.edu.uniquindio.proyectofinalprog2code.Model.Usuario;

import java.time.LocalDateTime;
import java.util.List;

public class ReporteProxy {
    private ReporteReal reporteReal;
    private Usuario usuario;

    public ReporteProxy(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ReporteDTO> generarReporte(String tipoReporte, LocalDateTime fechaInicio,
                                           LocalDateTime fechaFin) throws Exception {
        // Verificar permisos
        if (!tienePermisos()) {
            throw new SecurityException("Usuario no autorizado para generar reportes");
        }

        // Crear instancia del reporte real solo si es necesario
        if (reporteReal == null) {
            reporteReal = new ReporteReal();
        }

        // Delegar al reporte real
        return reporteReal.generarReporte(tipoReporte, fechaInicio, fechaFin);
    }

    private boolean tienePermisos() {
        return usuario != null && usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR;
    }
}
