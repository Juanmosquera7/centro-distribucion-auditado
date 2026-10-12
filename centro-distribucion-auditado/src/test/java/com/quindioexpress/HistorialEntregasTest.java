
package com.quindioexpress;

import junit.framework.TestCase;

public class HistorialEntregasTest extends TestCase {

    public void testHistorialConservaOrdenRealDeEntrega() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 5.0, 2, 30)
        );
        centro.registrarPaquete(
                new Paquete("PK002", "Armenia", 3.0, 5, 10)
        );
        centro.registrarPaquete(
                new Paquete("PK003", "Armenia", 4.0, 5, 20)
        );

        centro.registrarRepartidor(
                new Repartidor("R001", "Carlos", "Armenia", true)
        );
        centro.registrarRepartidor(
                new Repartidor("R002", "Laura", "Armenia", true)
        );
        centro.registrarRepartidor(
                new Repartidor("R003", "Andres", "Armenia", true)
        );

        // Se retiran de FIFO antes de asignarlos.
        assertEquals("PK001", centro.retirarSiguienteFIFO().getCodigo());
        assertTrue(centro.asignarPaquete("PK001"));
        assertEquals("PK002", centro.retirarSiguienteFIFO().getCodigo());
        assertTrue(centro.asignarPaquete("PK002"));
        assertEquals("PK003", centro.retirarSiguienteFIFO().getCodigo());
        assertTrue(centro.asignarPaquete("PK003"));

        // El orden de entrega no depende del código ni de la prioridad.
        assertTrue(centro.registrarEntrega("PK003"));
        assertTrue(centro.registrarEntrega("PK001"));
        assertTrue(centro.registrarEntrega("PK002"));

        assertEquals(3, centro.cantidadEntregas());
        assertEquals("PK003", centro.obtenerEntrega(0).getCodigo());
        assertEquals("PK001", centro.obtenerEntrega(1).getCodigo());
        assertEquals("PK002", centro.obtenerEntrega(2).getCodigo());
    }

    public void testHistorialInicialmenteVacio() {
        CentroDistribucion centro = new CentroDistribucion();

        assertTrue(centro.historialVacio());
        assertEquals(0, centro.cantidadEntregas());
    }

    public void testNoRegistraPaqueteInexistente() {
        CentroDistribucion centro = new CentroDistribucion();

        assertFalse(centro.registrarEntrega("PK999"));
        assertEquals(0, centro.cantidadEntregas());
    }

    public void testNoPermiteDuplicarEntrega() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 5.0, 5, 20)
        );
        centro.registrarRepartidor(
                new Repartidor("R001", "Carlos", "Armenia", true)
        );

        centro.retirarSiguienteFIFO();
        assertTrue(centro.asignarPaquete("PK001"));
        assertTrue(centro.registrarEntrega("PK001"));
        assertFalse(centro.registrarEntrega("PK001"));

        assertEquals(1, centro.cantidadEntregas());
    }

    public void testIndiceFueraDeRango() {
        CentroDistribucion centro = new CentroDistribucion();

        try {
            centro.obtenerEntrega(0);
            fail("Se esperaba IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException esperado) {
            assertEquals(0, centro.cantidadEntregas());
        }
    }

    public void testEntregaLiberaRepartidorYRetiraPaqueteDeColas() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 5.0, 5, 20)
        );
        Repartidor repartidor =
                new Repartidor("R001", "Carlos", "Armenia", true);

        centro.registrarRepartidor(repartidor);

        centro.retirarSiguienteFIFO();
        assertTrue(centro.asignarPaquete("PK001"));
        assertFalse(repartidor.isDisponible());

        assertTrue(centro.registrarEntrega("PK001"));

        assertTrue(repartidor.isDisponible());
        assertNull(centro.obtenerRepartidorAsignado("PK001"));
        assertEquals(0, centro.cantidadPendientesFIFO());
        assertEquals(0, centro.cantidadPendientesPorPrioridad());
    }

    public void testNoRegistraEntregaSinAsignacion() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 5.0, 5, 20)
        );

        assertFalse(centro.registrarEntrega("PK001"));
        assertEquals(0, centro.cantidadEntregas());
        assertEquals(1, centro.cantidadPendientesFIFO());
        assertEquals(1, centro.cantidadPendientesPorPrioridad());
    }
}
