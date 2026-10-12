package com.quindioexpress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Operaciones de búsqueda sobre paquetes.
 */
public final class BusquedaPaquetes {

    private BusquedaPaquetes() {
        // Evita crear instancias de esta clase.
    }

    /**
     * Busca un paquete mediante búsqueda binaria por código.
     *
     * Crea una copia y la ordena por el orden natural de Paquete,
     * por lo que no modifica la lista recibida.
     *
     * Complejidad total: O(n log n) por la ordenación
     * y O(log n) por la búsqueda.
     */
    public static Paquete buscarPorCodigo(
            List<Paquete> paquetes,
            String codigo) {

        if (paquetes == null) {
            throw new IllegalArgumentException(
                    "La lista de paquetes no puede ser null."
            );
        }

        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        List<Paquete> ordenados = new ArrayList<>(paquetes);
        Collections.sort(ordenados);

        String codigoBuscado = codigo.trim();

        int izquierda = 0;
        int derecha = ordenados.size() - 1;

        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;

            Paquete paqueteMedio = ordenados.get(medio);
            int comparacion =
                    paqueteMedio.getCodigo().compareTo(codigoBuscado);

            if (comparacion == 0) {
                return paqueteMedio;
            }

            if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }

        return null;
    }
}
