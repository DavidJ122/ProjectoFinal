package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Controller.ServicioCotizacion;
import co.edu.uniquindio.proyectofinalprog2code.Controller.ServicioEnvios;
import co.edu.uniquindio.proyectofinalprog2code.dto.CotizacionDTO;
import co.edu.uniquindio.proyectofinalprog2code.Excepciones.PlataformaLogisticaException;
import co.edu.uniquindio.proyectofinalprog2code.Model.*;
import co.edu.uniquindio.proyectofinalprog2code.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NuevoEnvioViewController implements Initializable {
    
    @FXML
    private ComboBox<Direccion> comboOrigen;
    
    @FXML
    private ComboBox<Direccion> comboDestino;
    
    @FXML
    private TextField txtPeso;
    
    @FXML
    private TextField txtVolumen;
    
    @FXML
    private CheckBox checkSeguro;
    
    @FXML
    private CheckBox checkPrioritario;
    
    @FXML
    private CheckBox checkFragil;
    
    @FXML
    private CheckBox checkFirma;
    
    @FXML
    private Label lblCostoEstimado;
    
    @FXML
    private Button btnCrearEnvio;
    
    @FXML
    private Label lblMensaje;
    
    private ServicioEnvios servicioEnvios;
    private ServicioCotizacion servicioCotizacion;
    private DecimalFormat formato = new DecimalFormat("#,###.00");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicioEnvios = new ServicioEnvios();
        servicioCotizacion = new ServicioCotizacion();

        Usuario currentUser = Sesion.getInstancia().getUsuarioActual();
        // Ahora SIEMPRE se habilita la vista; sólo muestra advertencia si están vacíos
        if (currentUser != null && currentUser.getDireccionesFrecuentes() != null) {
            comboOrigen.getItems().addAll(currentUser.getDireccionesFrecuentes());
            comboDestino.getItems().addAll(currentUser.getDireccionesFrecuentes());
        }

        // Listener para costo estimado
        comboOrigen.setOnAction(e -> calcularCostoEstimado());
        comboDestino.setOnAction(e -> calcularCostoEstimado());
        txtPeso.textProperty().addListener((obs, oldVal, newVal) -> calcularCostoEstimado());
        txtVolumen.textProperty().addListener((obs, oldVal, newVal) -> calcularCostoEstimado());
        checkPrioritario.setOnAction(e -> calcularCostoEstimado());
        lblMensaje.setVisible(false);
    }
    
    private void calcularCostoEstimado() {
        try {
            if (comboOrigen.getValue() != null && comboDestino.getValue() != null &&
                txtPeso.getText() != null && !txtPeso.getText().isEmpty() &&
                txtVolumen.getText() != null && !txtVolumen.getText().isEmpty()) {
                
                double peso = Double.parseDouble(txtPeso.getText());
                double volumen = Double.parseDouble(txtVolumen.getText());
                boolean esPrioritario = checkPrioritario.isSelected();
                
                CotizacionDTO cotizacion = servicioCotizacion.cotizarEnvio(
                    comboOrigen.getValue(), 
                    comboDestino.getValue(), 
                    peso, 
                    volumen, 
                    esPrioritario
                );
                
                // Agregar servicios adicionales
                List<ServicioAdicional> servicios = new ArrayList<>();
                if (checkSeguro.isSelected()) servicios.add(ServicioAdicional.SEGURO);
                if (checkFragil.isSelected()) servicios.add(ServicioAdicional.FRAGIL);
                if (checkFirma.isSelected()) servicios.add(ServicioAdicional.FIRMA_REQUERIDA);
                
                CotizacionDTO cotizacionFinal = servicioCotizacion.cotizarConServiciosAdicionales(cotizacion, servicios);
                
                lblCostoEstimado.setText("$" + formato.format(cotizacionFinal.getCostoTotal()));
            }
        } catch (NumberFormatException e) {
            // Ignorar errores de formato mientras el usuario escribe
        }
    }
    
    @FXML
    private void crearEnvio() {
        try {
            Direccion origen = comboOrigen.getValue();
            Direccion destino = comboDestino.getValue();
            Usuario usuario = Sesion.getInstancia().getUsuarioActual();

            if (origen == null || destino == null || usuario == null) {
                mostrarMensaje("Advertencia", "Debe seleccionar origen y destino válidos, y tener sesión iniciada.", Alert.AlertType.WARNING);
                return;
            }
            double peso = Double.parseDouble(txtPeso.getText());
            double volumen = Double.parseDouble(txtVolumen.getText());

            // Crear envío
            Envio envio = servicioEnvios.crearEnvio(origen, destino, peso, volumen, usuario);

            if (checkSeguro.isSelected()) envio.agregarServicioAdicional(ServicioAdicional.SEGURO);
            if (checkPrioritario.isSelected()) envio.agregarServicioAdicional(ServicioAdicional.ENTREGA_PRIORITARIA);
            if (checkFragil.isSelected()) envio.agregarServicioAdicional(ServicioAdicional.FRAGIL);
            if (checkFirma.isSelected()) envio.agregarServicioAdicional(ServicioAdicional.FIRMA_REQUERIDA);

            mostrarMensaje("Éxito", "Envío creado correctamente. ID: " + envio.getIdEnvio(), Alert.AlertType.INFORMATION);
            ((Stage) btnCrearEnvio.getScene().getWindow()).close();
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Por favor ingrese valores numéricos válidos", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cancelar() {
        ((Stage) btnCrearEnvio.getScene().getWindow()).close();
    }
    
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

