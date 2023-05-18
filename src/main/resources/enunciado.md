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
    * Comenzar en una fecha y hora y tener una duración arbitrarios.
      En ambos casos, el evento puede comenzar en un día y terminar en otro.

5) Las tareas no tienen duración, pudiendo ser:
    * De día completo.
    * Tener una fecha y hora de vencimiento.
6) Los eventos se pueden repetir:
    * Con frecuencia diaria, semanal, mensual o anual.
    * En caso de frecuencia diaria, es posible definir un intervalo (ej: “cada 3 días”).
    * En caso de frecuencia semanal, es posible definir los días de la semana (ej: “todos los martes y jueves”).
    * La repetición puede ser:
        - Infinita.
        - Terminar en una fecha determinada (ej: hasta el 13 de enero).
        - Terminar luego de una cantidad de repeticiones dada (ej: luego de 20 ocurrencias).
    * Al modificar o eliminar un evento con repetición, el cambio o eliminación se aplica a todas sus repticiones.

7) En un evento o tarea se pueden configurar una o más alarmas:
    * La alarma se dispara en un instante de tiempo, que se puede determinar de dos maneras:
        - Una fecha y hora absoluta
        - Un intervalo de tiempo relativo a la fecha y hora del evento/tarea (ej: “30 minutos antes”).
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