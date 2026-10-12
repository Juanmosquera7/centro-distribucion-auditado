
package com.quindioexpress;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Lista simplemente enlazada genérica implementada desde cero.
 */
public class ListaEnlazada<T>
        implements ListaSimple<T>, Iterable<T> {

    private static class Nodo<E> {
        private final E valor;
        private Nodo<E> siguiente;

        private Nodo(E valor) {
            this.valor = valor;
            this.siguiente = null;
        }
    }

    private Nodo<T> cabeza;
    private Nodo<T> cola;
    private int cantidad;

    public ListaEnlazada() {
        cabeza = null;
        cola = null;
        cantidad = 0;
    }

    @Override
    public void agregar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);

        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            cola = nuevo;
        }

        cantidad++;
    }

    @Override
    public T obtener(int indice) {
        validarIndice(indice);

        Nodo<T> actual = cabeza;

        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }

        return actual.valor;
    }

    @Override
    public T eliminar(int indice) {
        validarIndice(indice);

        Nodo<T> eliminado;

        if (indice == 0) {
            eliminado = cabeza;
            cabeza = cabeza.siguiente;

            if (cantidad == 1) {
                cola = null;
            }
        } else {
            Nodo<T> anterior = cabeza;

            for (int i = 0; i < indice - 1; i++) {
                anterior = anterior.siguiente;
            }

            eliminado = anterior.siguiente;
            anterior.siguiente = eliminado.siguiente;

            if (eliminado == cola) {
                cola = anterior;
            }
        }

        cantidad--;
        return eliminado.valor;
    }

    @Override
    public int tamaño() {
        return cantidad;
    }

    @Override
    public boolean estaVacia() {
        return cantidad == 0;
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException(
                    "Índice: " + indice
                    + ", tamaño de la lista: " + cantidad);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Nodo<T> actual = cabeza;

            @Override
            public boolean hasNext() {
                return actual != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException(
                            "No hay más elementos en la lista.");
                }

                T valor = actual.valor;
                actual = actual.siguiente;
                return valor;
            }
        };
    }
}
