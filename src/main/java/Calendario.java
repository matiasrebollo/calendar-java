import java.util.ArrayList;

public class Calendario {
    private ArrayList<Evento> eventos;
    private ArrayList<Tarea> tareas;
    public Calendario(){

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
    public void crearTarea(String titulo, String descripcion, boolean todoElDia) {
        var tarea = new Tarea(titulo, descripcion,todoElDia);
        tareas.add(tarea);
    }
    public void modificarTarea() {

    }
    public void eliminarTarea() {

    }
}
