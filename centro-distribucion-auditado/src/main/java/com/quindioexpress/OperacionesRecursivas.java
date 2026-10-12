package com.quindioexpress;

import java.util.List;

/**
 * Operaciones recursivas sobre colecciones de paquetes.
 */
public final class OperacionesRecursivas {

    private OperacionesRecursivas() {
        // Evita instanciar esta clase de utilidades.
    }

    /**
     * Suma recursivamente el peso de los paquetes
     * que pertenecen al destino indicado.
     */
    public static double sumarPesoPorDestino(
            List<Paquete> paquetes,
            String destino) {

        if (paquetes == null) {
            throw new IllegalArgumentException(
                    "La lista de paquetes no puede ser null."
            );
        }

        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException(
                    "El destino no puede estar vacío."
            );
        }

        return sumarPesoPorDestino(
                paquetes,
                destino.trim(),
                0
        );
    }

    private static double sumarPesoPorDestino(
            List<Paquete> paquetes,
            String destino,
            int indice) {

        // Caso base: se recorrieron todos los paquetes.
        if (indice >= paquetes.size()) {
            return 0.0;
        }

        Paquete paquete = paquetes.get(indice);
        double pesoActual = 0.0;

        if (paquete.getDestino().equalsIgnoreCase(destino)) {
            pesoActual = paquete.getPeso();
        }

        // Caso recursivo: peso actual + resultado del resto.
        return pesoActual
                + sumarPesoPorDestino(
                        paquetes,
                        destino,
                        indice + 1
                );
    }

    /**
     * Cuenta recursivamente los paquetes cuya prioridad
     * sea mayor o igual al umbral indicado.
     */
    public static int contarPorPrioridadMinima(
            List<Paquete> paquetes,
            int prioridadMinima) {

        if (paquetes == null) {
            throw new IllegalArgumentException(
                    "La lista de paquetes no puede ser null."
            );
        }

        return contarPorPrioridadMinima(
                paquetes,
                prioridadMinima,
                0
        );
    }

    private static int contarPorPrioridadMinima(
            List<Paquete> paquetes,
            int prioridadMinima,
            int indice) {

        // Caso base: se recorrieron todos los paquetes.
        if (indice >= paquetes.size()) {
            return 0;
        }

        Paquete paquete = paquetes.get(indice);

        int cuentaActual =
                paquete.getPrioridad() >= prioridadMinima ? 1 : 0;

        // Caso recursivo: cuenta actual + resultado del resto.
        return cuentaActual
                + contarPorPrioridadMinima(
                        paquetes,
                        prioridadMinima,
                        indice + 1
                );
    }
}
