# QuindíoExpress — cambios de auditoría

## Decisiones aplicadas
- Prioridad válida: entero entre 1 y 5.
- Flujo de despacho: registrar -> pendiente -> retirar de una cola (se elimina de ambas) -> retirado -> asignar repartidor -> asignado -> registrar entrega -> entregado.
- Un paquete retirado sin asignación puede reincorporarse a pendientes mediante `reincorporarPendiente`; al reincorporarlo, las colas se reconstruyen siguiendo el orden original de registro.

## Cambios
- `Paquete`: valida prioridad de 1 a 5.
- `EstadoPaquete`: enum con PENDIENTE, RETIRADO, ASIGNADO y ENTREGADO.
- `CentroDistribucion`: conserva orden de registro, registra municipios únicos normalizados, presenta municipios ordenados, agrupa paquetes por destino, expone estado y valida consistencia real de colas/asignaciones/entregas.
- `App`: muestra municipios y agrupación, conserva el flujo retirar-antes-de-asignar y muestra el resultado de la comprobación de consistencia.
- Tests: adaptados al rango de prioridad y al flujo de despacho; añadidas pruebas de municipios, agrupación, orden, validación de prioridad, transiciones de estado, reincorporación y consistencia.

## Verificación realizada en el entorno de auditoría
- Compilación de todas las clases principales con Java 21: correcta.
- Ejecución de 72 métodos de prueba con un runner local compatible con las aserciones utilizadas: 72 pasaron, 0 fallaron.
- Ejecución de `App`: finalizó y reportó `Estructuras consistentes: SI`.
- Maven no estaba instalado en el entorno de auditoría; ejecutar `mvn clean test` en el equipo del proyecto para confirmar con JUnit/Maven oficial.
