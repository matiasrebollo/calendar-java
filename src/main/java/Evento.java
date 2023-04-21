import java.time.LocalDate;
import java.time.LocalTime;
public class Evento {
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private boolean todoElDia;
    private FrecuenciaC frecuencia;


    public Evento(String titulo, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, LocalTime horarioInicio, LocalTime horarioFin, boolean todoElDia, FrecuenciaC frecuencia){
        this.titulo = titulo;
        this.descripcion= descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        if (todoElDia){
            this.horarioInicio = LocalTime.parse("00:00");
            this.horarioFin = LocalTime.parse("00:00");
            this.todoElDia = true;
        } else {
            this.horarioInicio = horarioInicio;
            this.horarioFin = horarioFin;
            this.todoElDia = false;
        }
        this.frecuencia = frecuencia;
        if (frecuencia == null){
            this.frecuencia = new FrecuenciaC(Frecuencia.TipoFrecuencia.CERO, fechaInicio,1, null);
        }

    }
    //en las funciones de modificar falta que haya algun retorno para poder hacer las pruebas
    public void modificarTitulo(String nuevoTitulo){
        this.titulo = nuevoTitulo;
    }
    public void modificarDescripcion(String nuevaDescripcion){
        this.descripcion = nuevaDescripcion;
    }

    public void modificarFecha(LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin){
        if (nuevaFechaInicio == null){
            this.fechaFin = nuevaFechaFin;
        } else if (nuevaFechaFin == null){
            this.fechaInicio = nuevaFechaInicio;
        } else {
            this.fechaInicio = nuevaFechaInicio;
            this.fechaFin = nuevaFechaFin;
        }
    }
    public void modificarHorario(LocalTime nuevoHorarioInicio, LocalTime nuevoHorarioFin){
        if (nuevoHorarioInicio == null){
            this.horarioFin = nuevoHorarioFin;
        } else if (nuevoHorarioFin == null){
            this.horarioInicio = nuevoHorarioInicio;
        } else {
            this.horarioInicio = nuevoHorarioInicio;
            this.horarioFin= nuevoHorarioFin;
        }
        this.todoElDia = nuevoHorarioInicio == LocalTime.MIDNIGHT && nuevoHorarioFin == LocalTime.MIDNIGHT;

    }

    public void modificarFrecuencia(FrecuenciaC frecuencia){
        this.frecuencia = frecuencia;
    }

    /*private boolean validarHorario(LocalTime nuevoHorarioInicio, LocalTime nuevoHorarioFin){
        int horaInicio = nuevoHorarioInicio.getHour();
        int horaFin = nuevoHorarioFin.getHour();
        int minutoInicio = nuevoHorarioInicio.getMinute();
        int minutoFin = nuevoHorarioFin.getMinute();
        //ver porque la condicion tira ese warning
        if (horaInicio < 0 || horaFin < 0 || horaInicio > 23 || horaFin > 23){
            return false;
        }
        if (minutoInicio < 0 || minutoFin < 0 || minutoInicio > 59 || minutoFin < 0){
            return false;
        }
        return true;
    }*/

    public String getTitulo() {
        return titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public LocalDate getFechaInicio(){
        return fechaInicio;
    }
    public LocalDate getFechaFin(){
        return fechaFin;
    }
    public LocalTime getHorarioInicio(){
        return horarioInicio;
    }
    public LocalTime getHorarioFin(){
        return horarioFin;
    }
    public FrecuenciaC getFrecuencia(){
        return frecuencia;
    }
}
