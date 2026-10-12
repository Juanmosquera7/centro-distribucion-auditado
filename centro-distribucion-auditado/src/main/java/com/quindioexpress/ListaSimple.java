
package com.quindioexpress;

/**
 * Define las operaciones básicas de una lista genérica.
 */
public interface ListaSimple<T> {

    void agregar(T elemento);

    T obtener(int indice);

    T eliminar(int indice);

    int tamaño();

    boolean estaVacia();
}
