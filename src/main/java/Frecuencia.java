import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.time.LocalDate;


@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = FrecuenciaCero.class, name = "FrecuenciaCero"),
        @JsonSubTypes.Type(value = FrecuenciaDiaria.class, name = "FrecuenciaDiaria"),
        @JsonSubTypes.Type(value = FrecuenciaSemanal.class, name = "FrecuenciaSemanal"),
        @JsonSubTypes.Type(value = FrecuenciaMensual.class, name = "FrecuenciaMensual"),
        @JsonSubTypes.Type(value = FrecuenciaAnual.class, name = "FrecuenciaAnual")
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)//para que lea todos los atributos

public abstract class Frecuencia implements Serializable {
    protected LocalDate fechaInicio;
    protected int intervalo;
    protected int ocurrencias;
    protected LocalDate fechaFin;
    protected LocalDate fechaProxima;


    //constructor que recibe una fecha fin
    public Frecuencia(LocalDate fechaInicio, int intervalo, LocalDate fechaFin) {
        this.intervalo = intervalo;
        this.fechaInicio = fechaInicio;
        if (fechaFin == null) {
            this.fechaFin = LocalDate.MAX;
        }
        else {
            this.fechaFin = fechaFin;
        }
        this.ocurrencias = -1; //-1 representa indefinidas repeticiones
        this.fechaProxima = fechaInicio;
    }

    //constructor que recibe una cantidad de ocurrencias
    public Frecuencia(LocalDate fechaInicio, int intervalo, int ocurrencias) {
        this.fechaProxima = fechaInicio;
        this.intervalo = intervalo;
        this.fechaInicio = fechaInicio;
        this.ocurrencias = ocurrencias;
    }

    public int getIntervalo() {
        return intervalo;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setIntervalo(int cadaCuanto) {
        this.intervalo = cadaCuanto;
        this.fechaFin = calcularFechaFin();
    }
    public void setFechaInicio(LocalDate fechaInicio){
        this.fechaInicio = fechaInicio;
        if (this.ocurrencias > 0){
            this.fechaFin = calcularFechaFin();
        }
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }


    /**
     * Devuelve la última fecha correspondiente a la frecuencia
     **/
    public abstract LocalDate calcularFechaFin();


    /**
     * Devuelve la proxima fecha correspondiente a la frecuencia
     * o null si no hay proxima
     **/
    public abstract LocalDate obtenerFechaProxima();


    /**
     * Recibe una fecha aleatoria y devuelve true si la fecha recibida
     * corresponde a la frecuencia.
     * Por ej. si la frecuencia cada semana los martes y jueves
     * y recibe una fecha que corresponde a un martes, devolverá true
     **/
    public abstract boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera);
}

