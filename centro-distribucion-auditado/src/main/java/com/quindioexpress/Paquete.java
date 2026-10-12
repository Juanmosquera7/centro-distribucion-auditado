
package com.quindioexpress;

/**
 * Representa un paquete registrado en QuindíoExpress.
 *
 * El orden natural de los paquetes se establece por código ascendente.
 */
public class Paquete implements Comparable<Paquete> {

    private final String codigo;
    private final String destino;
    private final double peso;
    private final int prioridad;
    private final int tiempoEstimado;

    public Paquete(
            String codigo,
            String destino,
            double peso,
            int prioridad,
            int tiempoEstimado) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException(
                    "El código no puede estar vacío.");
        }

        if (destino == null || destino.isBlank()) {
            throw new IllegalArgumentException(
                    "El destino no puede estar vacío.");
        }

        if (peso <= 0 || Double.isNaN(peso)
                || Double.isInfinite(peso)) {
            throw new IllegalArgumentException(
                    "El peso debe ser un número positivo y finito.");
        }

        if (prioridad < 1 || prioridad > 5) {
            throw new IllegalArgumentException(
                    "La prioridad debe estar entre 1 y 5.");
        }

        if (tiempoEstimado <= 0) {
            throw new IllegalArgumentException(
                    "El tiempo estimado debe ser positivo.");
        }

        this.codigo = codigo.trim();
        this.destino = destino.trim();
        this.peso = peso;
        this.prioridad = prioridad;
        this.tiempoEstimado = tiempoEstimado;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDestino() {
        return destino;
    }

    public double getPeso() {
        return peso;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public int getTiempoEstimado() {
        return tiempoEstimado;
    }

    @Override
    public int compareTo(Paquete otro) {
        return this.codigo.compareTo(otro.codigo);
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "codigo='" + codigo + '\'' +
                ", destino='" + destino + '\'' +
                ", peso=" + peso +
                ", prioridad=" + prioridad +
                ", tiempoEstimado=" + tiempoEstimado +
                '}';
    }
}
