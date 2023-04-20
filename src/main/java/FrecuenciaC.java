import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;

public class FrecuenciaC implements Frecuencia{
    private LocalDate fechaInicio;
    private TipoFrecuencia tipo;
    private FrecuenciaMensual frecuenciaMensual;
    private int intervalo;
    private ArrayList<DayOfWeek> diasDeLaSemana = new ArrayList<>();
    private LocalDate fechaFin;
    private int cantRepeticiones;

    public FrecuenciaC(TipoFrecuencia tipo, LocalDate fechaInicio, int intervalo, LocalDate fechaFin) {
        this.tipo = tipo;
        this.intervalo = 1; //por defecto
        frecuenciaMensual = FrecuenciaMensual.MISMODIA;//por defecto
        if (fechaFin == null) {
            this.fechaFin = LocalDate.MAX;
        }
        else {
            this.fechaFin = fechaFin;
        }
        this.diasDeLaSemana.add(fechaInicio.getDayOfWeek());//por defecto
        this.fechaInicio = fechaInicio;
    }
    public FrecuenciaC(TipoFrecuencia tipo,LocalDate fechaInicio, int intervalo, int cantRepeticiones) {
        this.tipo = tipo;
        this.intervalo = intervalo;
        this.fechaInicio = fechaInicio;
        this.cantRepeticiones = cantRepeticiones;
        this.frecuenciaMensual = FrecuenciaMensual.MISMODIA;//por defecto
        this.diasDeLaSemana.add(fechaInicio.getDayOfWeek());
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
     * agrega el dia de la semana al array si no fue agregado antes
     * o lo quita del array si este ya estaba agregado
     * */
    public void agregarOQuitarDiaDeLaSemana(DayOfWeek dia){
        if (this.tipo != TipoFrecuencia.SEMANAL) {
            return;
        }
        if (diasDeLaSemana.contains(dia)){
            diasDeLaSemana.remove(dia);
        }
        else {
            diasDeLaSemana.add(dia);
        }
    }
    public boolean incluyeElDia(DayOfWeek dia) {
        return diasDeLaSemana.contains(dia);
    }

    public void setTipo(TipoFrecuencia tipo) {
        this.tipo = tipo;
    }
    public void setIntervalo(int cadaCuanto) {
        this.intervalo = cadaCuanto;
    }

    public void setFrecuenciaMensual(FrecuenciaMensual frecuenciaMensual) {
        this.frecuenciaMensual = frecuenciaMensual;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    private LocalDate fechaFinDiaria(){
        return fechaFin;
    }
    private LocalDate fechaFinSemanal(LocalDate fechaAux) {
        int contador = 0;
        while (contador < cantRepeticiones) {
            var diaActual = fechaAux.getDayOfWeek();
            if (diasDeLaSemana.contains(diaActual)) {
                contador++;
            }
            if (contador < cantRepeticiones) {
                fechaAux = fechaAux.plusDays(1);
                if (fechaAux.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {//si termina la semana
                    fechaAux = fechaAux.plusWeeks(intervalo-1);
                }
            }
        }
        return fechaAux;
    }
    private LocalDate fechaFinMensual(LocalDate fechaAux){
        switch (frecuenciaMensual) {
            case MISMONUMERO -> {
                for (int i = 0; i < cantRepeticiones-1; i++) {
                    fechaAux = fechaAux.plus(intervalo, ChronoUnit.MONTHS);//sumo 1 mes
                }
            }
            case MISMODIA -> {
                int diaDelMes = fechaAux.getDayOfMonth();
                var diaInicial = fechaAux.getDayOfWeek();
                int mesActual = fechaAux.getMonthValue();
                int anioActual = fechaAux.getYear();
                int nroSemana = (diaDelMes - 1) / 7 + 1;// 7: cant de dias de una semana

                for (int i = 0; i < cantRepeticiones; i++) {
                    mesActual++;
                    if (mesActual > 12) {
                        mesActual = 1;
                        anioActual++;
                    }
                    i++;
                }
                fechaAux = LocalDate.of(anioActual,mesActual, 1);
                fechaAux = fechaAux.plusWeeks(nroSemana-1);

                while (!fechaAux.getDayOfWeek().equals(diaInicial)){
                    fechaAux = fechaAux.plusDays(1);
                }
            }
        }
        return fechaAux;
    }
    LocalDate calcularFechaFin(){
        LocalDate fechaAux = fechaInicio;
        switch (this.tipo) {
            case CERO -> {
                return fechaFin;
            }
            case DIARIA -> {
                for (int i = 0; i < cantRepeticiones-1; i++) {
                    fechaAux = fechaAux.plusDays(intervalo);
                }
            }
            case SEMANAL -> {
                fechaAux = fechaFinSemanal(fechaAux);
            }
            case MENSUAL -> {
                fechaAux = fechaFinMensual(fechaAux);
            }
            case ANUAL -> {
                for (int i = 0; i < cantRepeticiones-1; i++) {
                    fechaAux = fechaAux.plusYears(intervalo);
                }
            }
        }
        return fechaAux;
    }

    /**
     * Devuelve true si la fecha recibida está incluida en la frecuencia,
     * es decir si es parte del ciclo de dis establecidos.
     * Por ej. si la frecuencia cada semana los martes y jueves
     * y recibe una fecha que corresponde a un martes, devolverá true
     * */
    public boolean fechaEstaIncluida(LocalDate fechaCualqueira){
        if (fechaInicio.equals(fechaCualqueira) || fechaCualqueira.equals(fechaFin)) {
            return true;
        }
        else if (fechaInicio.isBefore(fechaCualqueira) && fechaCualqueira.isBefore(fechaFin)){
            int diferenciaDeDias = (int)DAYS.between(fechaInicio, fechaCualqueira);
            int diferenciaDeMeses = (int)MONTHS.between(fechaInicio, fechaCualqueira);
            int diaDelMes = fechaInicio.getDayOfMonth();
            int diaDelMesCualquiera = fechaCualqueira.getDayOfMonth();

            switch (this.tipo){
                case CERO -> {
                    return false;
                }
                case DIARIA -> {
                    return (diferenciaDeDias % intervalo == 0);
                }
                case SEMANAL -> {
                    DayOfWeek diaCualquiera = fechaCualqueira.getDayOfWeek();
                    return (this.incluyeElDia(diaCualquiera) && (diferenciaDeDias % (intervalo*7)) == 0);
                }
                case MENSUAL -> {
                    int intervalo = this.getIntervalo();
                    if (diferenciaDeMeses % intervalo != 0) {
                        return false;
                    }
                    switch (frecuenciaMensual) {
                        case MISMONUMERO -> {
                            return (diaDelMes == diaDelMesCualquiera);
                        }
                        case MISMODIA -> {
                            int nroSemana = (diaDelMes + 6) / 7;// 7: cant de dias de una semana
                            int nroSemanaCualquiera = (diaDelMesCualquiera + 6) / 7;
                            DayOfWeek dia = fechaInicio.getDayOfWeek();
                            DayOfWeek diaCualquiera = fechaCualqueira.getDayOfWeek();

                            return  (dia.equals(diaCualquiera) && (nroSemana == nroSemanaCualquiera));
                        }
                    }
                }
                case ANUAL -> {
                    Month mes = fechaInicio.getMonth();
                    Month mesCualquiera = fechaCualqueira.getMonth();
                    return (diaDelMes == diaDelMesCualquiera && mes.equals(mesCualquiera) &&
                            diferenciaDeMeses / intervalo*12 == 0);
                }
            }
        }
        return false;
    }
}
