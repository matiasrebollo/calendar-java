import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Evento {
    private String titulo;
    private String descripcion;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private boolean todoElDia;
    private FrecuenciaC frecuencia;
    private ArrayList<Alarma> alarmas = new ArrayList<>();


    public Evento(String titulo, String descripcion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, boolean todoElDia, FrecuenciaC frecuencia){
        this.titulo = titulo;
        this.descripcion= descripcion;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        if (todoElDia){
            this.fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), LocalTime.MIN);
            this.fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), LocalTime.MAX);
            this.todoElDia = true;
        } else {
            this.todoElDia = false;
        }
        this.frecuencia = frecuencia;
        if (frecuencia == null){
            this.frecuencia = new FrecuenciaC(Frecuencia.TipoFrecuencia.CERO, fechaHoraInicio.toLocalDate(),1, null);
        }
    }

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
    public FrecuenciaC getFrecuencia(){
        return frecuencia;
    }

    public void setTitulo(String nuevoTitulo){
        this.titulo = nuevoTitulo;
    }
    public void setDescripcion(String nuevaDescripcion){
        this.descripcion = nuevaDescripcion;
    }
    public void setFrecuencia(FrecuenciaC frecuencia){
        this.frecuencia = frecuencia;
    }

    private void modificarTodasLasAlarmas() {
        for (Alarma alarma : alarmas) {
            alarma.actualizarFechaHoraAlarma(fechaHoraInicio);
        }
    }

    /**
     * Modifica la fecha de inicio y fin del evento.
     * Si recibe null en alguno, esa fecha no la modifica
     * */
    public void modificarFecha(LocalDate nuevaFechaInicio, LocalDate nuevaFechaFin){
        if (nuevaFechaInicio == null){
            fechaHoraFin = LocalDateTime.of(nuevaFechaFin, fechaHoraFin.toLocalTime());
        } else if (nuevaFechaFin == null){
            fechaHoraInicio = LocalDateTime.of(nuevaFechaInicio, fechaHoraInicio.toLocalTime());
        } else {
            fechaHoraInicio = LocalDateTime.of(nuevaFechaInicio, fechaHoraInicio.toLocalTime());
            fechaHoraFin = LocalDateTime.of(nuevaFechaFin, fechaHoraFin.toLocalTime());
        }
        modificarTodasLasAlarmas();
        this.frecuencia.setFechaInicio(nuevaFechaInicio);
    }
    /**
     * Modifica el horario de inicio y fin del evento.
     * Si recibe null en alguno, ese horario no lo modifica
     * */
    public void modificarHorario(LocalTime nuevoHorarioInicio, LocalTime nuevoHorarioFin){
        if (nuevoHorarioInicio == null){
            fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), nuevoHorarioFin);

        } else if (nuevoHorarioFin == null){
            fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), nuevoHorarioInicio);

        } else {
            fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), nuevoHorarioInicio);
            fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), nuevoHorarioFin);
        }
        modificarTodasLasAlarmas();
        this.todoElDia = (nuevoHorarioInicio == LocalTime.MIN && nuevoHorarioFin == LocalTime.MAX);

    }



    public void marcarTodoElDia() {
        this.todoElDia = true;
        fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), LocalTime.MIN);
        fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), LocalTime.MAX);
    }

    /**
     * Crea una Alarma con los datos recibidos.
     * Devuelve la alarma creada o null en caso de error
     * */
    public Alarma agregarAlarma(LocalDateTime fechaHoraAlarma, int intervalo, Alarma.UnidadesDeTiempo unidad, Alarma.EfectosAlarma efecto){
        Alarma alarma;
        if (fechaHoraAlarma == null){
            alarma = new Alarma(fechaHoraInicio, intervalo, unidad, efecto);
        } else {
            alarma = new Alarma(fechaHoraInicio, fechaHoraAlarma, efecto);
        }
        this.alarmas.add(alarma);
        return alarma;
    }

    public int cantidadAlarmas(){//para hacer pruebas
        return alarmas.size();
    }
    public void destruirAlarma(Alarma alarma){
        this.alarmas.remove(alarma);
    }

    /**
     * Devuelve true si el evento ocurrirá en la fecha recibida
     * */
    public boolean ocurreEnFecha(LocalDate fechaCualquiera){
        return frecuencia.fechaCorrespondeAFrecuencia(fechaCualquiera);
    }
}

