package co.edu.uniquindio.proyectofinalprog2code.Model;

public class Tarifa {
    private static final long serialVersionUID = 1L;

    private double costoBasePorKm;
    private double costoPorKilo;
    private double costoPorVolumen;
    private double recargoPrioridad;

    public Tarifa() {
        this.costoBasePorKm = 2000;  // $2000 por km
        this.costoPorKilo = 1000;    // $1000 por kg
        this.costoPorVolumen = 500;  // $500 por m³
        this.recargoPrioridad = 1.5; // 50% adicional
    }

    public Tarifa(double costoBasePorKm, double costoPorKilo, double costoPorVolumen, double recargoPrioridad) {
        this.costoBasePorKm = costoBasePorKm;
        this.costoPorKilo = costoPorKilo;
        this.costoPorVolumen = costoPorVolumen;
        this.recargoPrioridad = recargoPrioridad;
    }

    // Getters y Setters
    public double getCostoBasePorKm() {
        return costoBasePorKm;
    }

    public void setCostoBasePorKm(double costoBasePorKm) {
        this.costoBasePorKm = costoBasePorKm;
    }

    public double getCostoPorKilo() {
        return costoPorKilo;
    }

    public void setCostoPorKilo(double costoPorKilo) {
        this.costoPorKilo = costoPorKilo;
    }

    public double getCostoPorVolumen() {
        return costoPorVolumen;
    }

    public void setCostoPorVolumen(double costoPorVolumen) {
        this.costoPorVolumen = costoPorVolumen;
    }

    public double getRecargoPrioridad() {
        return recargoPrioridad;
    }

    public void setRecargoPrioridad(double recargoPrioridad) {
        this.recargoPrioridad = recargoPrioridad;
    }

    @Override
    public String toString() {
        return "Tarifa{" +
                "costoBasePorKm=" + costoBasePorKm +
                ", costoPorKilo=" + costoPorKilo +
                ", costoPorVolumen=" + costoPorVolumen +
                ", recargoPrioridad=" + recargoPrioridad +
                '}';
    }
}
