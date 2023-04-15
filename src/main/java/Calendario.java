import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Calendario {
    enum Elementos {TITULO, DESCRIPCION, FECHA, HORARIO};
    private ArrayList<Evento> eventos;
    private ArrayList<Tarea> tareas;

    public Calendario(){
        this.eventos = new ArrayList<>();
        this.tareas = new ArrayList<>();
    }
    public void crearEvento(String titulo, String descripcion, Date fechaInicio, Date fechaFin, int[] horarioInicio, int[] horarioFin, boolean todoElDia) {
        var evento = new Evento(titulo, descripcion, fechaInicio, fechaFin, horarioInicio, horarioFin, todoElDia);
        eventos.add(evento);
    }
    public void modificarEvento(Evento evento) {

    }
    public void eliminarEvento(Evento evento) {
        eventos.remove(evento);
    }



    //TAREA
    public void crearTarea(String titulo, String descripcion, LocalDate fecha, boolean todoElDia) {
        var tarea = new Tarea(titulo, descripcion, fecha, todoElDia);
        tareas.add(tarea);
    }
    /**
     * Recibe el titulo de la Tarea a modificar, el elemento que desea modificar
     * y el nuevo valor de ese elemento
     * En el caso de la fecha el formato debe ser "dd/MM/yyyy"
     * */
    public void modificarTarea(String titulo, Elementos e, String nuevoValor) {
        Tarea tarea = buscarTareaPorTitulo(titulo);
        if (tarea == null) {
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
                //chequear que el formato sea correcto
                LocalDate nuevaFecha = LocalDate.parse(nuevoValor, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                tarea.modificarFecha(nuevaFecha);
            }
            case HORARIO -> {
                //chequear que el formato sea correcto
                LocalTime nuevoHorario = LocalTime.parse(nuevoValor, DateTimeFormatter.ofPattern("hh:mm"));
                tarea.modificarHorario(nuevoHorario);
            }
        }
    }
    /**
     * Recorre las tareas y devuelve la Tarea que tenga el titulo recibido,
     * o null en caso de que no exista
     * */
    private Tarea buscarTareaPorTitulo(String titulo) {
        for (int i = 0; i < tareas.size(); i++) {
            if (this.tareas.get(i).getTitulo().equals(titulo)) {
                return this.tareas.get(i);
            }
        }
        return null;
    }
    public boolean existeTarea(String titulo) {
        var tarea = buscarTareaPorTitulo(titulo);
        return (tarea != null);
    }
    public void eliminarTarea(String titulo) {
        var tarea = buscarTareaPorTitulo(titulo);
        if (tarea != null) {
            this.tareas.remove(tarea);
        }
        else {
            //la tarea no existe
        }

    }

    public int cantidadEventos() {
        return eventos.size();
    }
    public int cantidadTareas() {
        return tareas.size();
    }
}
