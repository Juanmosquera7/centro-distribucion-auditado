&#x20;QuindíoExpress — Centro de Distribución



&#x20;Descripción



QuindíoExpress es una aplicación de consola desarrollada en Java que simula la gestión de un centro de distribución de paquetes. El sistema permite registrar paquetes y repartidores, consultar información, organizar paquetes según diferentes criterios de despacho, asignar entregas y mantener un historial de los paquetes entregados.



El proyecto tiene como objetivo aplicar estructuras de datos, algoritmos de búsqueda, ordenamiento y recursividad en un caso práctico de gestión de información.



&#x20;Funcionalidades



\- Registro y consulta de paquetes mediante su código identificador.

\- Registro y consulta de repartidores.

\- Organización de paquetes por destino.

\- Gestión de paquetes pendientes mediante una cola FIFO.

\- Gestión de paquetes por prioridad mediante una cola de prioridad.

\- Asignación de paquetes a repartidores según su zona de distribución y disponibilidad.

\- Control de estados de los paquetes.

\- Registro del historial de entregas.

\- Consulta de municipios ordenados.

\- Búsqueda binaria de paquetes por código.

\- Cálculo recursivo del peso total por destino.

\- Conteo recursivo de paquetes según una prioridad mínima.

\- Identificación del paquete de mayor peso mediante divide y vencerás.

\- Verificación de la consistencia de las estructuras de datos.



Tecnologías



\- Java

\- Apache Maven

\- JUnit 3.8.1

\- Java Collections Framework



&#x20;Estructuras de datos utilizadas



| Estructura | Uso en el sistema |

|---|---|

| `HashMap` | Asociar códigos con paquetes y administrar diferentes relaciones del sistema. |

| `ArrayList` | Conservar el orden de registro y almacenar colecciones de objetos. |

| `ArrayDeque` | Implementar la cola FIFO de paquetes pendientes. |

| `PriorityQueue` | Organizar paquetes según los criterios de despacho. |

| `HashSet` | Controlar claves de municipios sin duplicados. |

| `TreeSet` | Mantener los municipios ordenados y sin repeticiones. |

| `ListaEnlazada<T>` | Implementar una lista simplemente enlazada genérica para el historial de entregas. |



&#x20;Criterios de despacho



La cola de prioridad utiliza los siguientes criterios, en este orden:



1\. Mayor prioridad.

2\. Menor tiempo estimado.

3\. Código del paquete en orden ascendente para desempatar.



La prioridad de los paquetes se encuentra dentro del rango de 1 a 5.



&#x20;Requisitos



\- Tener instalado un JDK compatible con la configuración del proyecto.

\- Tener Apache Maven instalado.

\- Disponer de una terminal ubicada en la raíz del proyecto.



&#x20;Ejecución



Para compilar el proyecto y ejecutar las pruebas unitarias:



```bash

mvn test

```



Para ejecutar la aplicación principal:



```bash

mvn exec:java "-Dexec.mainClass=com.quindioexpress.App"

```



La ejecución de la aplicación utiliza los datos de demostración definidos en el proyecto.



&#x20;Pruebas unitarias



En la ejecución de validación realizada durante el desarrollo se obtuvieron los siguientes resultados:



\- Pruebas ejecutadas: 79.

\- Fallos: 0.

\- Errores: 0.

\- Pruebas omitidas: 0.



Estos resultados corresponden a la ejecución registrada durante la revisión del proyecto y pueden comprobarse nuevamente con `mvn test`.



&#x20;Resultados de la demostración



En la ejecución de demostración registrada se obtuvieron los siguientes resultados:



\- Paquetes registrados: 30.

\- Repartidores registrados: 6.

\- Paquetes destinados a Armenia: 47.0 kg en total.

\- Paquetes con prioridad igual o superior a 4: 12.

\- Paquete de mayor peso: PK019, con 20.0 kg.

\- Paquete PK015 localizado mediante búsqueda: destino Circasia, peso de 16.0 kg, prioridad 1 y tiempo estimado 25.

\- Entregas registradas: 3.

\- Verificación final de consistencia de las estructuras: correcta.



Los resultados anteriores corresponden a la demostración ejecutada durante el desarrollo; pueden variar si se modifican los datos de prueba.



&#x20;Organización general del código



El proyecto contiene modelos para representar paquetes y repartidores, una clase central para administrar el centro de distribución, comparadores para establecer el orden de despacho, algoritmos de búsqueda y recursividad, una lista enlazada genérica y pruebas unitarias.



&#x20;Autoría y contexto académico



Proyecto académico desarrollado para aplicar los conceptos de estructuras de datos y algoritmos en Java.





