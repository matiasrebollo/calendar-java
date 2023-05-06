import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.MONTHS;

public class FrecuenciaMensual extends Frecuencia {
    enum Tipo {MISMODIA, MISMONUMERO}//ej. todos los meses, el dia 7 o todos los meses, el 3er lunes

    private Tipo frecuenciaMensual;

    public FrecuenciaMensual(LocalDate fechaInicio, int intervalo, LocalDate fechaFin, Tipo tipoFrecuenciaMensual) {
        super(fechaInicio, intervalo, fechaFin);
        this.frecuenciaMensual = tipoFrecuenciaMensual;
    }
    public FrecuenciaMensual(LocalDate fechaInicio, int intervalo, int ocurrencias, Tipo tipoFrecuenciaMensual) {
        super(fechaInicio,intervalo,ocurrencias);
        this.frecuenciaMensual = tipoFrecuenciaMensual;
        super.fechaFin = calcularFechaFin();
    }


    public void setTipoFrecuenciaMensual(Tipo tipoFrecuenciaMensual) {
        this.frecuenciaMensual = tipoFrecuenciaMensual;
        this.calcularFechaFin();
    }

    private int calcularNroSemana(LocalDate fecha) {
        int diaDelMes = fecha.getDayOfMonth();
        return (diaDelMes - 1) / 7 + 1; // 7: cant de dias de una semana
    }

    public LocalDate calcularFechaFin() {
        if (fechaFin != null && ocurrencias == -1 && fechaFin.equals(LocalDate.MAX)) {
            return fechaFin;
        }
        LocalDate fechaAux = fechaInicio;
        switch (frecuenciaMensual) {
            case MISMONUMERO -> {
                for (int i = 0; i < ocurrencias-1; i++) {
                    fechaAux = fechaAux.plus(intervalo, ChronoUnit.MONTHS);//sumo 1 mes
                }
            }
            case MISMODIA -> {
                var diaInicial = fechaAux.getDayOfWeek();
                int nroSemana = calcularNroSemana(fechaAux);
                fechaAux = LocalDate.of(fechaAux.getYear(), fechaAux.getMonthValue(), 1);

                for (int i = 0; i < ocurrencias-1; i++) {
                    fechaAux = fechaAux.plusMonths(intervalo);
                }

                int nroSemanaAux = calcularNroSemana(fechaAux);

                while (!fechaAux.getDayOfWeek().equals(diaInicial)) {//busco el mismo dia
                    fechaAux = fechaAux.plusDays(1);
                }
                while (nroSemanaAux < nroSemana) { //busco la "misma semana"
                    fechaAux = fechaAux.plusWeeks(1);
                    nroSemanaAux++;
                }
            }
        }
        return fechaAux;
    }
    public LocalDate obtenerFechaProxima(){
        switch (frecuenciaMensual) {
            case MISMONUMERO -> {
                fechaProxima = fechaProxima.plusMonths(intervalo);
            }
            case MISMODIA -> {
                var diaInicial = fechaProxima.getDayOfWeek();
                int nroSemana = calcularNroSemana(fechaProxima);
                fechaProxima = LocalDate.of(fechaProxima.getYear(), fechaProxima.getMonthValue(), 1);

                fechaProxima = fechaProxima.plusMonths(intervalo);

                int nroSemanaAux = calcularNroSemana(fechaProxima);

                while (!fechaProxima.getDayOfWeek().equals(diaInicial)) {//busco el mismo dia
                    fechaProxima = fechaProxima.plusDays(1);
                }
                while (nroSemanaAux < nroSemana) { //busco la "misma semana"
                    fechaProxima = fechaProxima.plusWeeks(1);
                    nroSemanaAux++;
                }
            }
        }
        if (fechaProxima.isAfter(fechaFin)){
            return null;
        }
        return fechaProxima;
    }
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera) {
        if (fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin)) {
            return true;
        }
        else if (fechaInicio.isBefore(fechaCualquiera) && fechaCualquiera.isBefore(fechaFin)){
            int diferenciaDeMeses = (int)MONTHS.between(fechaInicio, fechaCualquiera);
            int diaDelMes = fechaInicio.getDayOfMonth();
            int diaDelMesCualquiera = fechaCualquiera.getDayOfMonth();

            if ((diferenciaDeMeses % intervalo) != 0) {
                return false;
            }
            switch (frecuenciaMensual) {
                case MISMONUMERO -> {
                    return (diaDelMes == diaDelMesCualquiera);
                }
                case MISMODIA -> {
                    int nroSemana = calcularNroSemana(fechaInicio);
                    int nroSemanaCualquiera = calcularNroSemana(fechaCualquiera);
                    DayOfWeek dia = fechaInicio.getDayOfWeek();
                    DayOfWeek diaCualquiera = fechaCualquiera.getDayOfWeek();

                    return (dia.equals(diaCualquiera) && (nroSemana == nroSemanaCualquiera));
                }
            }
        }
        return false;
    }
}
