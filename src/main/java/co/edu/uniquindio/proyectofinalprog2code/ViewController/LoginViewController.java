package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Controller.ServicioUsuarios;
import co.edu.uniquindio.proyectofinalprog2code.Excepciones.PlataformaLogisticaException;
import co.edu.uniquindio.proyectofinalprog2code.Model.TipoUsuario;
import co.edu.uniquindio.proyectofinalprog2code.Model.Usuario;
import co.edu.uniquindio.proyectofinalprog2code.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private PasswordField txtContrasena;
    
    @FXML
    private Label lblMensaje;
    
    @FXML
    private Button btnLogin;
    
    @FXML
    private Hyperlink linkRegistro;
    
    private ServicioUsuarios servicioUsuarios;
    
    @FXML
    public void initialize() {
        servicioUsuarios = new ServicioUsuarios();
    }
    
    @FXML
    private void iniciarSesion() {
        try {
            String correo = txtCorreo.getText();
            String contrasena = txtContrasena.getText();
            
            if (correo.isEmpty() || contrasena.isEmpty()) {
                mostrarMensaje("Error", "Por favor complete todos los campos", Alert.AlertType.ERROR);
                return;
            }
            
            Usuario usuario = servicioUsuarios.autenticarUsuario(correo, contrasena);
            
            if (usuario != null) {
                Sesion.getInstancia().setUsuarioActual(usuario);
                
                // Determinar qué vista cargar según el tipo de usuario
                String vista = usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR 
                        ? "AdminView.fxml" : "UsuarioView.fxml";
                
                cargarVista(vista);
            }
            
        } catch (PlataformaLogisticaException e) {
            mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void irARegistro() {
        cargarVista("RegistroView.fxml");
    }
    
    private void cargarVista(String nombreVista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/" + nombreVista));
            Parent root = loader.load();
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Plataforma Logística");
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


