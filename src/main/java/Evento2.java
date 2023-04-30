import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Evento2 extends ElementoCalendario{
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;


    public Evento2(String titulo, String descripcion, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, boolean todoElDia, Frecuencia frecuencia){
        super(titulo,descripcion, fechaHoraInicio.toLocalDate(), todoElDia, fechaHoraInicio.toLocalTime(),frecuencia);
        this.fechaHoraFin = fechaHoraFin;
        this.fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), fechaHoraInicio.toLocalTime());
        if (todoElDia){
            this.fechaHoraInicio = LocalDateTime.of(fechaHoraInicio.toLocalDate(), LocalTime.MIN);
            this.fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), LocalTime.MAX);
        }
    }
    public LocalDate getFechaFin() {
        return fechaHoraFin.toLocalDate();
    }
    public LocalTime getHoraFin() {
        return fechaHoraFin.toLocalTime();
    }


    public void setFechaFin(LocalDate fechaFin) {
        this.fechaHoraFin = LocalDateTime.of(fechaFin, fechaHoraFin.toLocalTime());
    }

    public void setHoraFin(LocalTime horaFin) {
        fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), horaFin);
    }
    @Override
    public void marcarTodoElDia() {
        super.marcarTodoElDia();
        fechaHoraFin = LocalDateTime.of(fechaHoraFin.toLocalDate(), LocalTime.MAX);
    }
}
