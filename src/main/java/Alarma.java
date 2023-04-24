import java.time.LocalDateTime;

public class Alarma {
    enum UnidadesDeTiempo {MINUTOS, HORAS, DIAS, SEMANAS}
    enum EfectosAlarma {NOTIFICACION, SONIDO, EMAIL}

    private LocalDateTime fechaHoraEvento;
    private LocalDateTime fechaHoraAlarma;
    private int intervalo;
    private UnidadesDeTiempo unidad;
    private EfectosAlarma efecto;

    public Alarma(LocalDateTime fechaHoraEvento, LocalDateTime fechaHoraAlarma, EfectosAlarma efecto) {
        this.fechaHoraEvento = fechaHoraEvento;
        this.fechaHoraAlarma = fechaHoraAlarma;
        this.efecto = efecto;
    }
    public Alarma(LocalDateTime fechaHoraEvento, int intervalo, UnidadesDeTiempo unidad, EfectosAlarma efecto) {
        this.fechaHoraEvento = fechaHoraEvento;
        this.unidad = unidad;
        this.intervalo = intervalo;
        this.efecto = efecto;
        calcularFechaHoraAlarma();
    }

    private void calcularFechaHoraAlarma() {
        switch (unidad) {
            case MINUTOS -> fechaHoraAlarma = fechaHoraEvento.minusMinutes(intervalo);
            case HORAS -> fechaHoraAlarma = fechaHoraEvento.minusHours(intervalo);
            case DIAS -> fechaHoraAlarma = fechaHoraEvento.minusDays(intervalo);
            case SEMANAS -> fechaHoraAlarma = fechaHoraEvento.minusWeeks(intervalo);
        }
    }

    public void actualizarFechaHoraAlarma(LocalDateTime fechaHoraEvento) {
        this.fechaHoraEvento = fechaHoraEvento;
        if (unidad != null) {//si no hay una fecha y hora fijas
            calcularFechaHoraAlarma();
        }
    }

    /**
     * Devuelve 0 si envia una notificacion
     * Devuelve 1 si reproduce un sonido
     * Devuelve 2 si envia un email
     * Devuelve -1 en caso de error
     * */
    public int reproducirEfecto() {
        switch (efecto) {
            case NOTIFICACION -> {
                return EfectosAlarma.NOTIFICACION.ordinal();
            }
            case SONIDO -> {
                return EfectosAlarma.SONIDO.ordinal();
            }
            case EMAIL -> {
                return EfectosAlarma.EMAIL.ordinal();
            }
        }
        return -1;
    }

    public LocalDateTime getFechaHoraAlarma() {
        return fechaHoraAlarma;
    }
}
