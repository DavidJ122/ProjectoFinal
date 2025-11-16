package co.edu.uniquindio.proyectofinalprog2code.ViewController;

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

public class AdminViewController implements Initializable {
    
    @FXML
    private VBox contenidoPrincipal;
    
    @FXML
    private Label lblBienvenido;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (Sesion.getInstancia().getUsuarioActual() != null && lblBienvenido != null) {
            lblBienvenido.setText("Panel de Administración - " + Sesion.getInstancia().getUsuarioActual().getNombreCompleto());
        }
    }
    
    @FXML
    private void mostrarUsuarios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/AdminUsuariosView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestión de Usuarios");
            stage.setScene(new Scene(root, 900, 700));
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cargar la vista de usuarios", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void mostrarRepartidores() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/AdminRepartidoresView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestión de Repartidores");
            stage.setScene(new Scene(root, 900, 700));
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cargar la vista de repartidores", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void mostrarEnvios() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/AdminEnviosView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Gestión de Envíos");
            stage.setScene(new Scene(root, 1200, 800));
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cargar la vista de envíos", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void mostrarMetricas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/AdminMetricasView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Panel de Métricas");
            stage.setScene(new Scene(root, 1000, 800));
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cargar la vista de métricas", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void mostrarReportes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/AdminReportesView.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Generación de Reportes");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al cargar la vista de reportes", Alert.AlertType.ERROR);
            e.printStackTrace();
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
    
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
