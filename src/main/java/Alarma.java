import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Alarma {
    enum UnidadesDeTiempo {MINUTOS, HORAS, DIAS, SEMANAS}
    private LocalDateTime fechaHoraEvento;
    LocalDateTime fechaHoraAlarma;
    private int intervalo;
    private UnidadesDeTiempo unidad;
    public Alarma(LocalDateTime fechaHoraEvento, LocalDateTime fechaHoraAlarma) {
        this.fechaHoraEvento = fechaHoraEvento;
        this.fechaHoraAlarma = fechaHoraAlarma;
    }
    public Alarma(LocalDateTime fechaHoraEvento,
                  int intervalo, UnidadesDeTiempo unidad) {
        this.fechaHoraEvento = fechaHoraEvento;
        this.unidad = unidad;
        this.intervalo = intervalo;
        calcularFechaHoraAlarma();
    }

    private void calcularFechaHoraAlarma() {
        switch (unidad) {
            case MINUTOS -> {
                fechaHoraAlarma = fechaHoraEvento.minusMinutes(intervalo);
            }
            case HORAS -> {
                fechaHoraAlarma = fechaHoraEvento.minusHours(intervalo);
            }
            case DIAS -> {
                fechaHoraAlarma = fechaHoraEvento.minusDays(intervalo);
            }
            case SEMANAS -> {
                fechaHoraAlarma = fechaHoraEvento.minusDays(intervalo);
                break;
            }
        }
    }
    public void configurarAlarma(boolean mostrarNotificacion, boolean reproducirSonido, boolean enviarEmail) {

    }
}
