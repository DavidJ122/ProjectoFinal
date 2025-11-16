package co.edu.uniquindio.proyectofinalprog2code.Controller;

import co.edu.uniquindio.proyectofinalprog2code.dto.CotizacionDTO;
import co.edu.uniquindio.proyectofinalprog2code.Model.*;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.facade.PlataformaFacade;

import java.util.List;

/**
 * Servicio de negocio para cotizaciones (RF-031, RF-032)
 */
public class ServicioCotizacion {
    private final PlataformaFacade facade;

    public ServicioCotizacion() {
        this.facade = new PlataformaFacade();
    }

    public CotizacionDTO cotizarEnvio(Direccion origen, Direccion destino,
                                      double peso, double volumen,
                                      boolean esPrioritario) {
        PlataformaFacade.ResultadoCotizacion resultado =
                facade.cotizarEnvio(origen, destino, peso, volumen, esPrioritario);

        CotizacionDTO dto = new CotizacionDTO();
        dto.setCostoBase(resultado.getCostoBase());
        dto.setCostoPeso(resultado.getCostoPeso());
        dto.setCostoVolumen(resultado.getCostoVolumen());
        dto.setCostoPrioridad(esPrioritario ? resultado.getCostoTotal() * 0.33 : 0);
        dto.setCostoTotal(resultado.getCostoTotal());
        dto.setDistancia(resultado.getDistancia());

        return dto;
    }

    public CotizacionDTO cotizarConServiciosAdicionales(CotizacionDTO cotizacionBase,
                                                        List<ServicioAdicional> servicios) {
        double costoTotal = cotizacionBase.getCostoTotal();

        for (ServicioAdicional servicio : servicios) {
            costoTotal += servicio.getCostoAdicional();
        }

        cotizacionBase.setCostoTotal(costoTotal);
        return cotizacionBase;
    }
}