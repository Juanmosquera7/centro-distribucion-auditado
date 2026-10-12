package com.quindioexpress;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import junit.framework.TestCase;

public class ComparadoresPaqueteTest extends TestCase {

    public void testOrdenPorPrioridad() {
        Paquete p1 = new Paquete("PK001", "Armenia", 5.0, 2, 30);
        Paquete p2 = new Paquete("PK002", "Armenia", 3.0, 5, 40);
        Paquete p3 = new Paquete("PK003", "Armenia", 4.0, 4, 20);

        List<Paquete> paquetes =
                new ArrayList<>(Arrays.asList(p1, p2, p3));

        paquetes.sort(ComparadoresPaquete.POR_PRIORIDAD);

        assertEquals("PK002", paquetes.get(0).getCodigo());
        assertEquals("PK003", paquetes.get(1).getCodigo());
        assertEquals("PK001", paquetes.get(2).getCodigo());
    }

    public void testOrdenPorPeso() {
        Paquete p1 = new Paquete("PK001", "Armenia", 5.0, 2, 30);
        Paquete p2 = new Paquete("PK002", "Armenia", 12.0, 5, 40);
        Paquete p3 = new Paquete("PK003", "Armenia", 3.0, 5, 20);

        List<Paquete> paquetes =
                new ArrayList<>(Arrays.asList(p1, p2, p3));

        paquetes.sort(ComparadoresPaquete.POR_PESO);

        assertEquals("PK002", paquetes.get(0).getCodigo());
        assertEquals("PK001", paquetes.get(1).getCodigo());
        assertEquals("PK003", paquetes.get(2).getCodigo());
    }

    public void testOrdenPorTiempo() {
        Paquete p1 = new Paquete("PK001", "Armenia", 5.0, 2, 30);
        Paquete p2 = new Paquete("PK002", "Armenia", 3.0, 5, 10);
        Paquete p3 = new Paquete("PK003", "Armenia", 4.0, 5, 20);

        List<Paquete> paquetes =
                new ArrayList<>(Arrays.asList(p1, p2, p3));

        paquetes.sort(ComparadoresPaquete.POR_TIEMPO);

        assertEquals("PK002", paquetes.get(0).getCodigo());
        assertEquals("PK003", paquetes.get(1).getCodigo());
        assertEquals("PK001", paquetes.get(2).getCodigo());
    }

    public void testOrdenCompletoDeDespacho() {
        Paquete p1 = new Paquete("PK731", "Armenia", 5.0, 5, 45);
        Paquete p2 = new Paquete("PK105", "Armenia", 3.0, 5, 25);
        Paquete p3 = new Paquete("PK942", "Armenia", 4.0, 4, 20);
        Paquete p4 = new Paquete("PK420", "Armenia", 6.0, 3, 30);
        Paquete p5 = new Paquete("PK100", "Armenia", 2.0, 5, 25);

        List<Paquete> paquetes = new ArrayList<>(
                Arrays.asList(p1, p2, p3, p4, p5)
        );

        paquetes.sort(ComparadoresPaquete.PARA_DESPACHO);

        assertEquals("PK100", paquetes.get(0).getCodigo());
        assertEquals("PK105", paquetes.get(1).getCodigo());
        assertEquals("PK731", paquetes.get(2).getCodigo());
        assertEquals("PK942", paquetes.get(3).getCodigo());
        assertEquals("PK420", paquetes.get(4).getCodigo());
    }

    public void testOrdenNaturalNoSeModifica() {
        Paquete p1 = new Paquete("PK731", "Armenia", 5.0, 5, 45);
        Paquete p2 = new Paquete("PK105", "Armenia", 3.0, 5, 25);

        assertTrue(p1.compareTo(p2) > 0);
        assertTrue(
                ComparadoresPaquete.PARA_DESPACHO.compare(p1, p2) > 0
        );
    }
}
