* Se deben cumplir el 100% de los Requerimientos de la lógica de negocio.
* Se deben utilizar correctamente los conceptos de programación orientada a objetos y principios y patrones de diseño.
* Las pruebas deben tener al menos un 50% de cobertura.

Lógica de negocio (etapa 1)
===========================
1) En un calendario se pueden crear, modificar y eliminar eventos y tareas.
2) Tanto los eventos como tareas pueden tener un título y una descripción.
3) Las tareas pueden marcarse como completadas.
4) Los eventos pueden ser:
    * De día completo.
    * Comenzar en una fecha y hora y tener una duración arbitrarias.
      En ambos casos, el evento puede comenzar en un día y terminar en otro.

5) Las tareas no tienen duración, pudiendo ser:
    * De día completo.
    * Tener una fecha y hora de vencimiento.
6) Los eventos se pueden repetir:
    * Con frecuencia diaria, semanal, mensual o anual.
    * En caso de frecuencia diaria, es posible definir un intervalo (ej: “cada 3 días”).
    * En caso de frecuencia semanal, es posible definir los días de la semana (ej.: “todos los martes y jueves”).
    * La repetición puede ser:
        - Infinita.
        - Terminar en una fecha determinada (ej.: hasta el 13 de enero).
        - Terminar luego de una cantidad de repeticiones dada (ej: luego de 20 ocurrencias).
    * Al modificar o eliminar un evento con repetición, el cambio o eliminación se aplica a todas sus repeticiones.

7) En un evento o tarea se pueden configurar una o más alarmas:
    * La alarma se dispara en un instante de tiempo, que se puede determinar de dos maneras:
        - Una fecha y hora absoluta
        - Un intervalo de tiempo relativo a la fecha y hora del evento/tarea (ej.: “30 minutos antes”).
    * El efecto producido al dispararse la alarma es configurable:
        - Mostrar una notificación.
        - Reproducir un sonido.
        - Enviar un email.
          Nota: dado que en la primera etapa no se implementa la interacción con el usuario, no se deben implementar los efectos de las alarmas; pero sí deben tener pruebas asociadas.

Persistencia (Etapa 2)
======================
 * Agregar la capacidad de guardar el estado del calendario y recuperarlo luego. 
   Queda a libre elección el formato de serialización. Se puede diseñar un formato 
   propio (de texto o binario), o se puede usar un formato estándar como XML o JSON.
 * Agregar las pruebas asociadas.



Interfaz Grafica (Etapa 3)
==========================
Implementar una interfaz gráfica para la aplicación, utilizando JavaFX.

1) En la aplicación se puede ver los eventos y tareas con tres rangos de tiempo:
   - Un día 
   - Una semana (lunes a domingo o domingo a sábado)
   - Un mes
2) Por defecto se muestran las tareas y eventos de hoy (o de la semana o mes actuales, según el rango elegido).
3) Hay botones para ver el siguiente o anterior día (o semana o mes).
4) En el listado de eventos y tareas se debe ver claramente:
   * El título 
   * La fecha y hora de inicio y fin (o vencimiento) de los eventos y tareas que no son de día completo 
   * La fecha de los eventos y tareas que son de día completo (deben estar diferenciados de los que no son de día completo)
   * Si las tareas están completadas o no 
   * En el listado se muestran también los eventos que tienen repetición (y son visualmente indistinguibles de los que no tienen repetición)
5) Al hacer click en un evento o tarea veo sus propiedades detalladas: título, fechas, alarmas, repetición, etc.
6) La aplicación permite crear un evento o tarea nuevos, eligiendo su título, fechas, alarmas, repetición.
   - Como mínimo puedo elegir:
     * sin repetición 
     * repetición diaria con intervalo
   - Como mínimo puedo agregar alarmas de tipo “notificación”, y con un intervalo relativo.
7) Alarmas:
   - Las alarmas de tipo “notificación” deben ser disparadas en los momentos correspondientes, mostrando una alerta al usuario.


(*) Persistencia  
1) Se puede abrir la aplicación, hacer cualquier cambio (agregar, quitar o modificar eventos y tareas) y cerrar la aplicación.
La próxima vez que abra la aplicación, todos los cambios se ven reflejados.
2) No hay ningún botón para guardar o cargar los datos. La persistencia ocurre automáticamente.
3) Los datos se guardan en el directorio actual, que es equivalente a lo que devuelve System.getProperty("user.dir")
En caso de crear más de un archivo, agruparlos en una sub carpeta.
No se puede leer o escribir ningún archivo fuera del directorio actual.









