import java.util.Date;

public class Tarea {
    private static final int ERROR = -1;
    private static final int OK = 0;
    private String titulo;
    private String descripcion;
    private boolean todoElDia;
    private Date fecha;
    private int[] horario = new int[2];// [0] = hs,    [1] = min

    private boolean completada;

    //si recibe true, no deberia pedirle el horario
    //si recibe false, si deberia pedirle el horario
    public Tarea(String titulo, String descripcion, Date fecha, boolean todoElDia) {
        this.completada = false;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        if (todoElDia) {
            this.todoElDia = true;
        }
        else {
            this.todoElDia = false;
            //falta poner el horario
         }
    }

    /**
     * Recibe un horario nuevo y modifica el actual
     * si pudo modificarlo devuele 0
     * si hubo un error devuelve -1
     */
    public int modificarHorario(int[] nuevoHorario) {
        if (nuevoHorario[0] >= 24 || nuevoHorario[1] >= 60) {
            return ERROR;
        }
        else if (nuevoHorario[0] < 0 || nuevoHorario[1] < 0) {
            return ERROR;
        }
        this.horario[0] = nuevoHorario[0];
        this.horario[1] = nuevoHorario[1];
        return OK;
    }
    public void modificarFecha(Date fechaNueva) {
        this.fecha = fechaNueva;
    }
    public void modificarTitulo(String tituloNuevo) {
        this.titulo = tituloNuevo;
    }
    public void modificarDescripcion(String descripcionNueva) {
        this.descripcion = descripcionNueva;
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
}



