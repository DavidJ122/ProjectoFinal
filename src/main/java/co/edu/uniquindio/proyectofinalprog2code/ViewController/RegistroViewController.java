package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Controller.ServicioUsuarios;
import co.edu.uniquindio.proyectofinalprog2code.Excepciones.PlataformaLogisticaException;
import co.edu.uniquindio.proyectofinalprog2code.Model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroViewController {
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private PasswordField txtContrasena;
    
    @FXML
    private PasswordField txtConfirmarContrasena;
    
    @FXML
    private Button btnRegistrar;
    
    @FXML
    private Label lblMensaje;
    
    private ServicioUsuarios servicioUsuarios;
    
    @FXML
    public void initialize() {
        servicioUsuarios = new ServicioUsuarios();
    }
    
    @FXML
    private void registrar() {
        try {
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String telefono = txtTelefono.getText();
            String contrasena = txtContrasena.getText();
            String confirmarContrasena = txtConfirmarContrasena.getText();
            
            if (nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty() || 
                contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
                mostrarMensaje("Error", "Por favor complete todos los campos", Alert.AlertType.ERROR);
                return;
            }
            
            if (!contrasena.equals(confirmarContrasena)) {
                mostrarMensaje("Error", "Las contraseñas no coinciden", Alert.AlertType.ERROR);
                return;
            }
            
            String idUsuario = "USU-" + System.currentTimeMillis();
            Usuario usuario = servicioUsuarios.registrarUsuario(idUsuario, nombre, correo, telefono, contrasena);
            
            if (usuario != null) {
                mostrarMensaje("Éxito", "Usuario registrado correctamente", Alert.AlertType.INFORMATION);
                volverAlLogin();
            }
            
        } catch (PlataformaLogisticaException e) {
            mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void volverAlLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnRegistrar.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cargar la vista: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

