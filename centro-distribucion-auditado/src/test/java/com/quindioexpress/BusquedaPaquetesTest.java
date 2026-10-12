package com.quindioexpress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class BusquedaPaquetesTest extends TestCase {

    private List<Paquete> crearPaquetesDesordenados() {
        return new ArrayList<>(Arrays.asList(
                new Paquete("PK030", "Armenia", 5.0, 2, 30),
                new Paquete("PK010", "Calarca", 3.0, 5, 10),
                new Paquete("PK020", "Pereira", 4.0, 5, 20)
        ));
    }

    public void testEncuentraPaqueteExistente() {
        Paquete encontrado = BusquedaPaquetes.buscarPorCodigo(
                crearPaquetesDesordenados(), "PK020");

        assertNotNull(encontrado);
        assertEquals("PK020", encontrado.getCodigo());
    }

    public void testEncuentraPrimerCodigo() {
        Paquete encontrado = BusquedaPaquetes.buscarPorCodigo(
                crearPaquetesDesordenados(), "PK010");

        assertNotNull(encontrado);
        assertEquals("PK010", encontrado.getCodigo());
    }

    public void testEncuentraUltimoCodigo() {
        Paquete encontrado = BusquedaPaquetes.buscarPorCodigo(
                crearPaquetesDesordenados(), "PK030");

        assertNotNull(encontrado);
        assertEquals("PK030", encontrado.getCodigo());
    }

    public void testCodigoInexistenteDevuelveNull() {
        assertNull(BusquedaPaquetes.buscarPorCodigo(
                crearPaquetesDesordenados(), "PK999"));
    }

    public void testListaVaciaDevuelveNull() {
        assertNull(BusquedaPaquetes.buscarPorCodigo(
                new ArrayList<Paquete>(), "PK001"));
    }

    public void testCodigoNullDevuelveNull() {
        assertNull(BusquedaPaquetes.buscarPorCodigo(
                crearPaquetesDesordenados(), null));
    }

    public void testNoModificaOrdenOriginal() {
        List<Paquete> paquetes = crearPaquetesDesordenados();

        BusquedaPaquetes.buscarPorCodigo(paquetes, "PK020");

        assertEquals("PK030", paquetes.get(0).getCodigo());
        assertEquals("PK010", paquetes.get(1).getCodigo());
        assertEquals("PK020", paquetes.get(2).getCodigo());
    }

    public void testListaNullGeneraExcepcion() {
        try {
            BusquedaPaquetes.buscarPorCodigo(null, "PK001");
            fail("Se esperaba IllegalArgumentException.");
        } catch (IllegalArgumentException esperado) {
            // Comportamiento esperado.
        }
    }
}