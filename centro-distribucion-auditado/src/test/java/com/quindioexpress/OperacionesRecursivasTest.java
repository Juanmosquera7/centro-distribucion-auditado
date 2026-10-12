package com.quindioexpress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class OperacionesRecursivasTest extends TestCase {

    private List<Paquete> crearPaquetes() {
        return new ArrayList<>(Arrays.asList(
                new Paquete("PK001", "Armenia", 5.0, 2, 30),
                new Paquete("PK002", "Calarca", 3.5, 5, 10),
                new Paquete("PK003", "Armenia", 2.0, 5, 20),
                new Paquete("PK004", "Montenegro", 4.0, 3, 25),
                new Paquete("PK005", "Armenia", 1.5, 5, 15)
        ));
    }

    public void testSumarPesoPorDestino() {
        List<Paquete> paquetes = crearPaquetes();

        // Armenia: 5.0 + 2.0 + 1.5 = 8.5
        assertEquals(
                8.5,
                OperacionesRecursivas.sumarPesoPorDestino(
                        paquetes, "Armenia"),
                0.0001
        );
    }

    public void testDestinoSinPaquetesDevuelveCero() {
        assertEquals(
                0.0,
                OperacionesRecursivas.sumarPesoPorDestino(
                        crearPaquetes(), "Pereira"),
                0.0001
        );
    }

    public void testDestinoIgnoraMayusculas() {
        assertEquals(
                8.5,
                OperacionesRecursivas.sumarPesoPorDestino(
                        crearPaquetes(), "ARMENIA"),
                0.0001
        );
    }

    public void testContarPorPrioridadMinima() {
        // Prioridad >= 5: PK002, PK003 y PK005.
        assertEquals(
                3,
                OperacionesRecursivas.contarPorPrioridadMinima(
                        crearPaquetes(), 5)
        );
    }

    public void testNingunaPrioridadAlcanzaElUmbral() {
        assertEquals(
                0,
                OperacionesRecursivas.contarPorPrioridadMinima(
                        crearPaquetes(), 10)
        );
    }

    public void testListaVacia() {
        List<Paquete> vacia = Collections.emptyList();

        assertEquals(
                0.0,
                OperacionesRecursivas.sumarPesoPorDestino(
                        vacia, "Armenia"),
                0.0001
        );

        assertEquals(
                0,
                OperacionesRecursivas.contarPorPrioridadMinima(
                        vacia, 5)
        );
    }

    public void testListaNullGeneraExcepcion() {
        try {
            OperacionesRecursivas.contarPorPrioridadMinima(null, 5);
            fail("Se esperaba IllegalArgumentException.");
        } catch (IllegalArgumentException esperado) {
            // Comportamiento esperado.
        }
    }
}
