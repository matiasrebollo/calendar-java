import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Evento extends ElementoCalendario implements Serializable {
    private LocalDate fechaFin;
    private LocalTime horaFin;


    public Evento(String titulo, String descripcion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, boolean todoElDia, Frecuencia frecuencia){
        super(titulo,descripcion, fechaHoraInicio.toLocalDate(), todoElDia, fechaHoraInicio.toLocalTime(),frecuencia);
        this.fechaFin = fechaHoraFin.toLocalDate();
        this.horaFin = fechaHoraFin.toLocalTime();
        if (todoElDia){
            this.horaFin = LocalTime.MAX;
        }
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public void marcarTodoElDia() {
        super.marcarTodoElDia();
        this.horaFin = LocalTime.MAX;
    }
}
