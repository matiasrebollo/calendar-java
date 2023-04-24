import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Evento {
    private String titulo;
    private String descripcion;

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;

    //private LocalDate fechaInicio;
    //private LocalDate fechaFin;
    //private LocalTime horarioInicio;
    //private LocalTime horarioFin;
    private boolean todoElDia;
    private FrecuenciaC frecuencia;
    private ArrayList<Alarma> alarmas = new ArrayList<>();


    public Evento(String titulo, String descripcion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, boolean todoElDia, FrecuenciaC frecuencia){
        this.titulo = titulo;
        this.descripcion= descripcion;
        //this.fechaInicio = fechaInicio;
        //this.fechaFin = fechaFin;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        if (todoElDia){
            this.fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), LocalTime.MIN);
            this.fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), LocalTime.MAX);
            //this.horarioInicio = LocalTime.MIN;
            //this.horarioFin = LocalTime.MAX;
            this.todoElDia = true;
        } else {
            //this.horarioInicio = horarioInicio;
            //this.horarioFin = horarioFin;
            this.todoElDia = false;
        }
        this.frecuencia = frecuencia;
        if (frecuencia == null){
            this.frecuencia = new FrecuenciaC(Frecuencia.TipoFrecuencia.CERO, fechaHoraInicio.toLocalDate(),1, null);
        }

    }

    public void modificarTitulo(String nuevoTitulo){
        this.titulo = nuevoTitulo;
    }
    public void modificarDescripcion(String nuevaDescripcion){
        this.descripcion = nuevaDescripcion;
    }


    private void modificarTodasLasAlarmas() {
        for (Alarma alarma : alarmas) {
            alarma.actualizarFechaHoraAlarma(fechaHoraInicio);
        }
    }
    public void modificarFecha(LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin){
        if (nuevaFechaInicio == null){
            fechaHoraFin = LocalDateTime.of(nuevaFechaFin, fechaHoraFin.toLocalTime());
            //this.fechaFin = nuevaFechaFin;
        } else if (nuevaFechaFin == null){
            fechaHoraInicio = LocalDateTime.of(nuevaFechaInicio, fechaHoraInicio.toLocalTime());
            //this.fechaInicio = nuevaFechaInicio;
        } else {
            //this.fechaInicio = nuevaFechaInicio;
            //this.fechaFin = nuevaFechaFin;
            fechaHoraInicio = LocalDateTime.of(nuevaFechaInicio, fechaHoraInicio.toLocalTime());
            fechaHoraFin = LocalDateTime.of(nuevaFechaFin, fechaHoraFin.toLocalTime());
        }
        modificarTodasLasAlarmas();
        this.frecuencia.setFechaInicio(nuevaFechaInicio);
    }
    public void modificarHorario(LocalTime nuevoHorarioInicio, LocalTime nuevoHorarioFin){
        if (nuevoHorarioInicio == null){
            //this.horarioFin = nuevoHorarioFin;
            fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), nuevoHorarioFin);

        } else if (nuevoHorarioFin == null){
            //this.horarioInicio = nuevoHorarioInicio;
            fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), nuevoHorarioInicio);

        } else {
            //this.horarioInicio = nuevoHorarioInicio;
            //this.horarioFin= nuevoHorarioFin;
            fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), nuevoHorarioInicio);
            fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), nuevoHorarioFin);
        }
        modificarTodasLasAlarmas();

        this.todoElDia = (nuevoHorarioInicio == LocalTime.MIN && nuevoHorarioFin == LocalTime.MAX);

    }

    public void modificarFrecuencia(FrecuenciaC frecuencia){
        this.frecuencia = frecuencia;
    }

    public void marcarTodoElDia() {
        this.todoElDia = true;
        fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), LocalTime.MIN);
        fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), LocalTime.MAX);

        //this.horarioInicio = LocalTime.MIN;
        //this.horarioFin = LocalTime.MAX;
    }

    public Alarma agregarAlarma(LocalDateTime fechaHoraAlarma, int intervalo, Alarma.UnidadesDeTiempo unidad, Alarma.EfectosAlarma efecto){
        //LocalDateTime fechaHora = LocalDateTime.of(fechaFin, horarioFin);
        Alarma alarma;
        if (fechaHoraAlarma == null){
            alarma = new Alarma(fechaHoraInicio, intervalo, unidad, efecto);
        } else {
            alarma = new Alarma(fechaHoraAlarma, fechaHoraAlarma, efecto);
        }
        this.alarmas.add(alarma);
        return alarma;
    }

    public void destruirAlarma(Alarma alarma){
        this.alarmas.remove(alarma);
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

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }
    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

/*    public LocalDate getFechaInicio(){
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
    */
    public FrecuenciaC getFrecuencia(){
        return frecuencia;
    }

    /**
     * Devuelve true si la tarea ocurrirá en la fecha recibida
     * */
    public boolean ocurreEnFecha(LocalDate fechaCualquiera){
        return frecuencia.fechaCorrespondeAFrecuencia(fechaCualquiera);
    }
}
