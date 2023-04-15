import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
public class Evento {
    private String titulo;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private boolean todoElDia;

//falta ver como hacer que si le fecha de fin es anterior a la fecha de inicio que no se cree el evento
    public Evento(String titulo, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, LocalTime horarioInicio, LocalTime horarioFin, boolean todoElDia){
        this.titulo = titulo;
        this.descripcion= descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        if (todoElDia){
            this.horarioInicio = LocalTime.parse("00:00");
            this.horarioFin = LocalTime.parse("00:00");
        } else {
            this.horarioInicio = horarioInicio;
            this.horarioFin = horarioFin;
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
    public int modificarHorario(LocalTime nuevoHorarioInicio, LocalTime nuevoHorarioFin){
        if (!validarHorario(nuevoHorarioInicio, nuevoHorarioFin)){
            return -1;
        } else {
            this.horarioInicio = nuevoHorarioInicio;
            this.horarioFin= nuevoHorarioFin;
        }
        return 0;
    }

    private boolean validarHorario(LocalTime nuevoHorarioInicio, LocalTime nuevoHorarioFin){
        int horaInicio = nuevoHorarioInicio.getHour();
        int horaFin = nuevoHorarioFin.getHour();
        int minutoInicio = nuevoHorarioInicio.getMinute();
        int minutoFin = nuevoHorarioFin.getMinute();
        //ver porque la condicion tira ese warning
        if (horaInicio < 0 || horaFin < 0 || horaInicio > 23 || horaFin > 23){
            return false;
        }
        if (minutoInicio < 0 || minutoFin < 0 || minutoInicio > 59 || minutoFin < 0){
            return false;
        }
        return true;
    }

    /*public String getTitulo() {
        return titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }*/

}
