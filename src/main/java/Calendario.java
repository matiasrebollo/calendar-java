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
    public void crearEvento() {
        var evento = new Evento();
        eventos.add(evento);
    }
    public void modificarEvento(Evento evento) {

    }
    public void eliminarEvento(Evento evento) {
        eventos.remove(evento);
    }
    public void crearTarea(String titulo, String descripcion, Date fecha, boolean todoElDia) {
        var tarea = new Tarea(titulo, descripcion, fecha, todoElDia);
        tareas.add(tarea);
    }
    public void modificarTarea() {

    }
    public void eliminarTarea() {

    }
}
