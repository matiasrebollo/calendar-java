import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Tarea{
    private String titulo;
    private String descripcion;
    private boolean todoElDia;
    private LocalDate fecha;
    private LocalTime horario;
    private boolean completada;
    private FrecuenciaC frecuencia;
    private ArrayList<Alarma> alarmas = new ArrayList<>();


    public Tarea(String titulo, String descripcion, LocalDate fecha, boolean todoElDia, LocalTime horario, FrecuenciaC frecuencia) {
        this.completada = false;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.frecuencia = frecuencia;
        if (frecuencia == null) {
            this.frecuencia = new FrecuenciaC(FrecuenciaC.TipoFrecuencia.CERO, fecha,1, null);
        }
        this.horario = horario;
        this.todoElDia = todoElDia;
        if (todoElDia) {
            this.horario = LocalTime.MAX;
        }
    }


    public void modificarHorario(LocalTime nuevoHorario) {
        this.horario = nuevoHorario;
        this.todoElDia = false;
    }
    public void modificarFecha(LocalDate fechaNueva) {
        this.fecha = fechaNueva;
        this.frecuencia.setFechaInicio(fechaNueva);
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

    public void marcarTodoElDia() {
        this.todoElDia = true;
        this.horario = LocalTime.MAX;
    }

    public Alarma agregarAlarma(LocalDateTime fechaHoraAlarma, int intervalo, Alarma.UnidadesDeTiempo unidad, Alarma.EfectosAlarma efecto){
        LocalDateTime fechaHora = LocalDateTime.of(fecha, horario);
        Alarma alarma;
        if (fechaHoraAlarma == null){
            alarma = new Alarma(fechaHora, intervalo, unidad, efecto);
        } else {
            alarma = new Alarma(fechaHora, fechaHoraAlarma, efecto);
        }
        this.alarmas.add(alarma);
        return alarma;
    }
    public void destruirAlarma(Alarma alarma){
        this.alarmas.remove(alarma);
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
    public boolean ocurreEnFecha(LocalDate fechaCualquiera){
        return frecuencia.fechaEstaIncluida(fechaCualquiera);
    }
}



