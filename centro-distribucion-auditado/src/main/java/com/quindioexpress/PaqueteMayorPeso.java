package com.quindioexpress;

import java.util.List;

/**
 * Encuentra el paquete de mayor peso usando divide y vencerás.
 */
public final class PaqueteMayorPeso {

    private PaqueteMayorPeso() {
        // Evita crear instancias de esta clase.
    }

    /**
     * Devuelve el paquete de mayor peso de una lista.
     *
     * Complejidad temporal: O(n).
     * Complejidad espacial: O(log n) por la recursión balanceada.
     */
    public static Paquete encontrar(List<Paquete> paquetes) {
        if (paquetes == null) {
            throw new IllegalArgumentException(
                    "La lista de paquetes no puede ser null."
            );
        }

        if (paquetes.isEmpty()) {
            return null;
        }

        return encontrar(paquetes, 0, paquetes.size() - 1);
    }

    private static Paquete encontrar(
            List<Paquete> paquetes,
            int inicio,
            int fin) {

        // Caso base: queda un solo paquete.
        if (inicio == fin) {
            return paquetes.get(inicio);
        }

        // Dividir el intervalo en dos mitades.
        int medio = inicio + (fin - inicio) / 2;

        Paquete mayorIzquierda =
                encontrar(paquetes, inicio, medio);

        Paquete mayorDerecha =
                encontrar(paquetes, medio + 1, fin);

        // Combinar: conservar el paquete más pesado.
        if (mayorIzquierda.getPeso() >= mayorDerecha.getPeso()) {
            return mayorIzquierda;
        }

        return mayorDerecha;
    }
}