package com.quindioexpress;

import junit.framework.TestCase;

public class ColasPaqueteTest extends TestCase {

    public void testFIFORespetaOrdenDeRegistro() {
        CentroDistribucion centro = new CentroDistribucion();

        Paquete p1 = new Paquete("PK003", "Armenia", 5.0, 2, 30);
        Paquete p2 = new Paquete("PK001", "Armenia", 3.0, 5, 10);
        Paquete p3 = new Paquete("PK002", "Armenia", 4.0, 5, 20);

        centro.registrarPaquete(p1);
        centro.registrarPaquete(p2);
        centro.registrarPaquete(p3);

        assertEquals("PK003",
                centro.retirarSiguienteFIFO().getCodigo());
        assertEquals("PK001",
                centro.retirarSiguienteFIFO().getCodigo());
        assertEquals("PK002",
                centro.retirarSiguienteFIFO().getCodigo());
    }

    public void testPrioridadRespetaComparador() {
        CentroDistribucion centro = new CentroDistribucion();

        Paquete p1 = new Paquete("PK731", "Armenia", 5.0, 5, 45);
        Paquete p2 = new Paquete("PK105", "Armenia", 3.0, 5, 25);
        Paquete p3 = new Paquete("PK942", "Armenia", 4.0, 4, 20);

        centro.registrarPaquete(p1);
        centro.registrarPaquete(p2);
        centro.registrarPaquete(p3);

        assertEquals("PK105",
                centro.retirarSiguientePorPrioridad().getCodigo());
        assertEquals("PK731",
                centro.retirarSiguientePorPrioridad().getCodigo());
        assertEquals("PK942",
                centro.retirarSiguientePorPrioridad().getCodigo());
    }

    public void testConsultarNoRetiraPaquete() {
        CentroDistribucion centro = new CentroDistribucion();

        Paquete paquete =
                new Paquete("PK001", "Armenia", 5.0, 5, 20);

        centro.registrarPaquete(paquete);

        assertEquals("PK001",
                centro.consultarSiguienteFIFO().getCodigo());
        assertEquals(1, centro.cantidadPendientesFIFO());

        assertEquals("PK001",
                centro.consultarSiguientePorPrioridad().getCodigo());
        assertEquals(1, centro.cantidadPendientesPorPrioridad());
    }

    public void testRetirarReduceCantidadDeLaCola() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 5.0, 5, 20));
        centro.registrarPaquete(
                new Paquete("PK002", "Armenia", 3.0, 3, 30));

        assertEquals(2, centro.cantidadPendientesFIFO());

        centro.retirarSiguienteFIFO();

        assertEquals(1, centro.cantidadPendientesFIFO());
    }

    public void testColasVaciasDevuelvenNull() {
        CentroDistribucion centro = new CentroDistribucion();

        assertNull(centro.consultarSiguienteFIFO());
        assertNull(centro.retirarSiguienteFIFO());

        assertNull(centro.consultarSiguientePorPrioridad());
        assertNull(centro.retirarSiguientePorPrioridad());

        assertEquals(0, centro.cantidadPendientesFIFO());
        assertEquals(0, centro.cantidadPendientesPorPrioridad());
    }

    public void testRetirarFIFOEliminaPaqueteDeColaPrioridad() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 5.0, 5, 20));
        centro.registrarPaquete(
                new Paquete("PK002", "Armenia", 3.0, 3, 30));

        assertEquals("PK001",
                centro.retirarSiguienteFIFO().getCodigo());

        assertEquals(1, centro.cantidadPendientesFIFO());
        assertEquals(1, centro.cantidadPendientesPorPrioridad());

        assertEquals("PK002",
                centro.consultarSiguientePorPrioridad().getCodigo());
    }

    public void testRetirarPorPrioridadEliminaPaqueteDeColaFIFO() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 5.0, 2, 20));
        centro.registrarPaquete(
                new Paquete("PK002", "Armenia", 3.0, 5, 30));

        assertEquals("PK002",
                centro.retirarSiguientePorPrioridad().getCodigo());

        assertEquals(1, centro.cantidadPendientesFIFO());
        assertEquals(1, centro.cantidadPendientesPorPrioridad());

        assertEquals("PK001",
                centro.consultarSiguienteFIFO().getCodigo());
    }

}

