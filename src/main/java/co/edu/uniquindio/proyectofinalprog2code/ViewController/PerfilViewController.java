package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Model.Direccion;
import co.edu.uniquindio.proyectofinalprog2code.Model.MetodoPago;
import co.edu.uniquindio.proyectofinalprog2code.Model.TipoPago;
import co.edu.uniquindio.proyectofinalprog2code.Model.Usuario;
import co.edu.uniquindio.proyectofinalprog2code.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class PerfilViewController implements Initializable {
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtCorreo;
    
    @FXML
    private TextField txtTelefono;
    
    @FXML
    private TableView<Direccion> tablaDirecciones;
    
    @FXML
    private TableColumn<Direccion, String> colAlias;
    
    @FXML
    private TableColumn<Direccion, String> colCalle;
    
    @FXML
    private TableColumn<Direccion, String> colCiudad;
    
    @FXML
    private TableView<MetodoPago> tablaMetodosPago;
    
    @FXML
    private TableColumn<MetodoPago, String> colTipo;
    
    @FXML
    private TableColumn<MetodoPago, String> colNumero;
    
    @FXML
    private TableColumn<MetodoPago, String> colTitular;
    
    private Usuario usuarioActual;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioActual = Sesion.getInstancia().getUsuarioActual();
        
        if (usuarioActual != null) {
            txtNombre.setText(usuarioActual.getNombreCompleto());
            txtCorreo.setText(usuarioActual.getCorreo());
            txtTelefono.setText(usuarioActual.getTelefono());
            
            // Configurar tablas
            colAlias.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAlias()));
            colCalle.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCalle()));
            colCiudad.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCiudad()));
            
            colTipo.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipoPago().name()));
            colNumero.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNumeroOculto()));
            colTitular.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitular()));
            
            // Cargar datos
            tablaDirecciones.getItems().clear();
            if (usuarioActual.getDireccionesFrecuentes() != null) {
                tablaDirecciones.getItems().addAll(usuarioActual.getDireccionesFrecuentes());
            }

            tablaMetodosPago.getItems().clear();
            if (usuarioActual.getMetodosPago() != null) {
                tablaMetodosPago.getItems().addAll(usuarioActual.getMetodosPago());
            }
        }
    }
    
    @FXML
    private void agregarDireccion() {
        Dialog<Direccion> dialog = crearDialogoDireccion(null);
        Optional<Direccion> result = dialog.showAndWait();
        result.ifPresent(direccion -> {
            usuarioActual.agregarDireccion(direccion);
            tablaDirecciones.getItems().add(direccion);
        });
    }
    
    @FXML
    private void eliminarDireccion() {
        Direccion seleccionada = tablaDirecciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            usuarioActual.eliminarDireccion(seleccionada);
            tablaDirecciones.getItems().remove(seleccionada);
        } else {
            mostrarMensaje("Advertencia", "Seleccione una dirección para eliminar", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void agregarMetodoPago() {
        Dialog<MetodoPago> dialog = crearDialogoMetodoPago(null);
        Optional<MetodoPago> result = dialog.showAndWait();
        result.ifPresent(metodo -> {
            usuarioActual.agregarMetodoPago(metodo);
            tablaMetodosPago.getItems().add(metodo);
        });
    }
    
    @FXML
    private void eliminarMetodoPago() {
        MetodoPago seleccionado = tablaMetodosPago.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            usuarioActual.eliminarMetodoPago(seleccionado);
            tablaMetodosPago.getItems().remove(seleccionado);
        } else {
            mostrarMensaje("Advertencia", "Seleccione un método de pago para eliminar", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void guardarCambios() {
        if (usuarioActual != null) {
            usuarioActual.setNombreCompleto(txtNombre.getText());
            usuarioActual.setCorreo(txtCorreo.getText());
            usuarioActual.setTelefono(txtTelefono.getText());
            
            mostrarMensaje("Éxito", "Cambios guardados correctamente", Alert.AlertType.INFORMATION);
            ((Stage) txtNombre.getScene().getWindow()).close();
        }
    }
    
    @FXML
    private void cancelar() {
        ((Stage) txtNombre.getScene().getWindow()).close();
    }
    
    private Dialog<Direccion> crearDialogoDireccion(Direccion direccion) {
        Dialog<Direccion> dialog = new Dialog<>();
        dialog.setTitle("Agregar Dirección");
        dialog.setHeaderText("Ingrese los datos de la dirección");
        
        TextField txtAlias = new TextField();
        TextField txtCalle = new TextField();
        TextField txtCiudad = new TextField();
        
        dialog.getDialogPane().setContent(crearFormularioDireccion(txtAlias, txtCalle, txtCiudad));
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String id = "DIR-" + System.currentTimeMillis();
                return new Direccion(id, txtAlias.getText(), txtCalle.getText(), txtCiudad.getText(), 0, 0);
            }
            return null;
        });
        
        return dialog;
    }
    
    private VBox crearFormularioDireccion(TextField alias, TextField calle, TextField ciudad) {
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(
            new Label("Alias:"), alias,
            new Label("Calle:"), calle,
            new Label("Ciudad:"), ciudad
        );
        vbox.setPadding(new javafx.geometry.Insets(20));
        return vbox;
    }
    
    private Dialog<MetodoPago> crearDialogoMetodoPago(MetodoPago metodo) {
        Dialog<MetodoPago> dialog = new Dialog<>();
        dialog.setTitle("Agregar Método de Pago");
        dialog.setHeaderText("Ingrese los datos del método de pago");
        
        ComboBox<TipoPago> combo = new ComboBox<>();
        combo.getItems().addAll(TipoPago.values());
        combo.setValue(TipoPago.TARJETA_CREDITO);
        
        TextField txtNumero = new TextField();
        TextField txtTitular = new TextField();
        
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(
            new Label("Tipo:"), combo,
            new Label("Número:"), txtNumero,
            new Label("Titular:"), txtTitular
        );
        vbox.setPadding(new javafx.geometry.Insets(20));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String id = "MP-" + System.currentTimeMillis();
                return new MetodoPago(id, txtNumero.getText(), txtTitular.getText(), combo.getValue());
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

