package org;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;


@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)//para que incluya todos los atributos en el json
public class Alarma implements Serializable {
    public enum UnidadesDeTiempo {MINUTOS, HORAS, DIAS, SEMANAS}
    public enum EfectosAlarma {NOTIFICACION, SONIDO, EMAIL}

    private LocalDateTime fechaHoraEvento;
    private LocalDateTime fechaHoraAlarma;
    private int intervalo;
    private UnidadesDeTiempo unidad;
    private EfectosAlarma efecto;

    //constructor que recibe una fecha y hora específica para la alarma
    public Alarma(LocalDateTime fechaHoraEvento, LocalDateTime fechaHoraAlarma, EfectosAlarma efecto) {
        this.fechaHoraEvento = fechaHoraEvento;
        this.fechaHoraAlarma = fechaHoraAlarma;
        this.efecto = efecto;
    }
    //Constructor que recibe cuánto tiempo antes del evento sonará la alarma
    public Alarma(LocalDateTime fechaHoraEvento, int intervalo, UnidadesDeTiempo unidad, EfectosAlarma efecto) {
        this.fechaHoraEvento = fechaHoraEvento;
        this.unidad = unidad;
        this.intervalo = intervalo;
        this.efecto = efecto;
        this.calcularFechaHoraAlarma();
    }

    /**
     * Según el intervalo y las unidades de tiempo (es decir si es 1 hora antes, 5 minutos antes, 2 días antes, etc)
     * Calcula la fecha y hora en la que va a sonar la alarma
     * */
    private void calcularFechaHoraAlarma() {
        switch (unidad) {
            case MINUTOS -> fechaHoraAlarma = fechaHoraEvento.minusMinutes(intervalo);
            case HORAS -> fechaHoraAlarma = fechaHoraEvento.minusHours(intervalo);
            case DIAS -> fechaHoraAlarma = fechaHoraEvento.minusDays(intervalo);
            case SEMANAS -> fechaHoraAlarma = fechaHoraEvento.minusWeeks(intervalo);
        }
    }

    /**
     * Recibe una nueva fecha y hora del evento
     * y actualiza la fecha y hora en la que sonará la alarma
     */
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
    public EfectosAlarma getEfecto() {
        return efecto;
    }



    @JsonCreator
    private Alarma(
            @JsonProperty("fechaHoraEvento") LocalDateTime fechaHoraEvento,
            @JsonProperty("fechaHoraAlarma") LocalDateTime fechaHoraAlarma,
            @JsonProperty("intervalo") int intervalo,
            @JsonProperty("unidad") UnidadesDeTiempo unidad,
            @JsonProperty("efecto") EfectosAlarma efecto) {
        this.fechaHoraEvento = fechaHoraEvento;
        this.fechaHoraAlarma = fechaHoraAlarma;
        this.unidad = unidad;
        this.intervalo = intervalo;
        this.efecto = efecto;
    }

}
