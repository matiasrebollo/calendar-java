import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.MONTHS;

public class FrecuenciaAnual implements EstrategiaFrecuencia {
    public LocalDate calcularFechaFin(int intervalo, int ocurrencias, LocalDate fechaInicio, LocalDate fechaFin,
                                      ArrayList<DayOfWeek> dias, Frecuencia.FrecuenciaMensual frecuenciaMensual){
        if (ocurrencias == -1 && fechaFin.equals(LocalDate.MAX)) {
            return fechaFin;
        }
        LocalDate fechaAux = fechaInicio;
        for (int i = 0; i < ocurrencias-1; i++) {
            fechaAux = fechaAux.plusYears(intervalo);
        }
        return fechaAux;
    }
    public LocalDate obtenerFechaProxima(LocalDate fechaProxima, int intervalo, ArrayList<DayOfWeek> dias,
                                         Frecuencia.FrecuenciaMensual frecuenciaMensual, LocalDate fechaFin){
        fechaProxima = fechaProxima.plusYears(intervalo);
        if (fechaProxima.isAfter(fechaFin)){
            return null;
        }
        return fechaProxima;
    }
    public boolean fechaCorrespondeAFrecuencia(LocalDate fechaCualquiera, LocalDate fechaInicio, LocalDate fechaFin,
                                               int intervalo, ArrayList<DayOfWeek> dias, Frecuencia.FrecuenciaMensual frecuenciaMensual){
        if (fechaInicio.equals(fechaCualquiera) || fechaCualquiera.equals(fechaFin)) {
            return true;
        }
        else if (fechaInicio.isBefore(fechaCualquiera) && fechaCualquiera.isBefore(fechaFin)){
            int diferenciaDeMeses = (int)MONTHS.between(fechaInicio, fechaCualquiera);
            int diaDelMes = fechaInicio.getDayOfMonth();
            int diaDelMesCualquiera = fechaCualquiera.getDayOfMonth();

            if ((diferenciaDeMeses/12) % intervalo != 0) {
                return false;
            }
            Month mes = fechaInicio.getMonth();
            Month mesCualquiera = fechaCualquiera.getMonth();
            return (diaDelMes == diaDelMesCualquiera && mes.equals(mesCualquiera));
        }
        return false;
    }
}
