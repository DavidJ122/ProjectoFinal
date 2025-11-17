package co.edu.uniquindio.proyectofinalprog2code.util;

import co.edu.uniquindio.proyectofinalprog2code.Patrones.builder.EnvioBuilder;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.factory.FactoryMetodoPago;
import co.edu.uniquindio.proyectofinalprog2code.Model.*;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.strategy.*;
import co.edu.uniquindio.proyectofinalprog2code.Patrones.singleton.*;
import java.time.LocalDateTime;

public class InicializadorDatos {

    public static void inicializar() {
        try {
            Empresa empresa = PlataformaLogisticaSingleton.getInstancia().getEmpresa();

            // Crear usuarios
            Usuario admin = new Usuario("USU-001", "Administrador", "admin@plataforma.com",
                    "3001234567", "admin123");
            admin.setTipoUsuario(TipoUsuario.ADMINISTRADOR);
            empresa.agregarUsuario(admin);

            Usuario usuario1 = new Usuario("USU-002", "Juan Pérez", "juan@email.com",
                    "3001111111", "juan123");
            empresa.agregarUsuario(usuario1);

            Usuario usuario2 = new Usuario("USU-003", "María García", "maria@email.com",
                    "3002222222", "maria123");
            empresa.agregarUsuario(usuario2);

            // Crear direcciones
            Direccion dir1 = new Direccion("DIR-001", "Casa", "Calle 100 #50-30",
                    "Bogotá", 4.6533, -74.0836);
            Direccion dir2 = new Direccion("DIR-002", "Oficina", "Carrera 7 #32-16",
                    "Bogotá", 4.6097, -74.0817);
            Direccion dir3 = new Direccion("DIR-003", "Otro", "Calle 80 #11-45",
                    "Bogotá", 4.6512, -74.1087);
            Direccion dir4 = new Direccion("DIR-004", "Centro", "Carrera 13 #9-20",
                    "Bogotá", 4.5981, -74.0756);

            usuario1.agregarDireccion(dir1);
            usuario1.agregarDireccion(dir2);
            usuario2.agregarDireccion(dir3);
            usuario2.agregarDireccion(dir4);

            // Crear métodos de pago
            MetodoPago metodo1 = FactoryMetodoPago.crearMetodoPagoTarjeta("MP-001",
                    "4512345678901234", "Juan Pérez", true);
            usuario1.agregarMetodoPago(metodo1);

            MetodoPago metodo2 = FactoryMetodoPago.crearMetodoPagoTarjeta("MP-002",
                    "5212345678905678", "María García", false);
            usuario2.agregarMetodoPago(metodo2);

            // Crear repartidores
            Repartidor repartidor1 = new Repartidor("REP-001", "Carlos Rodríguez",
                    "123456789", "3101234567", "Bogotá Norte");
            repartidor1.setEstado(EstadoRepartidor.ACTIVO);
            empresa.agregarRepartidor(repartidor1);

            Repartidor repartidor2 = new Repartidor("REP-002", "Ana López",
                    "987654321", "3207654321", "Bogotá Centro");
            repartidor2.setEstado(EstadoRepartidor.ACTIVO);
            empresa.agregarRepartidor(repartidor2);

            Repartidor repartidor3 = new Repartidor("REP-003", "Luis Martínez",
                    "456789123", "3159876543", "Bogotá Sur");
            repartidor3.setEstado(EstadoRepartidor.INACTIVO);
            empresa.agregarRepartidor(repartidor3);

            // Crear envíos
            Envio envio1 = new EnvioBuilder()
                    .conId("ENV-001")
                    .conOrigen(dir1)
                    .conDestino(dir2)
                    .conPeso(5.0)
                    .conVolumen(0.5)
                    .conCosto(15000)
                    .conUsuario(usuario1)
                    .conRepartidor(repartidor1)
                    .conEstado(EstadoEnvio.ENTREGADO)
                    .agregarServicioAdicional(ServicioAdicional.SEGURO)
                    .construir();

            envio1.setFechaCreacion(LocalDateTime.now().minusDays(5));
            envio1.setFechaEstimadaEntrega(LocalDateTime.now().minusDays(4));
            envio1.setFechaEntregaReal(LocalDateTime.now().minusDays(4));

            Pago pago1 = new Pago("PAG-001", 20000, metodo1, EstadoPago.APROBADO);
            pago1.setFecha(LocalDateTime.now().minusDays(5));
            envio1.setPago(pago1);

            empresa.agregarEnvio(envio1);

            Envio envio2 = new EnvioBuilder()
                    .conId("ENV-002")
                    .conOrigen(dir3)
                    .conDestino(dir4)
                    .conPeso(10.0)
                    .conVolumen(1.0)
                    .conCosto(25000)
                    .conUsuario(usuario2)
                    .conRepartidor(repartidor2)
                    .conEstado(EstadoEnvio.EN_RUTA)
                    .agregarServicioAdicional(ServicioAdicional.ENTREGA_PRIORITARIA)
                    .agregarServicioAdicional(ServicioAdicional.FRAGIL)
                    .construir();

            envio2.setFechaCreacion(LocalDateTime.now().minusDays(2));
            envio2.setFechaEstimadaEntrega(LocalDateTime.now().plusDays(1));

            Pago pago2 = new Pago("PAG-002", 38000, metodo2, EstadoPago.APROBADO);
            pago2.setFecha(LocalDateTime.now().minusDays(2));
            envio2.setPago(pago2);

            empresa.agregarEnvio(envio2);

            Envio envio3 = new EnvioBuilder()
                    .conId("ENV-003")
                    .conOrigen(dir1)
                    .conDestino(dir3)
                    .conPeso(2.5)
                    .conVolumen(0.3)
                    .conCosto(12000)
                    .conUsuario(usuario1)
                    .conEstado(EstadoEnvio.SOLICITADO)
                    .agregarServicioAdicional(ServicioAdicional.FIRMA_REQUERIDA)
                    .construir();

            envio3.setFechaCreacion(LocalDateTime.now().minusHours(5));

            empresa.agregarEnvio(envio3);

            Envio envio4 = new EnvioBuilder()
                    .conId("ENV-004")
                    .conOrigen(dir2)
                    .conDestino(dir4)
                    .conPeso(8.0)
                    .conVolumen(0.8)
                    .conCosto(22000)
                    .conUsuario(usuario2)
                    .conRepartidor(repartidor1)
                    .conEstado(EstadoEnvio.ASIGNADO)
                    .construir();

            envio4.setFechaCreacion(LocalDateTime.now().minusDays(1));

            Pago pago4 = new Pago("PAG-003", 22000, metodo2, EstadoPago.APROBADO);
            pago4.setFecha(LocalDateTime.now().minusDays(1));
            envio4.setPago(pago4);

            empresa.agregarEnvio(envio4);

            Incidencia incidencia1 = new Incidencia(
                    "Retraso en la entrega debido a tráfico pesado en la zona",
                    LocalDateTime.now().minusDays(2).toString(),
                    "RETRASO",
                    envio2
            );
            envio2.addIncidencia(incidencia1);

            Incidencia incidencia2 = new Incidencia(
                    "Cliente ausente en el momento de la entrega, se dejó notificación",
                    LocalDateTime.now().minusDays(4).toString(),
                    "CLIENTE_AUSENTE",
                    envio1
            );
            envio1.addIncidencia(incidencia2);

            System.out.println("Datos inicializados correctamente");
        } catch (PlataformaLogisticaException e) {
            System.err.println("Error al inicializar datos: " + e.getMessage());
        }
    }
}
