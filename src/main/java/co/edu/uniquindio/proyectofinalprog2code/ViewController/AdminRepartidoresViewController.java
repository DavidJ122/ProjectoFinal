package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Controller.ServicioRepartidores;
import co.edu.uniquindio.proyectofinalprog2code.Excepciones.PlataformaLogisticaException;
import co.edu.uniquindio.proyectofinalprog2code.Model.EstadoRepartidor;
import co.edu.uniquindio.proyectofinalprog2code.Model.Repartidor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminRepartidoresViewController implements Initializable {
    
    @FXML
    private TableView<Repartidor> tablaRepartidores;
    
    @FXML
    private TableColumn<Repartidor, String> colId;
    
    @FXML
    private TableColumn<Repartidor, String> colNombre;
    
    @FXML
    private TableColumn<Repartidor, String> colDocumento;
    
    @FXML
    private TableColumn<Repartidor, String> colTelefono;
    
    @FXML
    private TableColumn<Repartidor, EstadoRepartidor> colEstado;
    
    @FXML
    private TableColumn<Repartidor, String> colZona;
    
    private ServicioRepartidores servicioRepartidores;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicioRepartidores = new ServicioRepartidores();
        
        colId.setCellValueFactory(new PropertyValueFactory<>("idRepartidor"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colZona.setCellValueFactory(new PropertyValueFactory<>("zonaCobertura"));
        
        cargarRepartidores();
    }
    
    private void cargarRepartidores() {
        tablaRepartidores.getItems().clear();
        tablaRepartidores.getItems().addAll(servicioRepartidores.obtenerTodosLosRepartidores());
    }
    
    @FXML
    private void agregarRepartidor() {
        Dialog<Repartidor> dialog = crearDialogoRepartidor(null);
        Optional<Repartidor> result = dialog.showAndWait();
        result.ifPresent(repartidor -> {
            servicioRepartidores.crearRepartidor(
                repartidor.getIdRepartidor(),
                repartidor.getNombre(),
                repartidor.getDocumento(),
                repartidor.getTelefono(),
                repartidor.getZonaCobertura()
            );
            cargarRepartidores();
            mostrarMensaje("Éxito", "Repartidor creado correctamente", Alert.AlertType.INFORMATION);
        });
    }
    
    @FXML
    private void editarRepartidor() {
        Repartidor seleccionado = tablaRepartidores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Dialog<Repartidor> dialog = crearDialogoRepartidor(seleccionado);
            Optional<Repartidor> result = dialog.showAndWait();
            result.ifPresent(repartidor -> {
                servicioRepartidores.actualizarRepartidor(repartidor);
                cargarRepartidores();
                mostrarMensaje("Éxito", "Repartidor actualizado", Alert.AlertType.INFORMATION);
            });
        } else {
            mostrarMensaje("Advertencia", "Seleccione un repartidor", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void cambiarEstado() {
        Repartidor seleccionado = tablaRepartidores.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Dialog<EstadoRepartidor> dialog = new Dialog<>();
            dialog.setTitle("Cambiar Estado");
            
            ComboBox<EstadoRepartidor> combo = new ComboBox<>();
            combo.getItems().addAll(EstadoRepartidor.values());
            combo.setValue(seleccionado.getEstado());
            
            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(new Label("Nuevo Estado:"), combo);
            vbox.setPadding(new javafx.geometry.Insets(20));
            dialog.getDialogPane().setContent(vbox);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            
            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? combo.getValue() : null);
            
            Optional<EstadoRepartidor> result = dialog.showAndWait();
            result.ifPresent(estado -> {
                servicioRepartidores.cambiarDisponibilidad(seleccionado.getIdRepartidor(), estado);
                cargarRepartidores();
                mostrarMensaje("Éxito", "Estado actualizado", Alert.AlertType.INFORMATION);
            });
        } else {
            mostrarMensaje("Advertencia", "Seleccione un repartidor", Alert.AlertType.WARNING);
        }
    }
    
    private Dialog<Repartidor> crearDialogoRepartidor(Repartidor repartidor) {
        Dialog<Repartidor> dialog = new Dialog<>();
        dialog.setTitle(repartidor == null ? "Agregar Repartidor" : "Editar Repartidor");
        
        TextField txtNombre = new TextField();
        TextField txtDocumento = new TextField();
        TextField txtTelefono = new TextField();
        TextField txtZona = new TextField();
        
        if (repartidor != null) {
            txtNombre.setText(repartidor.getNombre());
            txtDocumento.setText(repartidor.getDocumento());
            txtTelefono.setText(repartidor.getTelefono());
            txtZona.setText(repartidor.getZonaCobertura());
        }
        
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(
            new Label("Nombre:"), txtNombre,
            new Label("Documento:"), txtDocumento,
            new Label("Teléfono:"), txtTelefono,
            new Label("Zona:"), txtZona
        );
        vbox.setPadding(new javafx.geometry.Insets(20));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String id = repartidor != null ? repartidor.getIdRepartidor() : "REP-" + System.currentTimeMillis();
                return new Repartidor(id, txtNombre.getText(), txtDocumento.getText(), txtTelefono.getText(), txtZona.getText());
            }
            return null;
        });
        
        return dialog;
    }
    
    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

