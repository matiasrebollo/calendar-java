import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Tarea {
    private static final int ERROR = -1;
    private static final int OK = 0;
    private String titulo;
    private String descripcion;
    private boolean todoElDia;
    private LocalDate fecha;
    private LocalTime horario;

    private boolean completada;


    public Tarea(String titulo, String descripcion, LocalDate fecha, boolean todoElDia) {
        this.completada = false;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        if (todoElDia) {
            this.todoElDia = true;
        }
        else {
            this.todoElDia = false;
            horario = LocalTime.now(); //hora actual (valor por defecto)
         }
    }
    public Tarea(String titulo, String descripcion, LocalDate fecha, boolean todoElDia, LocalTime horario) {
        this.completada = false;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        if (todoElDia) {
            this.todoElDia = true;
        }
        else {
            this.todoElDia = false;
            horario = horario;
        }
    }


    public void modificarHorario(LocalTime nuevoHorario) {
        this.horario = nuevoHorario;
    }
    public void modificarFecha(LocalDate fechaNueva) {
        this.fecha = fechaNueva;
    }
    public void modificarTitulo(String tituloNuevo) {
        this.titulo = tituloNuevo;
    }
    public void modificarDescripcion(String descripcionNueva) {
        this.descripcion = descripcionNueva;
    }
    /**
     * Si la tarea está completada, la marca como no completada
     * si la tarea no está completada, la marca como completada
     * Devuelve true si al final queda como completada o false en caso contrario
     */
    public boolean marcarTareaCompletada() {
        if (this.completada == false) {
            this.completada = true;
        }
        else {
            this.completada = false;
        }
        return this.completada;
    }


    public String getTitulo() {
        return titulo;
    }
}



