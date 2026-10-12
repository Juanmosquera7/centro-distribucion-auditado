
package com.quindioexpress;

/**
 * Representa a un repartidor de QuindíoExpress.
 */
public class Repartidor {

    private final String id;
    private final String nombre;
    private final String zona;
    private boolean disponible;

    public Repartidor(
            String id,
            String nombre,
            String zona,
            boolean disponible) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException(
                    "El identificador no puede estar vacío.");
        }

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío.");
        }

        if (zona == null || zona.isBlank()) {
            throw new IllegalArgumentException(
                    "La zona no puede estar vacía.");
        }

        this.id = id.trim();
        this.nombre = nombre.trim();
        this.zona = zona.trim();
        this.disponible = disponible;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getZona() {
        return zona;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "Repartidor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", zona='" + zona + '\'' +
                ", disponible=" + disponible +
                '}';
    }
}
