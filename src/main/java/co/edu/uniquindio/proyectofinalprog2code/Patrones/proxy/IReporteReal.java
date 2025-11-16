package co.edu.uniquindio.proyectofinalprog2code.Patrones.proxy;

import co.edu.uniquindio.proyectofinalprog2code.dto.ReporteDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IReporteReal {


    List<ReporteDTO> generarReporte(String tipoReporte, LocalDateTime fechaInicio,
                                    LocalDateTime fechaFin) throws Exception;
}
