package co.edu.uniquindio.proyectofinalprog2code.Patrones.facade;

import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.PlataformaLogisticaSingleton;
import co.edu.uniquindio.proyectofinalprog2code.model.*;

public class PlataformaFacade {
    private final Empresa empresa;

    public PlataformaFacade() {
        this.empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();
    }

    // Métodos unificados para operaciones complejas
    public ResultadoCotizacion cotizarEnvio(Direccion origen, Direccion destino,
                                            double peso, double volumen,
                                            boolean esPrioritario) {
        double distancia = origen.calcularDistancia(destino);
        Tarifa tarifa = empresa.getTarifa();

        double costoBase = distancia * tarifa.getCostoBasePorKm();
        double costoPeso = peso * tarifa.getCostoPorKilo();
        double costoVolumen = volumen * tarifa.getCostoPorVolumen();

        double costoTotal = costoBase + costoPeso + costoVolumen;

        if (esPrioritario) {
            costoTotal = costoTotal * tarifa.getRecargoPrioridad();
        }

        return new ResultadoCotizacion(costoBase, costoPeso, costoVolumen, costoTotal, distancia);
    }

    public void procesarSolicitudCompleta(Usuario usuario, Direccion origen, Direccion destino,
                                          double peso, double volumen, Envio envio,
                                          MetodoPago metodoPago) throws PlataformaLogisticaException {
        // 1. Crear envío
        empresa.agregarEnvio(envio);

        // 2. Procesar pago
        Pago pago = procesarPago(envio.getCosto(), metodoPago);
        envio.setPago(pago);
    }

    public Pago procesarPago(double monto, MetodoPago metodoPago) {
        // Simulación simple de procesamiento de pago
        Pago pago = new Pago();
        pago.setIdPago("PAGO-" + System.currentTimeMillis());
        pago.setMonto(monto);
        pago.setMetodoPago(metodoPago);

        // Simulación: 90% de aprobación
        boolean aprobado = Math.random() > 0.1;
        pago.setResultado(aprobado ? EstadoPago.APROBADO : EstadoPago.RECHAZADO);

        if (aprobado) {
            pago.setComprobante("COMP-" + pago.getIdPago() + "-" + System.currentTimeMillis());
        }

        return pago;
    }

    public void notificarCambioEstado(Envio envio) {
        // El Observer se encargará de esto
        System.out.println("Notificación: Envío " + envio.getIdEnvio() +
                " ha cambiado a estado " + envio.getEstado());
    }

    public class ResultadoCotizacion {
        private final double costoBase;
        private final double costoPeso;
        private final double costoVolumen;
        private final double costoTotal;
        private final double distancia;

        public ResultadoCotizacion(double costoBase, double costoPeso, double costoVolumen,
                                   double costoTotal, double distancia) {
            this.costoBase = costoBase;
            this.costoPeso = costoPeso;
            this.costoVolumen = costoVolumen;
            this.costoTotal = costoTotal;
            this.distancia = distancia;
        }

        public double getCostoBase() { return costoBase; }
        public double getCostoPeso() { return costoPeso; }
        public double getCostoVolumen() { return costoVolumen; }
        public double getCostoTotal() { return costoTotal; }
        public double getDistancia() { return distancia; }
    }
}
