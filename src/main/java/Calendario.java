import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Calendario {
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
    public void crearTarea(String titulo, String descripcion, Date fecha, boolean todoElDia) {
        var tarea = new Tarea(titulo, descripcion, fecha, todoElDia);
        tareas.add(tarea);
    }
    public void modificarTarea(String titulo) {
        Tarea tarea = buscarTareaPorTitulo(titulo);
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
    public void eliminarTarea() {

    }

    public int cantidadEventos() {
        return eventos.size();
    }
    public int cantidadTareas() {
        return tareas.size();
    }
}
