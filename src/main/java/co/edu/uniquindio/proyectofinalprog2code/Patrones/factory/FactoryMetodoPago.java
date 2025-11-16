package co.edu.uniquindio.proyectofinalprog2code.Patrones.factory;

import co.edu.uniquindio.proyectofinalprog2code.Model.*;

public class FactoryMetodoPago {
    public static MetodoPago crearMetodoPago(String idMetodoPago, String numeroTarjeta,
                                             String titular, TipoPago tipoPago) {
        MetodoPago metodo = new MetodoPago();
        metodo.setIdMetodoPago(idMetodoPago);
        metodo.setNumeroTarjeta(numeroTarjeta);
        metodo.setTitular(titular);
        metodo.setTipoPago(tipoPago);

        return metodo;
    }

    public static MetodoPago crearMetodoPagoEfectivo(String idMetodoPago, String titular) {
        return crearMetodoPago(idMetodoPago, null, titular, TipoPago.EFECTIVO);
    }

    public static MetodoPago crearMetodoPagoTarjeta(String idMetodoPago, String numeroTarjeta,
                                                    String titular, boolean esCredito) {
        TipoPago tipo = esCredito ? TipoPago.TARJETA_CREDITO : TipoPago.TARJETA_DEBITO;
        return crearMetodoPago(idMetodoPago, numeroTarjeta, titular, tipo);
    }
}
