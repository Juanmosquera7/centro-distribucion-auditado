package com.quindioexpress;

import java.util.List;
import junit.framework.TestCase;

public class CentroDistribucionTest extends TestCase {

    public void testRegistrarYBuscarPaquete() {
        CentroDistribucion centro = new CentroDistribucion();

        Paquete paquete = new Paquete(
                "PK001", "Armenia", 5.5, 5, 30
        );

        centro.registrarPaquete(paquete);

        assertSame(paquete, centro.buscarPaquete("PK001"));
        assertEquals(1, centro.cantidadPaquetes());
    }

    public void testBuscarPaqueteInexistente() {
        CentroDistribucion centro = new CentroDistribucion();

        assertNull(centro.buscarPaquete("PK999"));
        assertNull(centro.buscarPaquete(null));
        assertNull(centro.buscarPaquete(""));
    }

    public void testRechazarPaqueteDuplicado() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 2.0, 5, 20)
        );

        try {
            centro.registrarPaquete(
                    new Paquete("PK001", "Calarcá", 3.0, 4, 15)
            );
            fail("Se esperaba IllegalArgumentException");
        } catch (IllegalArgumentException esperado) {
            assertEquals(1, centro.cantidadPaquetes());
        }
    }

    public void testRegistrarYBuscarRepartidor() {
        CentroDistribucion centro = new CentroDistribucion();

        Repartidor repartidor = new Repartidor(
                "R001", "Carlos", "Armenia", true
        );

        centro.registrarRepartidor(repartidor);

        assertSame(repartidor, centro.buscarRepartidor("R001"));
        assertEquals(1, centro.cantidadRepartidores());
    }

    public void testBuscarRepartidorInexistente() {
        CentroDistribucion centro = new CentroDistribucion();

        assertNull(centro.buscarRepartidor("R999"));
        assertNull(centro.buscarRepartidor(null));
        assertNull(centro.buscarRepartidor(""));
    }

    public void testRechazarRepartidorDuplicado() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarRepartidor(
                new Repartidor("R001", "Carlos", "Armenia", true)
        );

        try {
            centro.registrarRepartidor(
                    new Repartidor("R001", "Laura", "Calarcá", true)
            );
            fail("Se esperaba IllegalArgumentException");
        } catch (IllegalArgumentException esperado) {
            assertEquals(1, centro.cantidadRepartidores());
        }
    }

    public void testRechazarPaqueteNull() {
        CentroDistribucion centro = new CentroDistribucion();

        try {
            centro.registrarPaquete(null);
            fail("Se esperaba IllegalArgumentException");
        } catch (IllegalArgumentException esperado) {
            assertEquals(0, centro.cantidadPaquetes());
        }
    }

    public void testRechazarRepartidorNull() {
        CentroDistribucion centro = new CentroDistribucion();

        try {
            centro.registrarRepartidor(null);
            fail("Se esperaba IllegalArgumentException");
        } catch (IllegalArgumentException esperado) {
            assertEquals(0, centro.cantidadRepartidores());
        }
    }
    
    public void testAsignacionExitosa() {
        CentroDistribucion centro = new CentroDistribucion();

        Paquete paquete = new Paquete(
                "PK010", "Armenia", 5.0, 5, 30
        );

        Repartidor repartidor = new Repartidor(
                "R010", "Carlos", "Armenia", true
        );

        centro.registrarPaquete(paquete);
        centro.registrarRepartidor(repartidor);

        assertEquals("PK010", centro.retirarSiguienteFIFO().getCodigo());
        assertTrue(centro.asignarPaquete("PK010"));
        assertEquals(
                "R010",
                centro.obtenerRepartidorAsignado("PK010")
        );
        assertFalse(repartidor.isDisponible());
    }

    public void testNoAsignarSiLaZonaEsDiferente() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK011", "Armenia", 5.0, 5, 30)
        );

        Repartidor repartidor = new Repartidor(
                "R011", "Laura", "Calarcá", true
        );

        centro.registrarRepartidor(repartidor);

        centro.retirarSiguienteFIFO();
        assertFalse(centro.asignarPaquete("PK011"));
        assertTrue(repartidor.isDisponible());
        assertNull(centro.obtenerRepartidorAsignado("PK011"));
    }

    public void testNoAsignarSiElRepartidorEstaOcupado() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK012", "Armenia", 5.0, 5, 30)
        );

        Repartidor repartidor = new Repartidor(
                "R012", "Andrés", "Armenia", false
        );

        centro.registrarRepartidor(repartidor);

        centro.retirarSiguienteFIFO();
        assertFalse(centro.asignarPaquete("PK012"));
        assertFalse(repartidor.isDisponible());
        assertNull(centro.obtenerRepartidorAsignado("PK012"));
    }


    public void testMunicipiosUnicosOrdenadosYAgrupados() {
        CentroDistribucion centro = new CentroDistribucion();
        Paquete p1 = new Paquete("PK030", "Armenia", 2.0, 3, 10);
        Paquete p2 = new Paquete("PK010", "Calarcá", 3.0, 4, 12);
        Paquete p3 = new Paquete("PK020", " armenia ", 4.0, 5, 15);
        centro.registrarPaquete(p1);
        centro.registrarPaquete(p2);
        centro.registrarPaquete(p3);

        assertEquals(2, centro.obtenerMunicipiosOrdenados().size());
        assertEquals("Armenia", centro.obtenerMunicipiosOrdenados().iterator().next());
        assertEquals(2, centro.obtenerPaquetesPorDestino("ARMENIA").size());
        assertEquals(1, centro.obtenerPaquetesPorDestino("Calarca").size());
        assertEquals(0, centro.obtenerPaquetesPorDestino("Pereira").size());
    }

    public void testConservaOrdenRegistroAunqueLosCodigosEstanDesordenados() {
        CentroDistribucion centro = new CentroDistribucion();
        centro.registrarPaquete(new Paquete("PK030", "Armenia", 2.0, 3, 10));
        centro.registrarPaquete(new Paquete("PK010", "Calarcá", 3.0, 4, 12));
        centro.registrarPaquete(new Paquete("PK020", "Pereira", 4.0, 5, 15));

        List<Paquete> orden = centro.obtenerPaquetesEnOrdenRegistro();
        assertEquals("PK030", orden.get(0).getCodigo());
        assertEquals("PK010", orden.get(1).getCodigo());
        assertEquals("PK020", orden.get(2).getCodigo());
    }

    public void testNoPermiteAsignarSinRetirarPrimero() {
        CentroDistribucion centro = new CentroDistribucion();
        centro.registrarPaquete(new Paquete("PK001", "Armenia", 2.0, 3, 10));
        centro.registrarRepartidor(new Repartidor("R001", "Carlos", "Armenia", true));

        assertFalse(centro.asignarPaquete("PK001"));
        assertEquals(EstadoPaquete.PENDIENTE, centro.obtenerEstadoPaquete("PK001"));
        assertTrue(centro.estructurasConsistentes());
    }

    public void testRetirarAsignarEntregarActualizaEstadosYColas() {
        CentroDistribucion centro = new CentroDistribucion();
        Paquete paquete = new Paquete("PK001", "Armenia", 2.0, 3, 10);
        Repartidor repartidor = new Repartidor("R001", "Carlos", "Armenia", true);
        centro.registrarPaquete(paquete);
        centro.registrarRepartidor(repartidor);

        assertEquals(EstadoPaquete.PENDIENTE, centro.obtenerEstadoPaquete("PK001"));
        assertSame(paquete, centro.retirarSiguienteFIFO());
        assertEquals(EstadoPaquete.RETIRADO, centro.obtenerEstadoPaquete("PK001"));
        assertTrue(centro.estructurasConsistentes());
        assertTrue(centro.asignarPaquete("PK001"));
        assertEquals(EstadoPaquete.ASIGNADO, centro.obtenerEstadoPaquete("PK001"));
        assertFalse(repartidor.isDisponible());
        assertTrue(centro.registrarEntrega("PK001"));
        assertEquals(EstadoPaquete.ENTREGADO, centro.obtenerEstadoPaquete("PK001"));
        assertTrue(repartidor.isDisponible());
        assertEquals(1, centro.cantidadEntregas());
        assertTrue(centro.estructurasConsistentes());
        assertFalse(centro.reincorporarPendiente("PK001"));
    }

    public void testPaqueteRetiradoPuedeReincorporarseSiNoSeAsigna() {
        CentroDistribucion centro = new CentroDistribucion();
        centro.registrarPaquete(new Paquete("PK001", "Armenia", 2.0, 3, 10));
        centro.retirarSiguienteFIFO();
        assertEquals(EstadoPaquete.RETIRADO, centro.obtenerEstadoPaquete("PK001"));
        assertTrue(centro.reincorporarPendiente("PK001"));
        assertEquals(EstadoPaquete.PENDIENTE, centro.obtenerEstadoPaquete("PK001"));
        assertEquals("PK001", centro.consultarSiguienteFIFO().getCodigo());
        assertTrue(centro.estructurasConsistentes());
    }

    public void testNoAsignarPaqueteInexistente() {
        CentroDistribucion centro = new CentroDistribucion();

        assertFalse(centro.asignarPaquete("PK999"));
        assertNull(centro.obtenerRepartidorAsignado("PK999"));
    }
    

    public void testEntregaDuplicadaNoAlteraElEstado() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 2.0, 5, 10)
        );

        Repartidor repartidor =
                new Repartidor("R001", "Carlos", "Armenia", true);

        centro.registrarRepartidor(repartidor);
        centro.retirarSiguienteFIFO();

        assertTrue(centro.asignarPaquete("PK001"));
        assertTrue(centro.registrarEntrega("PK001"));

        assertTrue(repartidor.isDisponible());
        assertEquals(1, centro.cantidadEntregas());

        assertFalse(centro.registrarEntrega("PK001"));

        assertEquals(1, centro.cantidadEntregas());
        assertTrue(repartidor.isDisponible());
        assertNull(centro.obtenerRepartidorAsignado("PK001"));
        assertEquals(
                EstadoPaquete.ENTREGADO,
                centro.obtenerEstadoPaquete("PK001")
        );
        assertTrue(centro.estructurasConsistentes());
    }

    public void testConsultasDeRegistroNoPermitenModificarLaLista() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 2.0, 3, 10)
        );

        List<Paquete> registro = centro.obtenerPaquetesEnOrdenRegistro();

        try {
            registro.clear();
            fail("La lista de registro debe ser inmodificable.");
        } catch (UnsupportedOperationException esperado) {
            assertEquals(1, centro.cantidadPaquetes());
        }
    }

    public void testReincorporarNoDuplicaPaquetesEnLasColas() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK001", "Armenia", 2.0, 3, 10)
        );
        centro.registrarPaquete(
                new Paquete("PK002", "Armenia", 3.0, 5, 15)
        );

        assertEquals("PK001", centro.retirarSiguienteFIFO().getCodigo());
        assertTrue(centro.reincorporarPendiente("PK001"));

        assertEquals(2, centro.cantidadPendientesFIFO());
        assertEquals(2, centro.cantidadPendientesPorPrioridad());
        assertEquals("PK001", centro.consultarSiguienteFIFO().getCodigo());
        assertTrue(centro.estructurasConsistentes());

        assertFalse(centro.reincorporarPendiente("PK001"));

        assertEquals(2, centro.cantidadPendientesFIFO());
        assertEquals(2, centro.cantidadPendientesPorPrioridad());
        assertTrue(centro.estructurasConsistentes());
    }


    public void testEstructurasDetectanRepartidorOcupadoSinAsignacion() {
        CentroDistribucion centro = new CentroDistribucion();

        Repartidor repartidor =
                new Repartidor("R001", "Carlos", "Armenia", false);

        centro.registrarRepartidor(repartidor);

        assertFalse(
                "Un repartidor ocupado sin asignación debe detectarse como inconsistencia.",
                centro.estructurasConsistentes()
        );
    }


    public void testAsignacionDuplicadaNoCambiaElRepartidor() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK100", "Armenia", 2.0, 3, 10)
        );

        Repartidor repartidor =
                new Repartidor("R100", "Carlos", "Armenia", true);

        centro.registrarRepartidor(repartidor);
        centro.retirarSiguienteFIFO();

        assertTrue(centro.asignarPaquete("PK100"));
        assertEquals(
                "R100",
                centro.obtenerRepartidorAsignado("PK100")
        );

        assertFalse(centro.asignarPaquete("PK100"));

        assertEquals(
                "R100",
                centro.obtenerRepartidorAsignado("PK100")
        );
        assertFalse(repartidor.isDisponible());
        assertEquals(
                EstadoPaquete.ASIGNADO,
                centro.obtenerEstadoPaquete("PK100")
        );
        assertTrue(centro.estructurasConsistentes());
    }

    public void testNoPermiteEntregarPaqueteSinAsignar() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK101", "Armenia", 2.0, 3, 10)
        );

        assertFalse(centro.registrarEntrega("PK101"));
        assertEquals(
                EstadoPaquete.PENDIENTE,
                centro.obtenerEstadoPaquete("PK101")
        );
        assertEquals(0, centro.cantidadEntregas());
        assertTrue(centro.estructurasConsistentes());

        assertEquals(
                "PK101",
                centro.retirarSiguienteFIFO().getCodigo()
        );

        assertFalse(centro.registrarEntrega("PK101"));
        assertEquals(
                EstadoPaquete.RETIRADO,
                centro.obtenerEstadoPaquete("PK101")
        );
        assertEquals(0, centro.cantidadEntregas());
        assertTrue(centro.estructurasConsistentes());
    }

    public void testAsignacionRechazadaConservaLaConsistencia() {
        CentroDistribucion centro = new CentroDistribucion();

        centro.registrarPaquete(
                new Paquete("PK102", "Armenia", 2.0, 3, 10)
        );

        Repartidor repartidor =
                new Repartidor("R102", "Laura", "Calarcá", true);

        centro.registrarRepartidor(repartidor);

        assertEquals(
                "PK102",
                centro.retirarSiguienteFIFO().getCodigo()
        );

        assertFalse(centro.asignarPaquete("PK102"));

        assertEquals(
                EstadoPaquete.RETIRADO,
                centro.obtenerEstadoPaquete("PK102")
        );
        assertNull(centro.obtenerRepartidorAsignado("PK102"));
        assertTrue(repartidor.isDisponible());
        assertEquals(0, centro.cantidadEntregas());
        assertTrue(centro.estructurasConsistentes());
    }




}
