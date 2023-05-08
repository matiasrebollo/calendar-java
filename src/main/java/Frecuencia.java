import java.time.DayOfWeek;
import java.time.LocalDate;


public abstract class Frecuencia{

    protected LocalDate fechaInicio;
    protected int intervalo;
    protected int ocurrencias;
    protected LocalDate fechaFin;
    protected LocalDate fechaProxima;



    //recibe una fecha fin
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

    //recibe una cantidad de ocurrencias
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


    /**
     * En caso de ser frecuencia de tipo SEMANAL,
     * agrega el dia de la semana a la frecuencia si no fue agregado antes
     * o lo quita si este ya estaba agregado
     *
    public void agregarOQuitarDiaDeLaSemana(DayOfWeek dia){
        if (tipo instanceof FrecuenciaSemanal) { //no se nos ocurrio una forma de no usar instanceof
            ((FrecuenciaSemanal) tipo).agregarOQuitarDiaDeLaSemana(dia);
            this.fechaFin = tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
        }
    }*/

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

