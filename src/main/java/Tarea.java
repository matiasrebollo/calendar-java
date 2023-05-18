import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Tarea extends ElementoCalendario{
    private boolean completada;

    public Tarea(String titulo, String descripcion, LocalDate fecha, boolean todoElDia, LocalTime horario, Frecuencia frecuencia) {
        super(titulo,descripcion,fecha,todoElDia,horario,frecuencia);
        this.completada = false;
    }

    /**
     * Si la tarea está completada, la marca como no completada
     * si la tarea no está completada, la marca como completada
     * Devuelve true si al final queda como completada o false en caso contrario
     */
    public boolean marcarTareaCompletada() {
        if (!this.completada) {
            this.completada = true;
        }
        else {
            this.completada = false;
        }
        return this.completada;
    }


    @JsonCreator
    private Tarea(@JsonProperty("titulo") String titulo,
                 @JsonProperty("descripcion") String descripcion,
                 @JsonProperty("fechaInicio") LocalDate fechaInicio,
                 @JsonProperty("todoElDia") boolean todoElDia,
                 @JsonProperty("horaInicio") LocalTime horaInicio,
                 @JsonProperty("alarmas") ArrayList<Alarma> alarmas,
                 @JsonProperty("completada") boolean completada) {
        super(titulo, descripcion, fechaInicio, todoElDia, horaInicio,null);
        super.copiarAlarmas(alarmas);
        this.completada = completada;
    }
}


