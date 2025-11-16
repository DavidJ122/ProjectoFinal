package co.edu.uniquindio.proyectofinalprog2code.ViewController;

import co.edu.uniquindio.proyectofinalprog2code.Controller.ServicioEnvios;
import co.edu.uniquindio.proyectofinalprog2code.Excepciones.PlataformaLogisticaException;
import co.edu.uniquindio.proyectofinalprog2code.Model.*;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminEnviosViewController implements Initializable {
    
    @FXML
    private TableView<Envio> tablaEnvios;
    
    @FXML
    private TableColumn<Envio, String> colId;
    
    @FXML
    private TableColumn<Envio, String> colUsuario;
    
    @FXML
    private TableColumn<Envio, String> colOrigen;
    
    @FXML
    private TableColumn<Envio, String> colDestino;
    
    @FXML
    private TableColumn<Envio, EstadoEnvio> colEstado;
    
    @FXML
    private TableColumn<Envio, String> colRepartidor;
    
    @FXML
    private TableColumn<Envio, String> colFecha;
    
    @FXML
    private TableColumn<Envio, String> colCosto;
    
    @FXML
    private ComboBox<EstadoEnvio> comboEstado;
    
    private ServicioEnvios servicioEnvios;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        servicioEnvios = new ServicioEnvios();
        
        colId.setCellValueFactory(new PropertyValueFactory<>("idEnvio"));
        colUsuario.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue().getUsuario();
            return new javafx.beans.property.SimpleStringProperty(usuario != null ? usuario.getNombreCompleto() : "");
        });
        colOrigen.setCellValueFactory(cellData -> {
            Direccion origen = cellData.getValue().getOrigen();
            return new javafx.beans.property.SimpleStringProperty(origen != null ? origen.getAlias() : "");
        });
        colDestino.setCellValueFactory(cellData -> {
            Direccion destino = cellData.getValue().getDestino();
            return new javafx.beans.property.SimpleStringProperty(destino != null ? destino.getAlias() : "");
        });
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colRepartidor.setCellValueFactory(cellData -> {
            Repartidor repartidor = cellData.getValue().getRepartidor();
            return new javafx.beans.property.SimpleStringProperty(repartidor != null ? repartidor.getNombre() : "Sin asignar");
        });
        colFecha.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );
        });
        colCosto.setCellValueFactory(cellData -> {
            return new javafx.beans.property.SimpleStringProperty("$" + String.format("%.2f", cellData.getValue().getCosto()));
        });
        
        comboEstado.getItems().addAll(EstadoEnvio.values());
        comboEstado.getItems().add(0, null);
        
        cargarEnvios();
    }
    
    private void cargarEnvios() {
        tablaEnvios.getItems().clear();
        tablaEnvios.getItems().addAll(PlataformaLogisticaSingleton.getInstancia().getEmpresa().getEnvios());
    }
    
    @FXML
    private void filtrarEnvios() {
        EstadoEnvio estado = comboEstado.getValue();
        if (estado != null) {
            tablaEnvios.getItems().clear();
            tablaEnvios.getItems().addAll(PlataformaLogisticaSingleton.getInstancia().getEmpresa().filtrarEnviosPorEstado(estado));
        }
    }
    
    @FXML
    private void mostrarTodos() {
        cargarEnvios();
    }


    @FXML
    private void asignarRepartidor() {
        Envio envio = tablaEnvios.getSelectionModel().getSelectedItem();
        if (envio != null) {
            Dialog<Repartidor> dialog = new Dialog<>();
            dialog.setTitle("Asignar Repartidor");

            ComboBox<Repartidor> combo = new ComboBox<>();
            combo.getItems().addAll(PlataformaLogisticaSingleton.getInstancia().getEmpresa().buscarRepartidoresDisponibles());

            // Personalizar cómo se muestra cada repartidor en el ComboBox
            combo.setCellFactory(param -> new ListCell<Repartidor>() {
                @Override
                protected void updateItem(Repartidor item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre() + " - " + item.getZonaCobertura() + " (" + item.getEstado() + ")");
                    }
                }
            });

            // También personalizar el botón del ComboBox (lo que se ve cuando está cerrado)
            combo.setButtonCell(new ListCell<Repartidor>() {
                @Override
                protected void updateItem(Repartidor item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNombre() + " - " + item.getZonaCobertura() + " (" + item.getEstado() + ")");
                    }
                }
            });

            combo.setPromptText("Seleccione un repartidor");

            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(new Label("Repartidor:"), combo);
            vbox.setPadding(new javafx.geometry.Insets(20));
            dialog.getDialogPane().setContent(vbox);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? combo.getValue() : null);

            Optional<Repartidor> result = dialog.showAndWait();
            result.ifPresent(repartidor -> {
                servicioEnvios.asignarRepartidor(envio.getIdEnvio(), repartidor.getIdRepartidor());
                cargarEnvios();
                mostrarMensaje("Éxito", "Repartidor asignado correctamente", Alert.AlertType.INFORMATION);
            });
        } else {
            mostrarMensaje("Advertencia", "Seleccione un envío", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void cambiarEstado() {
        Envio envio = tablaEnvios.getSelectionModel().getSelectedItem();
        if (envio != null) {
            Dialog<EstadoEnvio> dialog = new Dialog<>();
            dialog.setTitle("Cambiar Estado");
            
            ComboBox<EstadoEnvio> combo = new ComboBox<>();
            combo.getItems().addAll(EstadoEnvio.values());
            combo.setValue(envio.getEstado());
            
            VBox vbox = new VBox(10);
            vbox.getChildren().addAll(new Label("Nuevo Estado:"), combo);
            vbox.setPadding(new javafx.geometry.Insets(20));
            dialog.getDialogPane().setContent(vbox);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            
            dialog.setResultConverter(dialogButton -> dialogButton == ButtonType.OK ? combo.getValue() : null);
            
            Optional<EstadoEnvio> result = dialog.showAndWait();
            result.ifPresent(estado -> {
                servicioEnvios.actualizarEstado(envio.getIdEnvio(), estado);
                cargarEnvios();
                mostrarMensaje("Éxito", "Estado actualizado", Alert.AlertType.INFORMATION);
            });
        } else {
            mostrarMensaje("Advertencia", "Seleccione un envío", Alert.AlertType.WARNING);
        }
    }
    
    @FXML
    private void verDetalle() {
        Envio envio = tablaEnvios.getSelectionModel().getSelectedItem();
        if (envio != null) {
            StringBuilder detalle = new StringBuilder();
            detalle.append("ID: ").append(envio.getIdEnvio()).append("\n");
            detalle.append("Usuario: ").append(envio.getUsuario() != null ? envio.getUsuario().getNombreCompleto() : "").append("\n");
            detalle.append("Origen: ").append(envio.getOrigen() != null ? envio.getOrigen().getCalle() : "").append("\n");
            detalle.append("Destino: ").append(envio.getDestino() != null ? envio.getDestino().getCalle() : "").append("\n");
            detalle.append("Estado: ").append(envio.getEstado()).append("\n");
            detalle.append("Peso: ").append(envio.getPeso()).append(" kg\n");
            detalle.append("Volumen: ").append(envio.getVolumen()).append(" m³\n");
            detalle.append("Costo: $").append(envio.getCosto()).append("\n");
            
            mostrarMensaje("Detalle del Envío", detalle.toString(), Alert.AlertType.INFORMATION);
        } else {
            mostrarMensaje("Advertencia", "Seleccione un envío", Alert.AlertType.WARNING);
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

