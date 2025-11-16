package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Controller.ServicioUsuarios;
import co.edu.uniquindio.proyectofinalprog2code.Excepciones.PlataformaLogisticaException;
import co.edu.uniquindio.proyectofinalprog2code.Model.Empresa;
import co.edu.uniquindio.proyectofinalprog2code.Model.TipoUsuario;
import co.edu.uniquindio.proyectofinalprog2code.Model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminUsuariosViewController implements Initializable {
    
    @FXML
    private TableView<Usuario> tablaUsuarios;
    
    @FXML
    private TableColumn<Usuario, String> colId;
    
    @FXML
    private TableColumn<Usuario, String> colNombre;
    
    @FXML
    private TableColumn<Usuario, String> colCorreo;
    
    @FXML
    private TableColumn<Usuario, String> colTelefono;
    
    @FXML
    private TableColumn<Usuario, TipoUsuario> colTipo;
    
    private ServicioUsuarios servicioUsuarios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicioUsuarios = new ServicioUsuarios();
        
        colId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));
        
        cargarUsuarios();
    }
    
    private void cargarUsuarios() {
        tablaUsuarios.getItems().clear();
        tablaUsuarios.getItems().addAll(servicioUsuarios.obtenerTodosLosUsuarios());
    }
    
    @FXML
    private void agregarUsuario() {
        Dialog<Usuario> dialog = crearDialogoUsuario(null);
        Optional<Usuario> result = dialog.showAndWait();
        result.ifPresent(usuario -> {
            try {
                servicioUsuarios.registrarUsuario(
                    usuario.getIdUsuario(),
                    usuario.getNombreCompleto(),
                    usuario.getCorreo(),
                    usuario.getTelefono(),
                    "password123"
                );
                cargarUsuarios();
                mostrarMensaje("Éxito", "Usuario creado correctamente", Alert.AlertType.INFORMATION);
            } catch (PlataformaLogisticaException e) {
                mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }
    
    @FXML
    private void editarUsuario() {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Dialog<Usuario> dialog = crearDialogoUsuario(seleccionado);
            Optional<Usuario> result = dialog.showAndWait();
            result.ifPresent(usuario -> {
                try {
                    servicioUsuarios.actualizarUsuario(usuario);
                    cargarUsuarios();
                    mostrarMensaje("Éxito", "Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
                } catch (PlataformaLogisticaException e) {
                    mostrarMensaje("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            });
        } else {
            mostrarMensaje("Advertencia", "Seleccione un usuario para editar", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void eliminarUsuario() throws PlataformaLogisticaException {
        Usuario seleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            servicioUsuarios.eliminarUsuario(seleccionado);
            cargarUsuarios();
            mostrarMensaje("Info", "Usuario Eliminado", Alert.AlertType.INFORMATION);
        } else {
            mostrarMensaje("Advertencia", "Seleccione un usuario para eliminar", Alert.AlertType.WARNING);
        }
    }
    
    private Dialog<Usuario> crearDialogoUsuario(Usuario usuario) {
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setTitle(usuario == null ? "Agregar Usuario" : "Editar Usuario");
        
        TextField txtNombre = new TextField();
        TextField txtCorreo = new TextField();
        TextField txtTelefono = new TextField();
        ComboBox<TipoUsuario> combo = new ComboBox<>();
        combo.getItems().addAll(TipoUsuario.values());
        
        if (usuario != null) {
            txtNombre.setText(usuario.getNombreCompleto());
            txtCorreo.setText(usuario.getCorreo());
            txtTelefono.setText(usuario.getTelefono());
            combo.setValue(usuario.getTipoUsuario());
        } else {
            combo.setValue(TipoUsuario.USUARIO);
        }
        
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(
            new Label("Nombre:"), txtNombre,
            new Label("Correo:"), txtCorreo,
            new Label("Teléfono:"), txtTelefono,
            new Label("Tipo:"), combo
        );
        vbox.setPadding(new javafx.geometry.Insets(20));
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String id = usuario != null ? usuario.getIdUsuario() : "USU-" + System.currentTimeMillis();
                return new Usuario(id, txtNombre.getText(), txtCorreo.getText(), txtTelefono.getText(), "");
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

