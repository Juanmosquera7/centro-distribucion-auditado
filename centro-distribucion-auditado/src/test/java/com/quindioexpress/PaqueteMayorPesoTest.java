package com.quindioexpress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

public class PaqueteMayorPesoTest extends TestCase {

    private List<Paquete> crearPaquetes() {
        return new ArrayList<>(Arrays.asList(
                new Paquete("PK001", "Armenia", 5.0, 3, 20),
                new Paquete("PK002", "Calarca", 12.5, 4, 30),
                new Paquete("PK003", "Pereira", 8.0, 2, 15),
                new Paquete("PK004", "Armenia", 20.0, 5, 40),
                new Paquete("PK005", "Montenegro", 3.5, 1, 10)
        ));
    }

    public void testEncuentraPaqueteMasPesado() {
        Paquete resultado =
                PaqueteMayorPeso.encontrar(crearPaquetes());

        assertNotNull(resultado);
        assertEquals("PK004", resultado.getCodigo());
        assertEquals(20.0, resultado.getPeso(), 0.001);
    }

    public void testListaVaciaDevuelveNull() {
        assertNull(PaqueteMayorPeso.encontrar(
                Collections.<Paquete>emptyList()));
    }

    public void testUnSoloPaquete() {
        List<Paquete> paquetes = Collections.singletonList(
                new Paquete("PK001", "Armenia", 7.0, 2, 10));

        Paquete resultado = PaqueteMayorPeso.encontrar(paquetes);

        assertEquals("PK001", resultado.getCodigo());
    }

    public void testEmpateConservaElPrimero() {
        List<Paquete> paquetes = Arrays.asList(
                new Paquete("PK001", "Armenia", 10.0, 2, 10),
                new Paquete("PK002", "Calarca", 10.0, 4, 20));

        Paquete resultado = PaqueteMayorPeso.encontrar(paquetes);

        assertEquals("PK001", resultado.getCodigo());
    }

    public void testListaNullGeneraExcepcion() {
        try {
            PaqueteMayorPeso.encontrar(null);
            fail("Se esperaba IllegalArgumentException.");
        } catch (IllegalArgumentException esperado) {
            // Comportamiento esperado.
        }
    }
}