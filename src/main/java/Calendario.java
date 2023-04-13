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
    public void crearTarea() {
        var tarea = new Tarea();
        tareas.add(tarea);
    }
    public void modificarTarea() {

    }
    public void eliminarTarea() {

    }
}
