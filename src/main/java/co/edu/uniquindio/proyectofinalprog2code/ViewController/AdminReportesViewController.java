package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Controller.GeneradorReportes;
import co.edu.uniquindio.proyectofinalprog2code.dto.ReporteDTO;
import co.edu.uniquindio.proyectofinalprog2code.Model.Usuario;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.proxy.ReporteProxy;
import co.edu.uniquindio.proyectofinalprog2code.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class AdminReportesViewController implements Initializable {
    
    @FXML
    private ComboBox<String> comboTipoReporte;
    
    @FXML
    private DatePicker dateDesde;
    
    @FXML
    private DatePicker dateHasta;
    
    @FXML
    private RadioButton radioCSV;
    
    @FXML
    private RadioButton radioPDF;
    
    @FXML
    private Label lblMensaje;
    
    private GeneradorReportes generadorReportes;
    private Usuario usuario;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generadorReportes = new GeneradorReportes();
        usuario = Sesion.getInstancia().getUsuarioActual();
        
        // Configurar fechas por defecto
        dateDesde.setValue(LocalDate.now().minusMonths(1));
        dateHasta.setValue(LocalDate.now());
    }
    
    @FXML
    private void generarReporte() {
        try {
            String tipoReporte = comboTipoReporte.getValue();
            LocalDate fechaDesde = dateDesde.getValue();
            LocalDate fechaHasta = dateHasta.getValue();
            boolean esCSV = radioCSV.isSelected();
            
            if (tipoReporte == null || fechaDesde == null || fechaHasta == null) {
                mostrarMensaje("Error", "Complete todos los campos", Alert.AlertType.ERROR);
                return;
            }
            
            if (fechaDesde.isAfter(fechaHasta)) {
                mostrarMensaje("Error", "La fecha desde debe ser anterior a la fecha hasta", Alert.AlertType.ERROR);
                return;
            }
            
            // Convertir LocalDate a LocalDateTime
            LocalDateTime inicio = fechaDesde.atStartOfDay();
            LocalDateTime fin = fechaHasta.atTime(23, 59, 59);
            
            // Generar reporte usando Proxy
            ReporteProxy proxy = new ReporteProxy(usuario);
            java.util.List<ReporteDTO> reportes = proxy.generarReporte(tipoReporte, inicio, fin);
            
            // Guardar archivo
            String extension = esCSV ? "csv" : "pdf";
            String ruta = generadorReportes.generarRutaReporte(extension);
            
            if (esCSV) {
                generadorReportes.generarReporteCSV(reportes, ruta);
            } else {
                generadorReportes.generarReportePDF(reportes, ruta);
            }
            
            mostrarMensaje("Éxito", "Reporte generado correctamente en:\n" + ruta, Alert.AlertType.INFORMATION);
            
        } catch (SecurityException e) {
            mostrarMensaje("Error de Permisos", "No tiene permisos para generar reportes", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error", "Error al generar reporte: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    @FXML
    private void cancelar() {
        ((Stage) lblMensaje.getScene().getWindow()).close();
    }
    
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

