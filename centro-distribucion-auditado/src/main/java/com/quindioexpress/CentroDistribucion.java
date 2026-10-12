
package com.quindioexpress;

import java.text.Normalizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Gestiona paquetes, municipios, repartidores, colas y entregas.
 */
public class CentroDistribucion {

    private final Map<String, Paquete> paquetesPorCodigo;
    private final List<Paquete> ordenRegistro;
    private final List<Repartidor> repartidores;
    private final Map<String, String> asignaciones;
    private final Map<String, EstadoPaquete> estadosPaquetes;

    private final Queue<Paquete> colaFIFO;
    private final PriorityQueue<Paquete> colaPrioridad;

    // Municipio normalizado -> nombre de presentación y paquetes registrados.
    private final Set<String> clavesMunicipios;
    private final TreeSet<String> municipiosOrdenados;
    private final Map<String, List<Paquete>> paquetesPorDestino;
    private final Map<String, String> nombresMunicipios;

    private final ListaEnlazada<Paquete> historialEntregas;
    private final Set<String> codigosEntregados;

    public CentroDistribucion() {
        paquetesPorCodigo = new HashMap<>();
        ordenRegistro = new ArrayList<>();
        repartidores = new ArrayList<>();
        asignaciones = new HashMap<>();
        estadosPaquetes = new HashMap<>();

        colaFIFO = new ArrayDeque<>();
        colaPrioridad = new PriorityQueue<>(
                ComparadoresPaquete.PARA_DESPACHO
        );

        clavesMunicipios = new HashSet<>();
        municipiosOrdenados = new TreeSet<>(
                String.CASE_INSENSITIVE_ORDER
        );
        paquetesPorDestino = new HashMap<>();
        nombresMunicipios = new HashMap<>();

        historialEntregas = new ListaEnlazada<>();
        codigosEntregados = new HashSet<>();
    }

    /**
     * Registra un paquete y actualiza todas las estructuras de registro.
     */
    public void registrarPaquete(Paquete paquete) {
        if (paquete == null) {
            throw new IllegalArgumentException(
                    "El paquete no puede ser null."
            );
        }

        String codigo = paquete.getCodigo();

        if (paquetesPorCodigo.containsKey(codigo)) {
            throw new IllegalArgumentException(
                    "Ya existe un paquete con el código: " + codigo
            );
        }

        String claveDestino = normalizarMunicipio(paquete.getDestino());

        if (clavesMunicipios.add(claveDestino)) {
            String nombreDestino = paquete.getDestino().trim();

            nombresMunicipios.put(claveDestino, nombreDestino);
            municipiosOrdenados.add(nombreDestino);
            paquetesPorDestino.put(
                    claveDestino,
                    new ArrayList<Paquete>()
            );
        }

        paquetesPorCodigo.put(codigo, paquete);
        ordenRegistro.add(paquete);
        estadosPaquetes.put(codigo, EstadoPaquete.PENDIENTE);

        colaFIFO.offer(paquete);
        colaPrioridad.offer(paquete);

        paquetesPorDestino.get(claveDestino).add(paquete);
    }

    public Paquete buscarPaquete(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        return paquetesPorCodigo.get(codigo.trim());
    }

    public List<Paquete> obtenerPaquetesEnOrdenRegistro() {
        return Collections.unmodifiableList(
                new ArrayList<>(ordenRegistro)
        );
    }

    /**
     * Devuelve los municipios únicos en orden alfabético.
     */
    public Set<String> obtenerMunicipiosOrdenados() {
        return Collections.unmodifiableSet(
                new TreeSet<>(municipiosOrdenados)
        );
    }

    /**
     * Devuelve una copia de los paquetes del municipio solicitado.
     */
    public List<Paquete> obtenerPaquetesPorDestino(String destino) {
        if (destino == null || destino.isBlank()) {
            return Collections.emptyList();
        }

        List<Paquete> encontrados =
                paquetesPorDestino.get(normalizarMunicipio(destino));

        if (encontrados == null) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(
                new ArrayList<>(encontrados)
        );
    }

    public EstadoPaquete obtenerEstadoPaquete(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            return null;
        }

        return estadosPaquetes.get(codigo.trim());
    }

    public void registrarRepartidor(Repartidor repartidor) {
        if (repartidor == null) {
            throw new IllegalArgumentException(
                    "El repartidor no puede ser null."
            );
        }

        for (Repartidor registrado : repartidores) {
            if (registrado.getId().equals(repartidor.getId())) {
                throw new IllegalArgumentException(
                        "Ya existe un repartidor con el ID: "
                                + repartidor.getId()
                );
            }
        }

        repartidores.add(repartidor);
    }

    public Repartidor buscarRepartidor(String id) {
        if (id == null || id.isBlank()) {
            return null;
        }

        for (Repartidor repartidor : repartidores) {
            if (repartidor.getId().equals(id.trim())) {
                return repartidor;
            }
        }

        return null;
    }

    /**
     * Solo se asignan paquetes previamente retirados de las colas.
     */
    public boolean asignarPaquete(String codigoPaquete) {
        Paquete paquete = buscarPaquete(codigoPaquete);

        if (paquete == null
                || obtenerEstadoPaquete(paquete.getCodigo())
                        != EstadoPaquete.RETIRADO
                || asignaciones.containsKey(paquete.getCodigo())
                || codigosEntregados.contains(paquete.getCodigo())) {
            return false;
        }

        for (Repartidor repartidor : repartidores) {
            if (repartidor.isDisponible()
                    && normalizarMunicipio(repartidor.getZona())
                            .equals(normalizarMunicipio(
                                    paquete.getDestino()))) {

                asignaciones.put(
                        paquete.getCodigo(),
                        repartidor.getId()
                );

                repartidor.setDisponible(false);

                estadosPaquetes.put(
                        paquete.getCodigo(),
                        EstadoPaquete.ASIGNADO
                );

                return true;
            }
        }

        return false;
    }

    /**
     * Devuelve a las colas un paquete retirado que todavía no fue asignado.
     */
    public boolean reincorporarPendiente(String codigoPaquete) {
        Paquete paquete = buscarPaquete(codigoPaquete);

        if (paquete == null
                || obtenerEstadoPaquete(paquete.getCodigo())
                        != EstadoPaquete.RETIRADO
                || asignaciones.containsKey(paquete.getCodigo())) {
            return false;
        }

        estadosPaquetes.put(
                paquete.getCodigo(),
                EstadoPaquete.PENDIENTE
        );

        reconstruirColasPendientes();

        return true;
    }

    public String obtenerRepartidorAsignado(String codigoPaquete) {
        if (codigoPaquete == null || codigoPaquete.isBlank()) {
            return null;
        }

        return asignaciones.get(codigoPaquete.trim());
    }

    public boolean registrarEntrega(String codigoPaquete) {
        Paquete paquete = buscarPaquete(codigoPaquete);

        if (paquete == null
                || obtenerEstadoPaquete(paquete.getCodigo())
                        != EstadoPaquete.ASIGNADO
                || codigosEntregados.contains(paquete.getCodigo())) {
            return false;
        }

        String codigo = paquete.getCodigo();
        String idRepartidor = asignaciones.get(codigo);

        if (idRepartidor == null) {
            return false;
        }

        historialEntregas.agregar(paquete);
        codigosEntregados.add(codigo);

        estadosPaquetes.put(codigo, EstadoPaquete.ENTREGADO);

        colaFIFO.remove(paquete);
        colaPrioridad.remove(paquete);

        Repartidor repartidor = buscarRepartidor(idRepartidor);

        if (repartidor != null) {
            repartidor.setDisponible(true);
        }

        asignaciones.remove(codigo);

        return true;
    }

    public Paquete obtenerEntrega(int indice) {
        return historialEntregas.obtener(indice);
    }

    public int cantidadEntregas() {
        return historialEntregas.tamaño();
    }

    public boolean historialVacio() {
        return historialEntregas.estaVacia();
    }

    public int cantidadPaquetes() {
        return paquetesPorCodigo.size();
    }

    public int cantidadRepartidores() {
        return repartidores.size();
    }

    public Paquete consultarSiguienteFIFO() {
        return colaFIFO.peek();
    }

    public Paquete consultarSiguientePorPrioridad() {
        return colaPrioridad.peek();
    }

    public Paquete retirarSiguienteFIFO() {
        Paquete paquete = colaFIFO.poll();

        if (paquete != null) {
            colaPrioridad.remove(paquete);

            estadosPaquetes.put(
                    paquete.getCodigo(),
                    EstadoPaquete.RETIRADO
            );
        }

        return paquete;
    }

    public Paquete retirarSiguientePorPrioridad() {
        Paquete paquete = colaPrioridad.poll();

        if (paquete != null) {
            colaFIFO.remove(paquete);

            estadosPaquetes.put(
                    paquete.getCodigo(),
                    EstadoPaquete.RETIRADO
            );
        }

        return paquete;
    }

    public int cantidadPendientesFIFO() {
        return colaFIFO.size();
    }

    public int cantidadPendientesPorPrioridad() {
        return colaPrioridad.size();
    }

    /**
     * Comprueba la consistencia entre paquetes, colas, estados,
     * asignaciones, repartidores, municipios e historial de entregas.
     */
    public boolean estructurasConsistentes() {

        Set<String> codigosRegistrados = new HashSet<>();

        // 1. El registro y el mapa deben contener los mismos paquetes.
        if (ordenRegistro.size() != paquetesPorCodigo.size()
                || estadosPaquetes.size() != paquetesPorCodigo.size()) {
            return false;
        }

        for (Paquete paquete : ordenRegistro) {
            if (paquete == null) {
                return false;
            }

            String codigo = paquete.getCodigo();

            if (!codigosRegistrados.add(codigo)
                    || paquetesPorCodigo.get(codigo) != paquete
                    || !estadosPaquetes.containsKey(codigo)) {
                return false;
            }
        }

        if (!codigosRegistrados.equals(paquetesPorCodigo.keySet())) {
            return false;
        }

        // 2. Ambas colas deben contener los mismos paquetes,
        // sin duplicados ni paquetes que no estén registrados.
        Set<String> codigosFIFO = new HashSet<>();
        Set<String> codigosPrioridad = new HashSet<>();

        for (Paquete paquete : colaFIFO) {
            if (paquete == null
                    || paquetesPorCodigo.get(paquete.getCodigo()) != paquete
                    || !codigosFIFO.add(paquete.getCodigo())) {
                return false;
            }
        }

        for (Paquete paquete : colaPrioridad) {
            if (paquete == null
                    || paquetesPorCodigo.get(paquete.getCodigo()) != paquete
                    || !codigosPrioridad.add(paquete.getCodigo())) {
                return false;
            }
        }

        if (!codigosFIFO.equals(codigosPrioridad)) {
            return false;
        }

        // 3. Cada estado debe coincidir con sus estructuras asociadas.
        int cantidadAsignados = 0;

        Set<String> codigosConAsignacion = new HashSet<>();
        Set<String> repartidoresAsignados = new HashSet<>();

        for (Paquete paquete : ordenRegistro) {
            String codigo = paquete.getCodigo();
            EstadoPaquete estado = estadosPaquetes.get(codigo);

            if (estado == null) {
                return false;
            }

            boolean estaEnColas = codigosFIFO.contains(codigo);
            boolean tieneAsignacion = asignaciones.containsKey(codigo);
            boolean estaEntregado = codigosEntregados.contains(codigo);

            if (estaEnColas != (estado == EstadoPaquete.PENDIENTE)) {
                return false;
            }

            if (tieneAsignacion != (estado == EstadoPaquete.ASIGNADO)) {
                return false;
            }

            if (estaEntregado != (estado == EstadoPaquete.ENTREGADO)) {
                return false;
            }

            if (estado == EstadoPaquete.ASIGNADO) {
                cantidadAsignados++;

                String idRepartidor = asignaciones.get(codigo);
                Repartidor repartidor = buscarRepartidor(idRepartidor);

                if (repartidor == null
                        || repartidor.isDisponible()
                        || !repartidoresAsignados.add(idRepartidor)) {
                    return false;
                }

                codigosConAsignacion.add(codigo);
            }
        }

        // 4. No puede haber asignaciones para paquetes inexistentes.
        if (asignaciones.size() != cantidadAsignados
                || !codigosConAsignacion.equals(asignaciones.keySet())) {
            return false;
        }

        // 5. Cada repartidor ocupado debe tener una asignación
        // y cada repartidor disponible debe carecer de ella.
        Set<String> idsRepartidores = new HashSet<>();

        for (Repartidor repartidor : repartidores) {
            if (repartidor == null
                    || !idsRepartidores.add(repartidor.getId())) {
                return false;
            }

            boolean tieneAsignacion =
                    repartidoresAsignados.contains(repartidor.getId());

            if (repartidor.isDisponible() == tieneAsignacion) {
                return false;
            }
        }

        // 6. El conjunto de entregados debe coincidir con el historial.
        Set<String> codigosHistorial = new HashSet<>();

        if (historialEntregas.tamaño() != codigosEntregados.size()) {
            return false;
        }

        for (int i = 0; i < historialEntregas.tamaño(); i++) {
            Paquete entregado = historialEntregas.obtener(i);

            if (entregado == null
                    || paquetesPorCodigo.get(entregado.getCodigo())
                            != entregado
                    || !codigosHistorial.add(entregado.getCodigo())) {
                return false;
            }

            if (estadosPaquetes.get(entregado.getCodigo())
                    != EstadoPaquete.ENTREGADO) {
                return false;
            }
        }

        if (!codigosHistorial.equals(codigosEntregados)) {
            return false;
        }

        // 7. Las estructuras de municipios deben estar sincronizadas.
        if (!clavesMunicipios.equals(nombresMunicipios.keySet())
                || !clavesMunicipios.equals(paquetesPorDestino.keySet())
                || municipiosOrdenados.size() != clavesMunicipios.size()) {
            return false;
        }

        // Reconstruir los municipios esperados a partir de los paquetes.
        Set<String> municipiosEsperados = new HashSet<>();

        for (Paquete paquete : ordenRegistro) {
            if (paquete.getDestino() == null
                    || paquete.getDestino().isBlank()) {
                return false;
            }

            municipiosEsperados.add(
                    normalizarMunicipio(paquete.getDestino())
            );
        }

        if (!municipiosEsperados.equals(clavesMunicipios)) {
            return false;
        }

        // Cada municipio debe corresponder a su clave normalizada.
        Set<String> nombresRegistrados = new HashSet<>();

        for (String clave : clavesMunicipios) {
            String nombre = nombresMunicipios.get(clave);

            if (nombre == null
                    || !normalizarMunicipio(nombre).equals(clave)
                    || !nombresRegistrados.add(nombre)
                    || !municipiosOrdenados.contains(nombre)) {
                return false;
            }
        }

        // Cada paquete debe aparecer una sola vez en el grupo
        // correspondiente a su destino.
        Set<String> codigosAgrupados = new HashSet<>();

        for (Map.Entry<String, List<Paquete>> entrada
                : paquetesPorDestino.entrySet()) {

            String claveDestino = entrada.getKey();
            List<Paquete> paquetesDestino = entrada.getValue();

            if (claveDestino == null || paquetesDestino == null) {
                return false;
            }

            for (Paquete paquete : paquetesDestino) {
                if (paquete == null
                        || paquetesPorCodigo.get(paquete.getCodigo())
                                != paquete
                        || !normalizarMunicipio(paquete.getDestino())
                                .equals(claveDestino)
                        || !codigosAgrupados.add(paquete.getCodigo())) {
                    return false;
                }
            }
        }

        return codigosAgrupados.equals(paquetesPorCodigo.keySet());
    }

    /**
     * Reconstruye ambas colas usando el orden original de registro.
     */
    private void reconstruirColasPendientes() {
        colaFIFO.clear();
        colaPrioridad.clear();

        for (Paquete registrado : ordenRegistro) {
            if (estadosPaquetes.get(registrado.getCodigo())
                    == EstadoPaquete.PENDIENTE) {

                colaFIFO.offer(registrado);
                colaPrioridad.offer(registrado);
            }
        }
    }

    /**
     * Normaliza un municipio: elimina espacios externos, tildes
     * y diferencias entre mayúsculas y minúsculas.
     */
    private String normalizarMunicipio(String municipio) {
        String sinAcentos = Normalizer
                .normalize(municipio.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return sinAcentos.toUpperCase(Locale.ROOT);
    }
}
