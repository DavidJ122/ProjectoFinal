module co.edu.uniquindio.proyectofinalprog2code {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;


    opens co.edu.uniquindio.proyectofinalprog2code to javafx.fxml;
    exports co.edu.uniquindio.proyectofinalprog2code;
    exports co.edu.uniquindio.proyectofinalprog2code.Controller;
    opens co.edu.uniquindio.proyectofinalprog2code.ViewController to javafx.fxml;
    opens co.edu.uniquindio.proyectofinalprog2code.Model to javafx.base;
    opens co.edu.uniquindio.proyectofinalprog2code.Controller to javafx.fxml;
}