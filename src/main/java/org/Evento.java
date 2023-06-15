package org;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Evento extends ElementoCalendario{
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
    public LocalDate getFechaFinRepeticion(LocalDate fecha) {
        if (ocurreEnFecha(fecha))
            return fecha;
        return fechaFin;
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


    @JsonCreator
    private Evento(
            @JsonProperty("titulo") String titulo,
            @JsonProperty("descripcion") String descripcion,
            @JsonProperty("fechaInicio") LocalDate fechaInicio,
            @JsonProperty("todoElDia") boolean todoElDia,
            @JsonProperty("horaInicio") LocalTime horaInicio,
            @JsonProperty("alarmas") ArrayList<Alarma> alarmas,
            @JsonProperty("fechaFin") LocalDate fechaFin,
            @JsonProperty("horaFin") LocalTime horaFin) {
        super(titulo, descripcion, fechaInicio, todoElDia, horaInicio,null);
        super.copiarAlarmas(alarmas);
        this.fechaFin = fechaFin;
        this.horaFin = horaFin;
    }

    @Override
    protected String getTypename() {
        return "Evento";
    }
}
