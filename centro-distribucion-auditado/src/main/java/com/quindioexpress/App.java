package com.quindioexpress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Punto de entrada de la aplicacion QuindioExpress.
 * Demuestra las estructuras de datos y operaciones del parcial.
 */
public class App {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("          QUINDIOEXPRESS");
        System.out.println("  Sistema de Centro de Distribucion");
        System.out.println("========================================");

        List<Paquete> paquetes = crearPaquetesDePrueba();

        // 1. Registrar paquetes y repartidores.
        CentroDistribucion centro = new CentroDistribucion();

        for (Paquete paquete : paquetes) {
            centro.registrarPaquete(paquete);
        }

        registrarRepartidores(centro);

        System.out.println("\n1. RESUMEN DEL CENTRO");
        System.out.println("Paquetes registrados: "
                + centro.cantidadPaquetes());
        System.out.println("Repartidores registrados: "
                + centro.cantidadRepartidores());

        // 2. Mostrar municipios únicos y paquetes agrupados por destino.
        System.out.println("\n2. MUNICIPIOS Y PAQUETES POR DESTINO");
        for (String municipio : centro.obtenerMunicipiosOrdenados()) {
            System.out.println(municipio + " -> "
                    + centro.obtenerPaquetesPorDestino(municipio).size()
                    + " paquetes");
        }
        System.out.println("Orden original de registro: ");
        for (Paquete paquete : centro.obtenerPaquetesEnOrdenRegistro()) {
            System.out.print(paquete.getCodigo() + " ");
        }
        System.out.println();

        // 3. Mostrar algunos paquetes.
        System.out.println("\n3. MUESTRA DE PAQUETES");

        for (int i = 0; i < Math.min(5, paquetes.size()); i++) {
            System.out.println(paquetes.get(i));
        }

        // 3. Buscar paquete mediante busqueda binaria.
        System.out.println("\n4. BUSQUEDA POR CODIGO");

        Paquete encontrado =
                BusquedaPaquetes.buscarPorCodigo(paquetes, "PK015");

        if (encontrado != null) {
            System.out.println("Paquete encontrado: " + encontrado);
        } else {
            System.out.println("No se encontro el paquete PK015.");
        }

        // 4. Encontrar el paquete de mayor peso mediante divide y venceras.
        System.out.println("\n5. PAQUETE DE MAYOR PESO");

        Paquete mayor = PaqueteMayorPeso.encontrar(paquetes);

        if (mayor != null) {
            System.out.println("Paquete mas pesado: " + mayor);
        } else {
            System.out.println("No hay paquetes registrados.");
        }

        // 5. Sumar pesos recursivamente por destino.
        System.out.println("\n6. PESO TOTAL POR DESTINO");

        double pesoArmenia =
                OperacionesRecursivas.sumarPesoPorDestino(
                        paquetes, "Armenia");

        System.out.println("Peso destinado a Armenia: "
                + pesoArmenia + " kg");

        // 6. Contar paquetes que cumplen una prioridad minima.
        System.out.println("\n7. PAQUETES DE PRIORIDAD ALTA");

        int cantidadAlta =
                OperacionesRecursivas.contarPorPrioridadMinima(
                        paquetes, 4);

        System.out.println("Paquetes con prioridad >= 4: "
                + cantidadAlta);

        // 7. Orden natural de los paquetes por codigo.
        System.out.println("\n8. ORDEN NATURAL POR CODIGO");

        List<Paquete> ordenados = new ArrayList<>(paquetes);
        Collections.sort(ordenados);

        for (Paquete paquete : ordenados) {
            System.out.print(paquete.getCodigo() + " ");
        }

        System.out.println();

        // 8. Asignar paquetes a repartidores compatibles.
        System.out.println("\n9. RETIRO FIFO Y ASIGNACION DE PAQUETES");

        String[] codigosAsignar = {
            "PK001", "PK002", "PK003",
            "PK004", "PK005", "PK006"
        };

        for (String codigo : codigosAsignar) {
            Paquete retirado = centro.retirarSiguienteFIFO();
            if (retirado == null || !retirado.getCodigo().equals(codigo)) {
                System.out.println(codigo + " -> no se pudo retirar en orden FIFO");
                continue;
            }
            boolean asignado = centro.asignarPaquete(codigo);

            if (asignado) {
                String idRepartidor =
                        centro.obtenerRepartidorAsignado(codigo);

                Repartidor repartidor =
                        centro.buscarRepartidor(idRepartidor);

                if (repartidor != null) {
                    System.out.println(codigo + " -> "
                            + repartidor.getNombre()
                            + " (zona: " + repartidor.getZona() + ")");
                } else {
                    System.out.println(codigo
                            + " -> asignacion registrada, repartidor no encontrado");
                }
            } else {
                System.out.println(codigo
                        + " -> no se pudo asignar un repartidor compatible");
            }
        }

        // 9. Consultar y retirar el primer paquete en orden FIFO.
        System.out.println("\n10. DESPACHO FIFO");

        Paquete siguienteFIFO = centro.consultarSiguienteFIFO();

        if (siguienteFIFO != null) {
            System.out.println("Siguiente paquete FIFO: "
                    + siguienteFIFO.getCodigo());

            Paquete retiradoFIFO = centro.retirarSiguienteFIFO();

            if (retiradoFIFO != null) {
                System.out.println("Retirado de las colas: "
                        + retiradoFIFO.getCodigo());
            }
        } else {
            System.out.println("No hay paquetes pendientes en FIFO.");
        }

        // 10. Consultar y retirar el paquete de mayor prioridad.
        System.out.println("\n11. DESPACHO POR PRIORIDAD");

        Paquete siguientePrioridad =
                centro.consultarSiguientePorPrioridad();

        if (siguientePrioridad != null) {
            System.out.println("Siguiente por prioridad: "
                    + siguientePrioridad.getCodigo()
                    + " (prioridad: "
                    + siguientePrioridad.getPrioridad() + ")");

            Paquete retiradoPrioridad =
                    centro.retirarSiguientePorPrioridad();

            if (retiradoPrioridad != null) {
                System.out.println("Retirado de las colas: "
                        + retiradoPrioridad.getCodigo());
            }
        } else {
            System.out.println(
                    "No hay paquetes pendientes por prioridad.");
        }

        // 11. Registrar las entregas y mostrar el historial.
        System.out.println("\n12. HISTORIAL DE ENTREGAS");

        // Los paquetes se retiran antes de asignarse; aquí solo se confirma la entrega.
        String[] codigosEntregados = {"PK001", "PK002", "PK003"};
        for (String codigo : codigosEntregados) {
            boolean registrada = centro.registrarEntrega(codigo);
            System.out.println(codigo + ": "
                    + (registrada ? "entrega registrada correctamente"
                    : "no se pudo registrar; verifique asignacion y estado"));
        }

        System.out.println("\nHistorial de entregas:");

        if (centro.cantidadEntregas() == 0) {
            System.out.println("Todavia no hay entregas registradas.");
        } else {
            for (int i = 0; i < centro.cantidadEntregas(); i++) {
                Paquete entrega = centro.obtenerEntrega(i);

                System.out.println((i + 1) + ". "
                        + entrega.getCodigo()
                        + " - " + entrega.getDestino());
            }
        }

        // 12. Resumen final.
        System.out.println("\n13. RESUMEN FINAL");

        System.out.println("Paquetes registrados: "
                + centro.cantidadPaquetes());

        System.out.println("Repartidores registrados: "
                + centro.cantidadRepartidores());

        System.out.println("Pendientes en FIFO: "
                + centro.cantidadPendientesFIFO());

        System.out.println("Pendientes por prioridad: "
                + centro.cantidadPendientesPorPrioridad());

        System.out.println("Entregas registradas: "
                + centro.cantidadEntregas());
        System.out.println("Estructuras consistentes: "
                + (centro.estructurasConsistentes() ? "SI" : "NO"));

        System.out.println("\n========================================");
        System.out.println("Demostracion finalizada.");
        System.out.println("========================================");
    }

    /**
     * Registra seis repartidores con cobertura en las zonas de prueba.
     */
    private static void registrarRepartidores(
            CentroDistribucion centro) {

        centro.registrarRepartidor(
                new Repartidor("R001", "Carlos", "Armenia", true));

        centro.registrarRepartidor(
                new Repartidor("R002", "Laura", "Calarca", true));

        centro.registrarRepartidor(
                new Repartidor("R003", "Andres", "Pereira", true));

        centro.registrarRepartidor(
                new Repartidor("R004", "Sofia", "Montenegro", true));

        centro.registrarRepartidor(
                new Repartidor("R005", "Daniel", "Circasia", true));

        centro.registrarRepartidor(
                new Repartidor("R006", "Valentina", "Armenia", true));
    }

    /**
     * Genera 30 paquetes distribuidos en cinco destinos.
     */
    private static List<Paquete> crearPaquetesDePrueba() {

        String[] destinos = {
            "Armenia",
            "Calarca",
            "Pereira",
            "Montenegro",
            "Circasia"
        };

        List<Paquete> paquetes = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            String codigo = String.format("PK%03d", i);
            String destino = destinos[(i - 1) % destinos.length];
            double peso = 1.0 + (i % 20);
            int prioridad = 1 + (i % 5);
            int tiempoEstimado = 10 + (i % 40);

            paquetes.add(new Paquete(
                    codigo,
                    destino,
                    peso,
                    prioridad,
                    tiempoEstimado
            ));
        }

        return paquetes;
    }
}
