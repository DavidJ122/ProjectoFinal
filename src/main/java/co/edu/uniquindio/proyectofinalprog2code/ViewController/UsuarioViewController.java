package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Controller.ServicioUsuarios;
import co.edu.uniquindio.proyectofinalprog2code.Model.Usuario;
import co.edu.uniquindio.proyectofinalprog2code.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioViewController implements Initializable {
    
    @FXML
    private VBox contenidoPrincipal;
    
    @FXML
    private Label lblBienvenido;
    
    private ServicioUsuarios servicioUsuarios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicioUsuarios = new ServicioUsuarios();
        
        Usuario usuario = Sesion.getInstancia().getUsuarioActual();
        if (usuario != null && lblBienvenido != null) {
            lblBienvenido.setText("Bienvenido, " + usuario.getNombreCompleto());
        }
    }
    
    @FXML
    private void mostrarNuevoEnvio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/NuevoEnvioView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Nuevo Envío");
            stage.setScene(new Scene(root, 700, 600));
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cargar la vista de nuevo envío", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    @FXML
    private void mostrarMisEnvios() {
        cargarVistaEnTabla("Mis Envíos");
    }
    
    @FXML
    private void mostrarCotizacion() {
        cargarVistaEnContenido("/co/edu/uniquindio/proyectofinalprog2code/CotizacionView.fxml");
    }
    
    @FXML
    private void mostrarHistorial() {
        cargarVistaEnTabla("Historial de Envíos");
    }
    
    @FXML
    private void mostrarPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/PerfilView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Mi Perfil");
            stage.setScene(new Scene(root, 600, 500));
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cargar la vista de perfil", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void cerrarSesion() {
        Sesion.getInstancia().cerrarSesion();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) contenidoPrincipal.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
            stage.setTitle("Plataforma Logística - Login");
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cerrar sesión", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarVistaEnContenido(String rutaVista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaVista));
            Parent root = loader.load();
            contenidoPrincipal.getChildren().clear();
            contenidoPrincipal.getChildren().add(root);
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cargar la vista", Alert.AlertType.ERROR);
        }
    }
    
    private void cargarVistaEnTabla(String titulo) {
        // Implementación simplificada
        contenidoPrincipal.getChildren().clear();
        Label label = new Label(titulo);
        contenidoPrincipal.getChildren().add(label);
    }
    
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
