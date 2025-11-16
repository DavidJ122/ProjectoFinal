package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Model.*;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class AdminMetricasViewController implements Initializable {
    
    @FXML
    private Label lblTotalEnvios;
    
    @FXML
    private Label lblEnviosEntregados;
    
    @FXML
    private Label lblEnviosEnRuta;
    
    @FXML
    private Label lblEnviosSolicitados;
    
    @FXML
    private Label lblIngresosTotales;
    
    @FXML
    private Label lblPromedioEnvio;
    
    @FXML
    private Label lblTotalRepartidores;
    
    @FXML
    private Label lblActivos;
    
    @FXML
    private Label lblInactivos;
    
    @FXML
    private Label lblEnRuta;
    
    @FXML
    private Label lblSolicitados;
    
    @FXML
    private Label lblAsignados;
    
    @FXML
    private Label lblEnRutaE;
    
    @FXML
    private Label lblEntregados;
    
    @FXML
    private Label lblIncidencias;
    
    private DecimalFormat formato = new DecimalFormat("#,###.00");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarMetricas();
    }
    
    @FXML
    private void actualizarMetricas() {
        Empresa empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();
        
        // Estadísticas de envíos
        long totalEnvios = empresa.getEnvios().size();
        long enviosEntregados = empresa.filtrarEnviosPorEstado(EstadoEnvio.ENTREGADO).size();
        long enviosEnRuta = empresa.filtrarEnviosPorEstado(EstadoEnvio.EN_RUTA).size();
        long enviosSolicitados = empresa.filtrarEnviosPorEstado(EstadoEnvio.SOLICITADO).size();
        long enviosAsignados = empresa.filtrarEnviosPorEstado(EstadoEnvio.ASIGNADO).size();
        long enviosIncidencias = empresa.filtrarEnviosPorEstado(EstadoEnvio.INCIDENCIA).size();
        
        lblTotalEnvios.setText("Total Envíos: " + totalEnvios);
        lblEnviosEntregados.setText("Envíos Entregados: " + enviosEntregados);
        lblEnviosEnRuta.setText("En Ruta: " + enviosEnRuta);
        lblEnviosSolicitados.setText("Solicitados: " + enviosSolicitados);
        lblSolicitados.setText("Solicitados: " + enviosSolicitados);
        lblAsignados.setText("Asignados: " + enviosAsignados);
        lblEnRutaE.setText("En Ruta: " + enviosEnRuta);
        lblEntregados.setText("Entregados: " + enviosEntregados);
        lblIncidencias.setText("Incidencias: " + enviosIncidencias);
        
        // Ingresos
        double ingresosTotales = empresa.getEnvios().stream()
                .filter(e -> e.getPago() != null && e.getPago().esAprobado())
                .mapToDouble(Envio::getCosto)
                .sum();
        
        double promedioEnvio = totalEnvios > 0 ? ingresosTotales / totalEnvios : 0;
        
        lblIngresosTotales.setText("Ingresos Totales: $" + formato.format(ingresosTotales));
        lblPromedioEnvio.setText("Promedio por Envío: $" + formato.format(promedioEnvio));
        
        // Estadísticas de repartidores
        long totalRepartidores = empresa.getRepartidores().size();
        long activos = empresa.getRepartidores().stream()
                .filter(r -> r.getEstado() == EstadoRepartidor.ACTIVO).count();
        long inactivos = empresa.getRepartidores().stream()
                .filter(r -> r.getEstado() == EstadoRepartidor.INACTIVO).count();
        long enRuta = empresa.getRepartidores().stream()
                .filter(r -> r.getEstado() == EstadoRepartidor.EN_RUTA).count();
        
        lblTotalRepartidores.setText("Total: " + totalRepartidores);
        lblActivos.setText("Activos: " + activos);
        lblInactivos.setText("Inactivos: " + inactivos);
        lblEnRuta.setText("En Ruta: " + enRuta);
    }
}

