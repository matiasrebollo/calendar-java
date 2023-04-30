import java.time.LocalDate;
import java.time.LocalTime;

public class Tarea2 extends ElementoCalendario{
    private boolean completada;

    public Tarea2(String titulo, String descripcion, LocalDate fecha, boolean todoElDia, LocalTime horario, Frecuencia frecuencia) {
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
}


