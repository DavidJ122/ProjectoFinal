package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Controller.ServicioCotizacion;
import co.edu.uniquindio.proyectofinalprog2code.dto.CotizacionDTO;
import co.edu.uniquindio.proyectofinalprog2code.Model.Direccion;
import co.edu.uniquindio.proyectofinalprog2code.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class CotizacionViewController implements Initializable {
    
    @FXML
    private ComboBox<Direccion> comboOrigen;
    
    @FXML
    private ComboBox<Direccion> comboDestino;
    
    @FXML
    private TextField txtPeso;
    
    @FXML
    private TextField txtVolumen;
    
    @FXML
    private CheckBox checkPrioritario;
    
    @FXML
    private Button btnCalcular;
    
    @FXML
    private VBox panelResultado;
    
    @FXML
    private Label lblDistancia;
    
    @FXML
    private Label lblCostoBase;
    
    @FXML
    private Label lblCostoPeso;
    
    @FXML
    private Label lblCostoVolumen;
    
    @FXML
    private Label lblCostoPrioridad;
    
    @FXML
    private Label lblCostoTotal;
    
    private ServicioCotizacion servicioCotizacion;
    private DecimalFormat formato = new DecimalFormat("#,###.00");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicioCotizacion = new ServicioCotizacion();
        
        // Cargar direcciones del usuario
        if (Sesion.getInstancia().getUsuarioActual() != null) {
            comboOrigen.getItems().addAll(Sesion.getInstancia().getUsuarioActual().getDireccionesFrecuentes());
            comboDestino.getItems().addAll(Sesion.getInstancia().getUsuarioActual().getDireccionesFrecuentes());
        }
    }
    
    @FXML
    private void calcularCotizacion() {
        try {
            Direccion origen = comboOrigen.getValue();
            Direccion destino = comboDestino.getValue();
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());
            boolean esPrioritario = checkPrioritario.isSelected();
            
            if (origen == null || destino == null) {
                mostrarMensaje("Error", "Debe seleccionar origen y destino", Alert.AlertType.ERROR);
                return;
            }
            
            CotizacionDTO cotizacion = servicioCotizacion.cotizarEnvio(origen, destino, peso, volumen, esPrioritario);
            
            // Mostrar resultados
            lblDistancia.setText(formato.format(cotizacion.getDistancia()) + " km");
            lblCostoBase.setText("$" + formato.format(cotizacion.getCostoBase()));
            lblCostoPeso.setText("$" + formato.format(cotizacion.getCostoPeso()));
            lblCostoVolumen.setText("$" + formato.format(cotizacion.getCostoVolumen()));
            lblCostoPrioridad.setText("$" + formato.format(cotizacion.getCostoPrioridad()));
            lblCostoTotal.setText("$" + formato.format(cotizacion.getCostoTotal()));
            
            panelResultado.setVisible(true);
            
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Por favor ingrese valores numéricos válidos", Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error", "Error al calcular cotización: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void limpiar() {
        comboOrigen.setValue(null);
        comboDestino.setValue(null);
        txtPeso.clear();
        txtVolumen.clear();
        checkPrioritario.setSelected(false);
        panelResultado.setVisible(false);
    }
    
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

