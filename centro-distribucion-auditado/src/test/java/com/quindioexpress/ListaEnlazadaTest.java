
package com.quindioexpress;

import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.TestCase;

public class ListaEnlazadaTest extends TestCase {

    public void testListaVacia() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();

        assertTrue(lista.estaVacia());
        assertEquals(0, lista.tamaño());
    }

    public void testAgregarYObtener() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(3, lista.tamaño());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    public void testEliminarCabeza() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");

        assertEquals("A", lista.eliminar(0));
        assertEquals(1, lista.tamaño());
        assertEquals("B", lista.obtener(0));
    }

    public void testEliminarCola() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");

        assertEquals("B", lista.eliminar(1));
        assertEquals(1, lista.tamaño());
        assertEquals("A", lista.obtener(0));
    }

    public void testEliminarUnicoElemento() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();

        lista.agregar("A");

        assertEquals("A", lista.eliminar(0));
        assertTrue(lista.estaVacia());
        assertEquals(0, lista.tamaño());

        // La lista debe seguir funcionando después de vaciarse.
        lista.agregar("B");
        assertEquals("B", lista.obtener(0));
    }

    public void testIterador() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");

        Iterator<String> iterador = lista.iterator();

        assertTrue(iterador.hasNext());
        assertEquals("A", iterador.next());
        assertTrue(iterador.hasNext());
        assertEquals("B", iterador.next());
        assertFalse(iterador.hasNext());

        try {
            iterador.next();
            fail("Se esperaba NoSuchElementException");
        } catch (NoSuchElementException esperado) {
            // Comportamiento esperado.
        }
    }

    public void testIndiceInvalido() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();
        lista.agregar("A");

        try {
            lista.obtener(1);
            fail("Se esperaba IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException esperado) {
            // Comportamiento esperado.
        }

        try {
            lista.eliminar(-1);
            fail("Se esperaba IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException esperado) {
            // Comportamiento esperado.
        }
    }
}
