package co.edu.uniquindio.proyectofinalprog2code.Patrones.command;

public interface Comando {
    void ejecutar() throws Exception;
    void deshacer() throws Exception;
}
