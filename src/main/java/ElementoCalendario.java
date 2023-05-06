import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ElementoCalendario {
    private String titulo;
    private String descripcion;
    private boolean todoElDia;
    private LocalDate fechaInicio;
    private LocalTime horaInicio;
    private Frecuencia frecuencia;
    private ArrayList<Alarma> alarmas = new ArrayList<>();



    public ElementoCalendario(String titulo, String descripcion, LocalDate fechaInicio, boolean todoElDia, LocalTime horaInicio, Frecuencia frecuencia) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.frecuencia = frecuencia;
        if (frecuencia == null) {
            this.frecuencia = new FrecuenciaCero(fechaInicio);
        }
        this.horaInicio = horaInicio;
        this.todoElDia = todoElDia;
        if (todoElDia) {
            this.horaInicio = LocalTime.MAX;
        }
    }

    private void modificarTodasLasAlarmas() {
        LocalDateTime fechaInicioHoraInicio = LocalDateTime.of(fechaInicio, horaInicio);
        for (Alarma alarma : alarmas) {
            alarma.actualizarFechaHoraAlarma(fechaInicioHoraInicio);
        }
    }

    public void setHoraInicio(LocalTime nuevoHorario) {
        this.horaInicio = nuevoHorario;
        this.todoElDia = false;
        modificarTodasLasAlarmas();
    }
    public void setFechaInicio(LocalDate fechaInicioNueva) {
        this.fechaInicio = fechaInicioNueva;
        this.frecuencia.setFechaInicio(fechaInicioNueva);
        modificarTodasLasAlarmas();
    }
    public void setTitulo(String tituloNuevo) {
        this.titulo = tituloNuevo;
    }
    public void setDescripcion(String descripcionNueva) {
        this.descripcion = descripcionNueva;
    }
    public void setFrecuencia(Frecuencia frecuencia){
        if (frecuencia == null) {
            this.frecuencia = new FrecuenciaCero(fechaInicio);
        }
        else {
            this.frecuencia = frecuencia;
        }
    }

    public void marcarTodoElDia() {
        this.todoElDia = true;
        this.horaInicio = LocalTime.MIN;
    }

    /**
     * Crea una Alarma con los datos recibidos.
     * Devuelve la alarma creada o null en caso de error
     * */
    public Alarma agregarAlarma(LocalDateTime fechaHoraAlarma, int intervalo, Alarma.UnidadesDeTiempo unidad, Alarma.EfectosAlarma efecto){
        LocalDateTime fechaHora = LocalDateTime.of(fechaInicio, horaInicio);
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
    public int cantidadAlarmas(){//para hacer pruebas
        return alarmas.size();
    }//para hacer pruebas


    public String getTitulo() {
        return titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public Frecuencia getFrecuencia(){
        return frecuencia;
    }
    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    /**
     * Devuelve true si la tarea ocurrirá en la fechaInicio recibida
     * */
    public boolean ocurreEnFecha(LocalDate fechaCualquiera){
        return frecuencia.fechaCorrespondeAFrecuencia(fechaCualquiera);
    }
}
