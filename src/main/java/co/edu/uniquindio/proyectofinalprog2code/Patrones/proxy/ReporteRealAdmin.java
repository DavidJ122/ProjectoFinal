package co.edu.uniquindio.proyectofinalprog2code.Patrones.proxy;

import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;
import co.edu.uniquindio.proyectofinalprog2code.dto.ReporteDTO;
import co.edu.uniquindio.proyectofinalprog2code.Model.Empresa;
import co.edu.uniquindio.proyectofinalprog2code.Model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReporteRealAdmin implements IReporteReal{
    private final Empresa empresa;

    public ReporteRealAdmin() {
        this.empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();
    }
    @Override
    public List<ReporteDTO> generarReporte(String tipoReporte, LocalDateTime fechaInicio,
                                           LocalDateTime fechaFin) {
        List<Envio> envios = empresa.getEnvios().stream()
                .filter(e -> e.getFechaCreacion().isAfter(fechaInicio)
                        && e.getFechaCreacion().isBefore(fechaFin))
                .collect(Collectors.toList());

        List<ReporteDTO> reportes = new ArrayList<>();

        switch (tipoReporte.toUpperCase()) {
            case "ENVIOS":
                reportes = generarReporteEnvios(envios);
                break;
            case "INGRESOS":
                reportes = generarReporteIngresos(envios);
                break;
            case "ESTADOS":
                reportes = generarReporteEstados(envios);
                break;
            default:
                reportes = generarReporteEnvios(envios);
        }

        return reportes;
    }

    private List<ReporteDTO> generarReporteEnvios(List<Envio> envios) {
        List<ReporteDTO> reportes = new ArrayList<>();
        for (Envio envio : envios) {
            ReporteDTO dto = new ReporteDTO();
            dto.setTipo("ENVIO");
            dto.setId(envio.getIdEnvio());
            dto.setFecha(envio.getFechaCreacion());
            dto.setEstado(envio.getEstado().name());
            dto.setMonto(envio.getCosto());
            reportes.add(dto);
        }
        return reportes;
    }

    private List<ReporteDTO> generarReporteIngresos(List<Envio> envios) {
        List<ReporteDTO> reportes = new ArrayList<>();
        double total = envios.stream()
                .filter(e -> e.getPago() != null && e.getPago().esAprobado())
                .mapToDouble(Envio::getCosto)
                .sum();

        ReporteDTO dto = new ReporteDTO();
        dto.setTipo("INGRESOS");
        dto.setId("TOTAL");
        dto.setFecha(LocalDateTime.now());
        dto.setEstado("TOTAL");
        dto.setMonto(total);
        reportes.add(dto);

        return reportes;
    }

    private List<ReporteDTO> generarReporteEstados(List<Envio> envios) {
        List<ReporteDTO> reportes = new ArrayList<>();
        for (EstadoEnvio estado : EstadoEnvio.values()) {
            long cantidad = envios.stream()
                    .filter(e -> e.getEstado() == estado)
                    .count();

            ReporteDTO dto = new ReporteDTO();
            dto.setTipo("ESTADO");
            dto.setId(estado.name());
            dto.setFecha(LocalDateTime.now());
            dto.setEstado(estado.name());
            dto.setMonto(cantidad);
            reportes.add(dto);
        }
        return reportes;
    }
}
