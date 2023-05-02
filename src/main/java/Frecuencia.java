import java.time.DayOfWeek;
import java.time.LocalDate;


public class Frecuencia{

    private LocalDate fechaInicio;
    private int intervalo;
    private int ocurrencias;
    private LocalDate fechaFin;
    private EstrategiaFrecuencia tipo;
    private LocalDate fechaProxima;



    //recibe una fecha fin
    public Frecuencia(EstrategiaFrecuencia tipo, LocalDate fechaInicio, int intervalo, LocalDate fechaFin) {
        this.tipo = tipo;
        this.intervalo = intervalo;
        this.fechaInicio = fechaInicio;
        if (fechaFin == null) {
            this.fechaFin = LocalDate.MAX;
        }
        else {
            this.fechaFin = fechaFin;
        }
        this.ocurrencias = -1; //-1 representa indefinidas repeticiones
        this.agregarOQuitarDiaDeLaSemana(fechaInicio.getDayOfWeek());
        this.fechaProxima = fechaInicio;
    }

    //recibe una cantidad de ocurrencias
    public Frecuencia(EstrategiaFrecuencia tipo, LocalDate fechaInicio, int intervalo, int ocurrencias) {
        this.tipo = tipo;
        this.fechaProxima = fechaInicio;
        this.intervalo = intervalo;
        this.fechaInicio = fechaInicio;
        this.ocurrencias = ocurrencias;
        agregarOQuitarDiaDeLaSemana(fechaInicio.getDayOfWeek());
        this.fechaFin = calcularFechaFin();
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
     * */
    public void agregarOQuitarDiaDeLaSemana(DayOfWeek dia){
        if (tipo instanceof FrecuenciaSemanal) { //no se nos ocurrio una forma de no usar instanceof
            ((FrecuenciaSemanal) tipo).agregarOQuitarDiaDeLaSemana(dia);
            this.fechaFin = tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
        }
    }

    public void setTipo(EstrategiaFrecuencia tipo) {
        this.tipo = tipo;
        this.fechaFin = this.tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
    }
    public void setIntervalo(int cadaCuanto) {
        this.intervalo = cadaCuanto;
        this.fechaFin = tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
    }
    public void modificarTipoFrecuenciaMensual(FrecuenciaMensual.Tipo tipoFrecuenciaMensual) {
        if (tipo instanceof  FrecuenciaMensual) {
            ((FrecuenciaMensual) tipo).setTipoFrecuenciaMensual(tipoFrecuenciaMensual);
        }
        this.fechaFin = tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
    }
    public void setFechaInicio(LocalDate fechaInicio){
        this.fechaInicio = fechaInicio;
        if (this.ocurrencias > 0){
            this.fechaFin = tipo.calcularFechaFin(intervalo, ocurrencias, this.fechaInicio, fechaFin);
        }
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }


    /**
     * Devuelve la última fecha correspondiente a la frecuencia
     * */
    public LocalDate calcularFechaFin(){//Deberia ser privada, es publica para hacer las pruebas
        return tipo.calcularFechaFin(intervalo, ocurrencias, fechaInicio, fechaFin);
    }


    /**
     * Devuelve la proxima fecha correspondiente a la frecuencia
     * o null si no hay proxima
     * */
    public LocalDate obtenerProximaFecha() {
        return tipo.obtenerFechaProxima(fechaProxima, intervalo, fechaFin);
    }


    /**
     * Recibe una fecha aleatoria y devuelve true si la fecha recibida
     * corresponde a la frecuencia.
     * Por ej. si la frecuencia cada semana los martes y jueves
     * y recibe una fecha que corresponde a un martes, devolverá true
     * */
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera) {
        return tipo.fechaCorrespondeAFrecuencia(fechaCualquiera, fechaInicio, fechaFin, intervalo);
    }
}
