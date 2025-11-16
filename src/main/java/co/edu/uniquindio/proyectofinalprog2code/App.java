package co.edu.uniquindio.proyectofinalprog2code;

import co.edu.uniquindio.proyectofinalprog2code.util.InicializadorDatos;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inicializar datos de prueba
        InicializadorDatos.inicializar();

        // Cargar vista de login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/proyectofinalprog2code/LoginView.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Plataforma Logística - Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}