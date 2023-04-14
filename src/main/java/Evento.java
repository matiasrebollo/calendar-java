import java.util.Date;
public class Evento {
    private String titulo;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private int[] horarioInicio = new int[2];
    private int[] horarioFin = new int[2];
    private boolean todoElDia;

//falta ver como hacer que si le fecha de fin es anterior a la fecha de inicio que no se cree el evento
    public Evento(String titulo, String descripcion, Date fechaInicio, Date fechaFin, int[] horarioInicio, int[] horarioFin, boolean todoElDia){
        this.titulo = titulo;
        this.descripcion= descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin= fechaFin;
        if (todoElDia){
            this.todoElDia = true;
            this.horarioInicio[0] = 0;
            this.horarioInicio[1] = 0;
            this.horarioFin[0] = 0;
            this.horarioFin[1] = 0;
        } else {
            this.todoElDia = false; // no se si hace falta que todoeldia sea un atributo
            this.horarioInicio[0] = horarioInicio[0];
            this.horarioInicio[1] = horarioInicio[1];
            this.horarioFin[0] = horarioFin[0];
            this.horarioFin[1] = horarioFin[1];
        }
    }
    //en las funciones de modificar falta que haya algun retorno para poder hacer las pruebas
    public void modificarTitulo(String nuevoTitulo){
        this.titulo = nuevoTitulo;
    }
    public void modificarDescripcion(String nuevaDescripcion){
        this.descripcion = nuevaDescripcion;
    }
//para mi los modificar fecha y horario tendrian que ir juntos, falta verificar que el horario de fin no sea menor al horario
//de inicio, si es que estan en el mismo dia, por eso digo que deben ir juntos, para que un evento no arranque en el presente
//y finalice en el pasado.
    public void modificarFecha(Date nuevaFechaInicio, Date nuevaFechaFin){

    }
    public int modificarHorario(int[] nuevoHorarioInicio, int[] nuevoHorarioFin){
        if (nuevoHorarioInicio[0] >= 24 || nuevoHorarioInicio[0] < 0 || nuevoHorarioFin[0] >= 24 || nuevoHorarioFin[0] < 0){
            return -1;
        }
        else if (nuevoHorarioInicio[1] >= 60 || nuevoHorarioInicio[1] < 0 || nuevoHorarioFin[1] >= 60 || nuevoHorarioFin[1] < 0){
            return -1;
        }
        this.horarioInicio[0] = nuevoHorarioInicio[0];
        this.horarioInicio[1] = nuevoHorarioInicio[1];
        this.horarioFin[0] = nuevoHorarioFin[0];
        this.horarioFin[1] = nuevoHorarioFin[1];
        return 0;
    }

    /*public String getTitulo() {
        return titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }*/

}
