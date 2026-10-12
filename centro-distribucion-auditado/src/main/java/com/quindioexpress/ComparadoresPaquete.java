package com.quindioexpress;

import java.util.Comparator;

/**
 * Comparadores reutilizables para ordenar paquetes
 * según los criterios de QuindíoExpress.
 */
public final class ComparadoresPaquete {

    private ComparadoresPaquete() {
        // Evita crear objetos de esta clase.
    }

    /**
     * Ordena por prioridad de mayor a menor.
     */
    public static final Comparator<Paquete> POR_PRIORIDAD =
            Comparator.comparingInt(Paquete::getPrioridad)
                    .reversed();

    /**
     * Ordena por peso de mayor a menor.
     */
    public static final Comparator<Paquete> POR_PESO =
            Comparator.comparingDouble(Paquete::getPeso)
                    .reversed();

    /**
     * Ordena por tiempo estimado de menor a mayor.
     */
    public static final Comparator<Paquete> POR_TIEMPO =
            Comparator.comparingInt(Paquete::getTiempoEstimado);

    /**
     * Orden de despacho prioritario:
     * 1. Mayor prioridad.
     * 2. Menor tiempo estimado.
     * 3. Código alfabéticamente ascendente.
     */
    public static final Comparator<Paquete> PARA_DESPACHO =
            POR_PRIORIDAD
                    .thenComparingInt(Paquete::getTiempoEstimado)
                    .thenComparing(Paquete::getCodigo);
}
