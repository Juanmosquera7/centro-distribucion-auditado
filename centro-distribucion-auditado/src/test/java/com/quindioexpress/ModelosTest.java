package com.quindioexpress;

import junit.framework.TestCase;

public class ModelosTest extends TestCase {

    public void testCrearPaquete() {
        Paquete paquete = new Paquete(
                "PK001", "Armenia", 5.5, 5, 30
        );

        assertEquals("PK001", paquete.getCodigo());
        assertEquals("Armenia", paquete.getDestino());
        assertEquals(5.5, paquete.getPeso(), 0.001);
        assertEquals(5, paquete.getPrioridad());
        assertEquals(30, paquete.getTiempoEstimado());
    }

    public void testOrdenNaturalPorCodigo() {
        Paquete paqueteA = new Paquete(
                "PK001", "Armenia", 2.0, 5, 20
        );

        Paquete paqueteB = new Paquete(
                "PK002", "Calarcá", 3.0, 4, 15
        );

        assertTrue(paqueteA.compareTo(paqueteB) < 0);
        assertTrue(paqueteB.compareTo(paqueteA) > 0);
        assertEquals(0, paqueteA.compareTo(paqueteA));
    }

    public void testRechazarPesoInvalido() {
        try {
            new Paquete("PK003", "Armenia", 0, 5, 20);
            fail("Se esperaba IllegalArgumentException");
        } catch (IllegalArgumentException esperado) {
            // Comportamiento esperado.
        }
    }

    public void testRechazarTiempoInvalido() {
        try {
            new Paquete("PK004", "Armenia", 2.0, 5, 0);
            fail("Se esperaba IllegalArgumentException");
        } catch (IllegalArgumentException esperado) {
            // Comportamiento esperado.
        }
    }


    public void testRechazarPrioridadMenorQueUno() {
        try {
            new Paquete("PK005", "Armenia", 2.0, 0, 10);
            fail("Se esperaba IllegalArgumentException para prioridad 0");
        } catch (IllegalArgumentException esperado) {
            // Comportamiento esperado.
        }
    }

    public void testRechazarPrioridadMayorQueCinco() {
        try {
            new Paquete("PK006", "Armenia", 2.0, 6, 10);
            fail("Se esperaba IllegalArgumentException para prioridad 6");
        } catch (IllegalArgumentException esperado) {
            // Comportamiento esperado.
        }
    }

    public void testCrearRepartidor() {
        Repartidor repartidor = new Repartidor(
                "R001", "Carlos", "Armenia", true
        );

        assertEquals("R001", repartidor.getId());
        assertEquals("Carlos", repartidor.getNombre());
        assertEquals("Armenia", repartidor.getZona());
        assertTrue(repartidor.isDisponible());
    }

    public void testCambiarDisponibilidad() {
        Repartidor repartidor = new Repartidor(
                "R002", "Laura", "Calarcá", true
        );

        repartidor.setDisponible(false);
        assertFalse(repartidor.isDisponible());

        repartidor.setDisponible(true);
        assertTrue(repartidor.isDisponible());
    }
}
