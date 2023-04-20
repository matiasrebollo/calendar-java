import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;

import static java.time.temporal.ChronoUnit.*;

public class Tarea implements Frecuencia{
    private String titulo;
    private String descripcion;
    private boolean todoElDia;
    private LocalDate fecha;
    private LocalTime horario;
    private boolean completada;
    //FALTA EL TEMA DE LAS ALARMAS
    private FrecuenciaC frecuencia;


    public Tarea(String titulo, String descripcion, LocalDate fecha, boolean todoElDia, LocalTime horario, FrecuenciaC frecuencia) {
        this.completada = false;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.frecuencia = frecuencia;
        if (frecuencia == null) {
            this.frecuencia = new FrecuenciaC(TipoFrecuencia.CERO, fecha,1, null);
        }
        this.horario = horario;
        this.todoElDia = todoElDia;
        if (todoElDia) {
            this.horario = LocalTime.MIDNIGHT;
        }

    }

    public void modificarHorario(LocalTime nuevoHorario) {
        this.horario = nuevoHorario;
        this.todoElDia = false;
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
    public void modificarFrecuencia(FrecuenciaC frecuencia){
        this.frecuencia = frecuencia;
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
    public String getDescripcion() {
        return descripcion;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public FrecuenciaC getFrecuencia(){
        return frecuencia;
}
    public LocalTime getHorario() {
        return horario;
    }

    /**
     * Devuelve true si la tarea ocurrirá en la fecha recibida
     * */
//soy mati, cambie el coinciden fechas por el fecha esta incluida porque tiraba error
    public boolean ocurreEnFecha(LocalDate fechaCualqueira){
        return frecuencia.fechaEstaIncluida(fechaCualqueira);
    }
}



