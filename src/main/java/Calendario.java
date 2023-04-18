import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Calendario {
    enum Elementos {TITULO, DESCRIPCION, FECHA, HORARIO};
    enum Semana {LUNES, MARTES, MIERCOLES, JUEVES, VIERNES};
    private ArrayList<Evento> eventos;
    private ArrayList<Tarea> tareas;
    private LocalDate fechaActual;
    private LocalTime horaActual;

    public Calendario(){
        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
        this.fechaActual = LocalDate.now();
    }
    public void crearEvento(String titulo, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, LocalTime horarioInicio, LocalTime horarioFin, boolean todoElDia) {
        var evento = new Evento(titulo, descripcion, fechaInicio, fechaFin, horarioInicio, horarioFin, todoElDia);
        eventos.add(evento);
    }
    public void modificarEvento(Evento evento) {

    }
    public void eliminarEvento(Evento evento) {
        eventos.remove(evento);
    }



    //TAREA
    /**
     * Devuelve la tarea creada o null en caso de que no se cree
     * el formato de fecha debe ser "d/M/yyyy", por ej. "2/10/2022"
     * el formato de la hora debe ser "kk:mm", por ej. "20:05"
     * */
    public Tarea crearTarea(String titulo, String descripcion, String fecha, boolean todoElDia, String hora, FrecuenciaC frecuencia) {
        //chequear que el formato de la fecha sea correcto (etapa 2)
        LocalDate fechaDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("d/M/yyyy"));

        Tarea tarea;
        //chequear que el formato de la hora sea correcto (etapa 2)
        LocalTime horaTime = LocalTime.parse(hora,DateTimeFormatter.ofPattern("kk:mm"));
        tarea = new Tarea(titulo,descripcion, fechaDate, todoElDia, horaTime,frecuencia);

        this.tareas.add(tarea);
        return tarea;
    }

    /**
     * Recibe la tarea a modificar, el elemento que desea modificar
     * y el nuevo valor de ese elemento
     * En el caso de la fecha el formato debe ser "d/M/yyyy"
     * En el caso de la hora el formato debe ser "kk:mm"
     * */
    public void modificarTarea(Tarea tarea, Elementos e, String nuevoValor) {
        if (!existeTarea(tarea)) {
            return;//La tarea no existe
        }
        switch (e) {
            case TITULO -> {
                tarea.modificarTitulo(nuevoValor);
            }
            case DESCRIPCION -> {
                tarea.modificarDescripcion(nuevoValor);
            }
            case FECHA -> {
                //falta chequear que el formato sea correcto
                LocalDate nuevaFecha = LocalDate.parse(nuevoValor, DateTimeFormatter.ofPattern("d/M/yyyy"));
                tarea.modificarFecha(nuevaFecha);
            }
            case HORARIO -> {
                //falta chequear que el formato sea correcto
                LocalTime nuevoHorario = LocalTime.parse(nuevoValor, DateTimeFormatter.ofPattern("kk:mm"));
                tarea.modificarHorario(nuevoHorario);
            }
        }
    }

    public boolean existeTarea(Tarea tarea) {
        return this.tareas.contains(tarea);
    }
    public void eliminarTarea(Tarea tarea) {
        if (existeTarea(tarea)) {
            this.tareas.remove(tarea);
        }
    }

    public int cantidadEventos() {
        return eventos.size();
    }
    public int cantidadTareas() {
        return tareas.size();
    }

    /**
     * muestra las tareas que hay en una fecha cualquiera
     * */
    public void mostrarTareasDelDia(LocalDate fecha) {

    }
}
